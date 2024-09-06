package api.controller;

import api.dto.AddressInput;
import api.dto.AddressOutput;
import api.dto.UserOutput;
import domain.service.ManageAddressUseCase;
import domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final ManageAddressUseCase manageAddressUseCase;

    public UserController(UserService userService, ManageAddressUseCase manageAddressUseCase) {
        this.userService = userService;
        this.manageAddressUseCase = manageAddressUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutput> getUserById(@PathVariable UUID id){
        var user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<AddressOutput> getUserAddress(@PathVariable UUID id){
        var address = manageAddressUseCase.getAddressByUserId(id);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/address")
    public ResponseEntity<AddressOutput> getLoggedUserAddress(){
        var address = manageAddressUseCase.getLoggedUserAddress();
        return ResponseEntity.ok(address);
    }

    @PutMapping("/address")
    public ResponseEntity<AddressOutput> updateUserAddress(
            @RequestBody @Valid AddressInput input){

        AddressOutput address = manageAddressUseCase.updateUserAddress(input);

        return ResponseEntity.ok(address);

    }

}
