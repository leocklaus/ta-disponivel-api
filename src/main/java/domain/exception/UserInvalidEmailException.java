package domain.exception;

public class UserInvalidEmailException extends UserException{

    public UserInvalidEmailException(String msg) {
        super(msg);
    }

    public UserInvalidEmailException(){
        this("O email informado é inválido");
    }
}
