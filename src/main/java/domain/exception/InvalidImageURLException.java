package domain.exception;

public class InvalidImageURLException extends RuntimeException{
    public InvalidImageURLException(){
        super("A URL da imagem é inválida.");
    }
}
