package builders;

import domain.entity.User;
import domain.entity.UserRoles;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserBuilder {

    private UUID id = UUID.randomUUID();
    private String firstName = "user";
    private String lastName = "random";
    private String email = "user@email.com";
    private String password = "Aa123456!";
    private UserRoles userRoles = null;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private boolean isEnabled = false;

    public UserBuilder withId(UUID id){
        this.id = id;
        return this;
    }

    public UserBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(String password){
        this.password = password;
        return this;
    }

    public UserBuilder asAdmin(){
        this.userRoles = UserRoles.ADMIN;
        return this;
    }

    public UserBuilder enabled(){
        this.isEnabled = true;
        return this;
    }

    public User build(){
        return new User(id, firstName, lastName, email, password,userRoles, createdAt, isEnabled);
    }

}
