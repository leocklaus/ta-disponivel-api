package domain.entity;

import domain.exception.InvalidCEPException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record CEP(String value) {

    public CEP{
        if(!isCEPValid(value)){
            throw new InvalidCEPException();
        }
    }


    private boolean isCEPValid(String value){

        if(value == null){
            return false;
        }

        Pattern p = Pattern.compile("^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$");
        Matcher m = p.matcher(value);
        return m.matches();

    }
}
