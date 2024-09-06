package domain.exception;

import java.util.UUID;

public class ItemNotFoundException extends ItemException{
    public ItemNotFoundException(String msg) {
        super(msg);
    }

    public ItemNotFoundException(UUID id){
        this("Item not found with id: " + id);
    }
}
