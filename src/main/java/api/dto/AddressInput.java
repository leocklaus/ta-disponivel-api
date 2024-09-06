package api.dto;

import domain.entity.CEP;
import jakarta.validation.constraints.NotBlank;

public record AddressInput(
        @NotBlank(message = "O CEP é obrigatório")
        CEP cep,
        @NotBlank(message = "A rua é obrigatória")
        String street,
        @NotBlank(message = "O bairro é obrigatório")
        String neighbourhood,
        @NotBlank(message = "A cidade é obrigatória")
        String city,
        @NotBlank(message = "O estado é obrigatório")
        String state,
        @NotBlank(message = "O número é obrigatório")
        Integer number,
        String extraInfo
) {
}
