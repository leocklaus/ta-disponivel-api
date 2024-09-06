package api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordWithCode(
        @NotBlank(message = "O código é obrigatório") String code,
        @NotNull(message = "A senha é obrigatória") String newPassword
) {
}
