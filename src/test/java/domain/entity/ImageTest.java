package domain.entity;

import domain.exception.InvalidImageURLException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ImageTest {

    @Test
    public void shouldCreateImageIfValidURL(){
        String validURL = "http://www.google.com";
        Image image = new Image(validURL);
        assertThat(image.imgURL())
                .isEqualTo(validURL);
    }

    @Test
    public void shouldThrowExceptionIfURLIsInvalid(){
        String invalidURL = "google";
        assertThrows(InvalidImageURLException.class, ()->{
            new Image(invalidURL);
        });
    }

    @Test
    public void shouldThrowExceptionIfURLIsNull(){
        assertThrows(InvalidImageURLException.class, ()->{
            new Image(null);
        });
    }
}