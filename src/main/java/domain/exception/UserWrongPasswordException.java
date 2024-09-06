package domain.exception;

public class UserWrongPasswordException extends UserException{
    public UserWrongPasswordException() {
        super("A senha informada está incorreta.");
    }
}
