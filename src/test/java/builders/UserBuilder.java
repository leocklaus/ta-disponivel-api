package builders;

import domain.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserBuilder {

    private UUID id = UUID.randomUUID();
    private String firstName = "user";
    private String lastName = "random";
    private String email = "user@email.com";
    private String password = "Aa123456!";
    private final LocalDateTime createdAt = LocalDateTime.now();

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

    public User build(){
        return new User(id, firstName, lastName, email, password, createdAt);
    }

}
