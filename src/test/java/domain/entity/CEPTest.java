package domain.entity;

import domain.exception.InvalidCEPException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CEPTest {

    @Test
    public void shouldCreateObjectIfCEPIsValid(){
        String value = "95940-000";
        CEP cep = new CEP(value);

        assertThat(cep.value())
                .isEqualTo(value);
    }

    @Test
    public void shouldThrowExceptionIfCEPIsInvalid(){
        String invalidValue = "9596000";

        assertThrows(InvalidCEPException.class, ()->{
            new CEP(invalidValue);
        });

    }

    @Test
    public void shouldThrowExceptionIfValueIsNull(){

        assertThrows(InvalidCEPException.class, ()->{
            new CEP(null);
        });
    }

}