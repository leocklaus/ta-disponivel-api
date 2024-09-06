package domain.exception;

public class UserNotAuthorizedException extends UserException{
    public UserNotAuthorizedException(String msg){
        super(msg);
    }

    public UserNotAuthorizedException(){
        this("Usuário não autorizado.");
    }
}
