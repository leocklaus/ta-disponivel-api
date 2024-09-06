package domain.exception;

import java.util.UUID;

public class UserNotFoundException extends UserException{
    public UserNotFoundException(String msg) {
        super(msg);
    }
    public UserNotFoundException(UUID uuid){
        this("Usuário não encontrado com o id: " + uuid);
    }
}
