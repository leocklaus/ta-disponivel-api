package domain.service;

import domain.entity.Gallery;
import domain.entity.Item;
import domain.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageGalleryUseCase {

    private final GalleryRepository galleryRepository;

    public void createGallery(Item item){
        Gallery gallery = new Gallery(item);
        galleryRepository.save(gallery);
    }

    public void addImage(String image){

    }

    public void removeImage(String imgUrl){}

}
