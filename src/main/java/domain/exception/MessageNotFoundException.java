package domain.exception;

public class MessageNotFoundException extends RuntimeException{
    public MessageNotFoundException(){
        super("Mensagem não encontrada");
    }
}
