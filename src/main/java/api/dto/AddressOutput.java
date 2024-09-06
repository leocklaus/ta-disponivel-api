package api.dto;

import domain.entity.Address;
import domain.entity.CEP;
import jakarta.validation.constraints.NotBlank;

public record AddressOutput(
        CEP cep,
        String street,
        String neighbourhood,
        String city,
        String state,
        Integer number,
        String extraInfo
) {
    public AddressOutput(Address address){
        this(
                address.getCEP(),
                address.getStreet(),
                address.getNeighbourhood(),
                address.getCity(),
                address.getState(),
                address.getNumber(),
                address.getExtraInfo()
        );
    }
}
