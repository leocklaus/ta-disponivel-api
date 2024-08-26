package domain.entity;

import domain.exception.UserInvalidEmailException;
import domain.exception.UserInvalidPasswordException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateANewUserCorrectly(){
        String firstName = "user";
        String lastName = "random";
        String email = "user@email.com";
        String password = "Aa123456!";

        User user = new User(firstName, lastName, email, password);

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
        String firstName = "user";
        String lastName = "random";
        String email = "user@email.com";
        String password = "Aa123456!";

        String fullName = firstName + " " + lastName;

        User user = new User(firstName, lastName, email, password);

        assertThat(user.getName())
                .isEqualTo(fullName);
    }

    @Test
    void shouldThrowExceptionIfEmailFormatIsInvalid(){
        String firstName = "user";
        String lastName = "random";
        String email = "email";
        String password = "Aa123456!";


        assertThrows(UserInvalidEmailException.class, ()->{
            User user = new User(firstName, lastName, email, password);
        });
    }

    @Test
    public void shouldCreateUserIfValidEmail(){
        String firstName = "user";
        String lastName = "random";
        String email = "user@email.com";
        String password = "Aa123456!";


        User user = new User(firstName, lastName, email, password);

        assertThat(user.getEmail())
                .isEqualTo(email);
    }

    @Test
    public void shouldThrowExceptionIfPasswordFormatIsInvalid(){
        String firstName = "user";
        String lastName = "random";
        String email = "email@email.com";
        String password = "123456!";


        assertThrows(UserInvalidPasswordException.class, ()->{
            User user = new User(firstName, lastName, email, password);
        });
    }

    @Test
    public void shouldCreateUserCorrectlyIfPasswordFormatIsValid(){
        String firstName = "user";
        String lastName = "random";
        String email = "user@email.com";
        String password = "Aa123456!";


        User user = new User(firstName, lastName, email, password);

    }

}