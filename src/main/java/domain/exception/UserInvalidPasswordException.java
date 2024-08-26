package domain.exception;

public class UserInvalidPasswordException extends UserException{

    public UserInvalidPasswordException(String msg) {
        super(msg);
    }

    public UserInvalidPasswordException(){
        this("A senha é inválida.");
    }
}
