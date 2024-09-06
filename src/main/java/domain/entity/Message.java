package domain.entity;

import domain.exception.UserNotAuthorizedException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_from_id")
    private User from;

    @ManyToOne
    @JoinColumn(name = "user_to_id")
    private User to;
    private String message;
    @Setter(AccessLevel.NONE)
    private boolean isRead;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Setter(AccessLevel.NONE)
    private LocalDateTime readAt;

    public Message(User from, User to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.isRead = false;
    }

    public void setAsReadBy(User user){

        if(user.equals(to)){
            this.isRead = true;
            this.readAt = LocalDateTime.now();
        }


    }

}