package builders;

import api.dto.AddressInput;
import api.dto.UserInput;

public class UserInputBuilder {
    private String firstName = "username";
    private String lastName = "lastname";
    private String email = "email@email.com";
    private String password = "Aa123456!";
    private AddressInput addressInput = null;

    public UserInputBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public UserInputBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public UserInputBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public UserInputBuilder withPassword(String password){
        this.password = password;
        return this;
    }

    public UserInputBuilder withAdress(AddressInput input){
        this.addressInput = input;
        return this;
    }

    public UserInput build(){
        return new UserInput(
                firstName,
                lastName,
                email,
                password,
                addressInput
        );
    }
}
