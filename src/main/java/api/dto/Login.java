package api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record Login(
        @NotNull(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,
        @NotNull(message = "Senha é obrigatória")
        String password
) {
}
