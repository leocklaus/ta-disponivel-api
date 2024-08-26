package domain.entity;

import builders.UserBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    public void shouldCreateAddressCorrectlyIfInputIsCorrect(){

        CEP cep = new CEP("95940-000");
        User user = new UserBuilder().build();

        Address address = new Address(
                1L,
                cep,
                "rua",
                "Centro",
                "São Paulo",
                "SP",
                400,
                null,
                user
        );

        assertThat(address.getId())
                .isEqualTo(1L);

        assertThat(address.getCEP())
                .isEqualTo(cep);

    }


}