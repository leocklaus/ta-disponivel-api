package domain.entity;

import builders.UserBuilder;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordRecoverCodeTest {

    @Test
    void shouldBeCreatedAsPasswordRecoverCodeType(){
        var user = new UserBuilder().build();
        PasswordRecoverCode passwordRecoverCode = new PasswordRecoverCode(user);

        assertThat(passwordRecoverCode.getCodeType())
                .isEqualTo(CodeType.PASSWORD_RECOVER);
    }

    @Test
    void shouldSetTimeAs5Minutes(){
        var user = new UserBuilder().build();
        PasswordRecoverCode passwordRecoverCode = new PasswordRecoverCode(user);
        passwordRecoverCode.setFiveMinutesDuration();
        assertThat(passwordRecoverCode.getValidUntil())
                .isNotNull();
    }

    @Test
    void shouldSetTimeAs10Minutes(){
        var user = new UserBuilder().build();
        PasswordRecoverCode passwordRecoverCode = new PasswordRecoverCode(user);
        passwordRecoverCode.setTenMinutesDuration();
        assertThat(passwordRecoverCode.getValidUntil())
                .isNotNull();
    }

    @Test
    void shouldGenerateCode(){
        var user = new UserBuilder().build();
        PasswordRecoverCode passwordRecoverCode = new PasswordRecoverCode(user);

        assertThat(passwordRecoverCode.getCode())
                .isNotNull();

        assertThat(passwordRecoverCode.getCode().length())
                .isEqualTo(5);
    }

    @Test
    void shouldCheckIfExpired(){
        var user = new UserBuilder().build();
        PasswordRecoverCode passwordRecoverCode = new PasswordRecoverCode(user);
        passwordRecoverCode.setValidUntil(Instant.now().minusSeconds(10));

        assertThat(passwordRecoverCode.isExpired())
                .isTrue();
    }

}