package domain.exception;

public class NotificationNotFoundException extends RuntimeException{
    public NotificationNotFoundException(){
        super("Notificação não encontrada");
    }
}
