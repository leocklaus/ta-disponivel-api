package domain.entity;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GalleryTest {

    @Test
    void shouldCreateGalleryIfCorrectInput(){

        UUID itemId = UUID.randomUUID();

        Item item = new Item();
        item.setId(itemId);

        var gallery = new Gallery(item);

        assertThat(gallery.getItem().getId())
                .isEqualTo(itemId);

        assertThat(gallery.getImages())
                .isNotNull();
    }

    @Test
    void shouldAddImageCorrectly(){
        Item item = new Item();
        Gallery gallery = new Gallery(item);
        Image image = new Image("http://www.google.com");

        gallery.addImage(image);

        assertThat(gallery.getImages())
                .isNotNull();

        assertThat(gallery.getImages().contains(image))
                .isTrue();
    }

}