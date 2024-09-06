package domain.service;

import api.dto.AddressInput;
import api.dto.AddressOutput;
import domain.entity.Address;
import domain.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManageAddressUseCase {

    private final UserService userService;
    private final AddressRepository addressRepository;

    public AddressOutput getAddressByUserId(UUID uuid){
        var user = userService.getUserByIdOrThrowsExceptionIfNotExists(uuid);

        var address = addressRepository.findAddressByUser(user);

        return new AddressOutput(address);
    }

    public AddressOutput getLoggedUserAddress(){
        var user = userService.getLoggedUserOrThrowsExceptionIfNotExists();
        var address = addressRepository.findAddressByUser(user);

        return new AddressOutput(address);
    }

    public AddressOutput updateUserAddress(AddressInput newAddress){
        var user = userService.getLoggedUserOrThrowsExceptionIfNotExists();
        var currentAddress = addressRepository.findAddressByUser(user);
        updateAddress(currentAddress, newAddress);

        currentAddress = addressRepository.save(currentAddress);

        return new AddressOutput(currentAddress);
    }

    private void updateAddress(Address currentAddress, AddressInput newAddress){
        currentAddress.update(newAddress);
    }


}
