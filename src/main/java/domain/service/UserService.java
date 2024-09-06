package domain.service;

import api.dto.*;
import domain.entity.ActivationCode;
import domain.entity.Code;
import domain.entity.PasswordRecoverCode;
import domain.entity.User;
import domain.exception.*;
import domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final CodeService codeService;


    public UserOutput getUserById(UUID id){
        User user = getUserByIdOrThrowsExceptionIfNotExists(id);
        return new UserOutput(user);
    }

    @Transactional
    public UserOutput addUser(UserInput userInput) {

        if(userRepository.existsByEmail(userInput.email())){
            throw new UserEmailAlreadyTakenException(userInput.email());
        }

        User user = new User(userInput);

        user.setEncodedPassword(encodePassword(user.getPassword()));

        user = userRepository.save(user);

        codeService.sendEmail(new ActivationCode(user));

        return new UserOutput(user);
    }

    public void enableUser(String activationCode) {
        Code code = codeService.findCode(activationCode);

        var user = code.getUser();

        if(code.isExpired() && !user.isEnabled()){
            codeService.sendEmail(new ActivationCode(user));
            throw new InvalidCodeException("Code has expired");
        }

        user.enableAccount();

        userRepository.save(user);
    }


    @Transactional
    public void changePassword(ChangePasswordInput input){
        User user = getLoggedUserOrThrowsExceptionIfNotExists();

        if(!passwordMatches(input.currentPassword(), user.getPassword())){
            throw new UserWrongPasswordException();
        };

        user.setEncodedPassword(encodePassword(input.newPassword()));

        userRepository.save(user);
    }

    @Transactional
    public void changePasswordWithCode(ChangePasswordWithCode input){
        User user = forgotPasswordValidate(input.code());
        user.setEncodedPassword(encodePassword(input.newPassword()));

        userRepository.save(user);
    }

    public void changeEmail(ChangeEmailInput input){
        User user = getLoggedUserOrThrowsExceptionIfNotExists();

        if(!Objects.equals(user.getEmail(), input.currentEmail())){
            throw new UserNotAuthorizedException();
        }

        user.setEmail(input.newEmail());

        userRepository.save(user);

    }

    public void forgotPasswordRequest(String email){
        User user = getUserByEmailOrThrowsExceptionIfNotExists(email);

        codeService.sendEmail(new PasswordRecoverCode(user));
    }

    private User forgotPasswordValidate(String code){
        Code passwordCode = codeService.findCode(code);

        if(passwordCode.isExpired()){
            throw new InvalidCodeException();
        }

        return passwordCode.getUser();
    }

    private static boolean passwordMatches(String currentRawPassword, String currentEncryptedPassword) {
        return new BCryptPasswordEncoder().matches(currentRawPassword, currentEncryptedPassword);
    }

    private String encodePassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    public User getLoggedUserOrThrowsExceptionIfNotExists(){
        String email = authService.getAuthenticatedUsername();
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()){
            throw new UserNotFoundException("Usuário não encontrado com o email: " + email);
        }

        return user.get();
    }

    public User getUserByIdOrThrowsExceptionIfNotExists(UUID id){
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    public User getUserByEmailOrThrowsExceptionIfNotExists(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("Usuário não encontrado com o email: " + email));
    }

}
