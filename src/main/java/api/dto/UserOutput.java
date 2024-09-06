package api.dto;

import domain.entity.User;

import java.util.UUID;

public record UserOutput(
        UUID id,
        String firstName,
        String lastName,
        String email

) {
    public UserOutput(User user){
        this(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
