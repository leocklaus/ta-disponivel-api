package domain.exception;

public class UserEmailAlreadyTakenException extends UserException{
    public UserEmailAlreadyTakenException(String msg) {
        super(msg);
    }

    public UserEmailAlreadyTakenException(){
        this("O email escolhido já está em uso.");
    }
}
