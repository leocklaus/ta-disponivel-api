package domain.exception;

public class UserCannotReserveOwnItemException extends UserException{

    public UserCannotReserveOwnItemException(String msg) {
        super(msg);
    }

    public UserCannotReserveOwnItemException(){
        super("O usuário não pode reservar seu pŕoprio item.");
    }
}
