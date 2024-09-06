package api.dto;

import jakarta.validation.constraints.NotNull;

public record ChangeEmailInput(
        @NotNull(message = "O email é obrigatório") String currentEmail,
        @NotNull(message = "O email é obrigatório") String newEmail
) {
}
