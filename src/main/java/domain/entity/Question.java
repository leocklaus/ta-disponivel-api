package domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Question extends FAQ{
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;


    public Question(User user, String text, Item item){
        super(user, text);
        this.item = item;
    }

}
