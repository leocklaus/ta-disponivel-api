package domain.exception;

public class ItemReservedByDifferentUserException extends ItemException{
    public ItemReservedByDifferentUserException(String msg) {
        super(msg);
    }

    public ItemReservedByDifferentUserException(){
        this("O item já está reservado para outro usuário.");
    }
}
