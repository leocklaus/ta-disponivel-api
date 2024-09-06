package domain.exception;

public class InvalidCodeException extends RuntimeException{
    public InvalidCodeException(String msg){
        super(msg);
    }
    public InvalidCodeException(){
        this("O código informado é inválido ou já expirou. Um novo código foi enviado.");
    }
}
