package domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter
    private Long id;
    @Cascade(CascadeType.ALL)
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;
    private List<Image> images;

    public Gallery(Item item){
        this.item = item;
        this.images = new ArrayList<>();
    }

    public void addImage(Image image){
        this.images.add(image);
    }

}
