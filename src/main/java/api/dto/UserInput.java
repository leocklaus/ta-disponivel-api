package api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserInput(
        @NotBlank(message = "O nome é obrigatório")
        String firstName,
        @NotBlank(message = "O sobrenome é obrigatório")
        String lastName,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Email deve ser válido")
        String email,
        @NotBlank(message = "A senha é obrigatória")
        String password,
        AddressInput address
) {
}
