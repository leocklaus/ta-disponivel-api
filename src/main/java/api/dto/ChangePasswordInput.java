package api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordInput(
        @NotBlank(message = "A senha é obrigatória") String currentPassword,
        @NotNull(message = "A senha é obrigatória") String newPassword
) {
}
