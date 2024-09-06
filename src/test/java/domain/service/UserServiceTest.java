package domain.service;

import api.dto.*;
import builders.UserBuilder;
import builders.UserInputBuilder;
import domain.entity.*;
import domain.exception.*;
import domain.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    AuthService authService;

    @Mock
    CodeService codeService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    ArgumentCaptor<Code> codeArgumentCaptor;

    @Spy
    @InjectMocks
    UserService userService;

    @Nested
    class GetUserById{

        @Test
        void getUserByIdShouldReturnUserOutput() {

            User user = new UserBuilder()
                    .enabled()
                    .build();

            UserOutput output = new UserOutput(user);

            doReturn(user).when(userService)
                    .getUserByIdOrThrowsExceptionIfNotExists(uuidArgumentCaptor.capture());

            var response = userService.getUserById(user.getId());

            assertEquals(user.getId(), uuidArgumentCaptor.getValue());
            assertEquals(output, response);
            verify(userService, times(1)).getUserById(any());
        }
    }


    @Nested
    class AddUser{

        @Test
        void shouldThrowExceptionIfEmailAlreadyExists() {
            UserInput userInput = new UserInputBuilder()
                    .build();

            doReturn(true).when(userRepository).existsByEmail(userInput.email());

            assertThrows(UserEmailAlreadyTakenException.class, ()->{
                userService.addUser(userInput);
            });
        }

        @Test
        void shouldBuildUserEntityCorrectly() {
            UserInput userInput = new UserInputBuilder()
                    .build();

            User user = new UserBuilder()
                    .withFirstName(userInput.firstName())
                    .withLastName(userInput.lastName())
                    .withEmail(userInput.email())
                    .withPassword(userInput.password())
                    .build();

            doReturn(false).when(userRepository).existsByEmail(userInput.email());
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            userService.addUser(userInput);

            assertEquals(userInput.email(), userArgumentCaptor.getValue().getEmail());
            assertNotEquals(userInput.password(), userArgumentCaptor.getValue().getPassword());

        }

        @Test
        void shouldReturnOutputDTO() {
            UserInput userInput = new UserInputBuilder()
                    .build();

            User user = new UserBuilder()
                    .withFirstName(userInput.firstName())
                    .withLastName(userInput.lastName())
                    .withPassword(userInput.password())
                    .build();

            var output = new UserOutput(user);

            doReturn(false).when(userRepository).existsByEmail(userInput.email());
            doReturn(user).when(userRepository).save(any());

            var response = userService.addUser(userInput);

            assertEquals(output.firstName(), response.firstName());
            assertEquals(output.email(), response.email());

        }

        @Test
        void shouldSendEmail() {
            UserInput userInput = new UserInputBuilder()
                    .build();

            User user = new UserBuilder()
                    .withFirstName(userInput.firstName())
                    .withLastName(userInput.lastName())
                    .withEmail(userInput.email())
                    .withPassword(userInput.password())
                    .build();

            doReturn(false).when(userRepository).existsByEmail(userInput.email());
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            doNothing().when(codeService).sendEmail(
                    codeArgumentCaptor.capture());

            userService.addUser(userInput);

            assertEquals(CodeType.ACTIVATION, codeArgumentCaptor.getValue().getCodeType());
            assertEquals(user, codeArgumentCaptor.getValue().getUser());
            assertEquals(userInput.email(), userArgumentCaptor.getValue().getEmail());
            verify(codeService, times(1)).sendEmail(any());
        }



    }

    @Nested
    class EnableUser{


        @Test
        void shouldThrowExceptionIfCodeHasExpired() {
            String activationCodeInput = "123456";

            User user = new UserBuilder().build();
            Code code = new ActivationCode(user);

            code.setValidUntil(Instant.now().minusSeconds(10));

            doReturn(code).when(codeService).findCode(activationCodeInput);

            assertThrows(InvalidCodeException.class, ()->{
                userService.enableUser(activationCodeInput);
            });
        }

        @Test
        void shouldSendNewEmailIfCodeHasExpired() {
            String activationCodeInput = "123456";

            User user = new UserBuilder().build();
            Code code = new ActivationCode(user);

            code.setValidUntil(Instant.now().minusSeconds(10));

            doReturn(code).when(codeService).findCode(activationCodeInput);

            assertThrows(InvalidCodeException.class, ()->{
                userService.enableUser(activationCodeInput);
            });

            verify(codeService, times(1)).sendEmail(any());
        }

        @Test
        void shouldEnableAccountIfValidCodeAndNotExpired() {
            String activationCodeInput = "123456";

            User user = new UserBuilder().build();
            ActivationCode code = new ActivationCode(user);

            doReturn(code).when(codeService).findCode(activationCodeInput);
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            userService.enableUser(activationCodeInput);

            verify(userRepository, times(1)).save(any());

            assertTrue(userArgumentCaptor.getValue().isEnabled());
        }

    }

    @Nested
    class ChangePassword{
        @Test
        void shouldThrowExceptionIfPasswordNotMatching() {

            ChangePasswordInput input = new ChangePasswordInput("Df45677!", "Ad432567#");

            User user = new UserBuilder()
                    .withPassword("ASa24567#")
                    .build();

            doReturn(user).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();

            assertThrows(UserWrongPasswordException.class, ()->{
                userService.changePassword(input);
            });

        }

        @Test
        void shouldChangePasswordIfMatching() {
            ChangePasswordInput input = new ChangePasswordInput("Aa123456!", "Bdd43765!");

            User user = new UserBuilder()
                    .withPassword(new BCryptPasswordEncoder().encode("Aa123456!"))
                    .build();

            doReturn(user).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();

            userService.changePassword(input);

            verify(userRepository, times(1)).save(any());
        }

        @Test
        void shouldSendPasswordRecoverCodeWhenRequested(){
            String email = "email@test.com";
            User user = new UserBuilder()
                    .withEmail(email)
                    .build();

            doReturn(user).when(userService).getUserByEmailOrThrowsExceptionIfNotExists(email);

            userService.forgotPasswordRequest(email);

            verify(codeService, times(1)).sendEmail(any());
        }

        @Test
        void shouldChangePasswordIfValidCode(){
            ChangePasswordWithCode input = new ChangePasswordWithCode(
                    "abcde", "Aa123456!");
            User user = new UserBuilder()
                    .withPassword("Cd13456!")
                    .build();
            PasswordRecoverCode code = new PasswordRecoverCode(user);

            doReturn(code).when(codeService).findCode(any());
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            userService.changePasswordWithCode(input);

            verify(userRepository, times(1)).save(any());
            assertThat(userArgumentCaptor.getValue().getPassword())
                    .isNotEqualTo("Cd13456!");

        }

        @Test
        void shouldThrowExceptionIfInvalidCode(){
            ChangePasswordWithCode input = new ChangePasswordWithCode(
                    "abcde", "Aa123456!");
            User user = new UserBuilder().build();
            PasswordRecoverCode code = new PasswordRecoverCode(user);
            code.setValidUntil(Instant.now().minusSeconds(10));

            doReturn(code).when(codeService).findCode(any());

            assertThrows(InvalidCodeException.class, ()->{
                userService.changePasswordWithCode(input);
            });

        }
    }

    @Nested
    class ChangeEmail{
        @Test
        void shouldChangeEmailIfMatches(){
            String currentEmail = "current@email.com";
            String newEmail = "new@email.com";
            ChangeEmailInput input = new ChangeEmailInput(currentEmail, newEmail);
            User user = new UserBuilder()
                    .withEmail(currentEmail)
                    .build();

            doReturn(user).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            userService.changeEmail(input);

            assertThat(userArgumentCaptor.getValue().getEmail())
                    .isEqualTo(newEmail);

            verify(userRepository, times(1)).save(any());

        }

        @Test
        void shouldThrowExceptionIfEmailNotMatchesLoggedUser(){
            String currentEmail = "current@email.com";
            String newEmail = "new@email.com";
            ChangeEmailInput input = new ChangeEmailInput("other@email.com", newEmail);
            User user = new UserBuilder()
                    .withEmail(currentEmail)
                    .build();

            doReturn(user).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();

            assertThrows(UserNotAuthorizedException.class, ()->{
                userService.changeEmail(input);
            });
        }
    }

    @Nested
    class GetLoggedUserOrThrowsException{
        @Test
        void shouldThrowExceptionIfNotExists() {

            doReturn("username").when(authService).getAuthenticatedUsername();
            doReturn(Optional.empty()).when(userRepository).findByEmail("username");

            assertThrows(UserNotFoundException.class, ()->{
                userService.getLoggedUserOrThrowsExceptionIfNotExists();
            });
        }
    }

    @Nested
    class GetUserByIdOrThrowsException{
        @Test
        void shouldThrowsExceptionIfUserNotExists() {

            doReturn(Optional.empty()).when(userRepository).findById(any());

            assertThrows(UserNotFoundException.class, ()->{
                userService.getUserByIdOrThrowsExceptionIfNotExists(any());
            });
        }
    }

    @Nested
    class GetUserByEmail{

        @Test
        void shouldThrowExceptionIfUserNotExists(){

            doReturn(Optional.empty()).when(userRepository).findByEmail(any());

            assertThrows(UserNotFoundException.class, ()->{
                userService.getUserByEmailOrThrowsExceptionIfNotExists(any());
            });
        }
    }

}