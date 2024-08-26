package domain.entity;

import domain.exception.InvalidImageURLException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Image(String imgURL) {
    public Image{
        if(!isURLValid(imgURL)){
            throw new InvalidImageURLException();
        }
    }

    private boolean isURLValid(String imgURL){
        if(imgURL == null){
            return false;
        }

        Pattern p = Pattern.compile("^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$");
        Matcher m = p.matcher(imgURL);
        return m.matches();
    }
}
