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
public class Reply extends FAQ{

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Reply(User user, String text, Question question) {
        super(user, text);
        this.question = question;
    }
}
