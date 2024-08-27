package domain.entity;

import builders.UserBuilder;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ActivationCodeTest {

    @Test
    void shouldBeCreatedAsActivationCodeType(){
        var user = new UserBuilder().build();
        ActivationCode activationCode = new ActivationCode(user);

        assertThat(activationCode.getCodeType())
                .isEqualTo(CodeType.ACTIVATION);
    }

    @Test
    void shouldSetTimeAs5Minutes(){
        var user = new UserBuilder().build();
        ActivationCode activationCode = new ActivationCode(user);
        activationCode.setFiveMinutesDuration();
        assertThat(activationCode.getValidUntil())
                .isNotNull();
    }

    @Test
    void shouldSetTimeAs10Minutes(){
        var user = new UserBuilder().build();
        ActivationCode activationCode = new ActivationCode(user);
        activationCode.setTenMinutesDuration();
        assertThat(activationCode.getValidUntil())
                .isNotNull();
    }

    @Test
    void shouldGenerateCode(){
        var user = new UserBuilder().build();
        ActivationCode activationCode = new ActivationCode(user);

        assertThat(activationCode.getCode())
                .isNotNull();

        assertThat(activationCode.getCode().length())
                .isEqualTo(5);
    }

    @Test
    void shouldCheckIfExpired(){
        var user = new UserBuilder().build();
        ActivationCode activationCode = new ActivationCode(user);
        activationCode.setValidUntil(Instant.now().minusSeconds(10));

        assertThat(activationCode.isExpired())
                .isTrue();
    }

}