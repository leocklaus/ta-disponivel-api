package domain.exception;

public class QuestionNotFoundException extends RuntimeException{
    public QuestionNotFoundException(){
        super("Nenhuma pergunta encontrada com o Id fornecido");
    }
}
