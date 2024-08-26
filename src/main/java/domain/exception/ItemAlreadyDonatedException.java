package domain.exception;

public class ItemAlreadyDonatedException extends ItemException{
    public ItemAlreadyDonatedException(String msg) {
        super(msg);
    }

    public ItemAlreadyDonatedException(){
        super("O item já foi doado.");
    }
}
