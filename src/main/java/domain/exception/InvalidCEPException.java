package domain.exception;

public class InvalidCEPException extends RuntimeException{
    public InvalidCEPException(){
        super("O CEP fornecido é inválido.");
    }
}
