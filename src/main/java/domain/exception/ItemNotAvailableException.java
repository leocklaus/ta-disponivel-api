package domain.exception;

public class ItemNotAvailableException extends ItemException{


    public ItemNotAvailableException(String msg) {
        super(msg);
    }

    public ItemNotAvailableException(){
        this("O item não está disponível");
    }
}
