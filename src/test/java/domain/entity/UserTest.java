package domain.entity;

import domain.exception.UserInvalidEmailException;
import domain.exception.UserInvalidPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final String firstName = "user";
    private final String lastName = "random";
    private final String email = "user@email.com";
    private final String password = "Aa123456!";
    private User user;

    @BeforeEach
    public void setUp(){
        this.user = new User(firstName, lastName, email, password);
    }

    @Test
    void shouldCreateANewUserCorrectly(){

        assertThat(user.getFirstName())
                .isEqualTo(firstName);

        assertThat(user.getLastName())
                .isEqualTo(lastName);

        assertThat(user.getEmail())
                .isEqualTo(email);

        assertThat(user.getPassword())
                .isEqualTo(password);

    }

    @Test
    public void shouldReturnFullName(){

        String fullName = firstName + " " + lastName;

        assertThat(user.getName())
                .isEqualTo(fullName);
    }

    @Test
    void shouldThrowExceptionIfEmailFormatIsInvalid(){

        String email = "email";

        assertThrows(UserInvalidEmailException.class, ()->{
            new User(firstName, lastName, email, password);
        });
    }

    @Test
    public void shouldCreateUserIfValidEmail(){

        String email = "user@email.com";

        User user = new User(firstName, lastName, email, password);

        assertThat(user.getEmail())
                .isEqualTo(email);
    }

    @Test
    public void shouldThrowExceptionIfPasswordFormatIsInvalid(){

        String password = "123456!";

        assertThrows(UserInvalidPasswordException.class, ()->{
            new User(firstName, lastName, email, password);
        });
    }

    @Test
    public void shouldCreateUserCorrectlyIfPasswordFormatIsValid(){

        String password = "Aa123456!";

        User user = new User(firstName, lastName, email, password);

    }

    @Test
    public void shouldCreateUserInitiallyDisabled(){

        User user = new User(firstName, lastName, email, password);

        assertThat(user.isEnabled())
                .isFalse();
    }

    @Test
    public void shouldEnableUser(){

        User user = new User(firstName, lastName, email, password);
        user.enableAccount();

        assertThat(user.isEnabled())
                .isTrue();
    }

    @Test
    public void shouldNotBeCreatedAsAdmin(){

        User user = new User(firstName, lastName, email, password);

        assertThat(user.isAdmin())
                .isFalse();
    }

    @Test
    public void shouldMakeUserAdminWhenRequested(){

        User user = new User(firstName, lastName, email, password);
        user.setAsAdmin();

        assertThat(user.isAdmin())
                .isTrue();
    }

}