package domain.entity;

import api.dto.NotificationOutput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private User from;
    @Column(nullable = false)
    private User to;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private boolean isRead;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime readAt;

    public Notification(User from, User to, NotificationType type){
        this.from = from;
        this.to = to;
        this.type = type;
        this.isRead = false;
    }

    public NotificationOutput getNotification(){
        return new NotificationOutput(
                id,
                type.getName(),
                this.getMessage(),
                isRead(),
                createdAt
        );
    }

    private String getMessage(){
        return from.getFirstName() + type.getMessage();
    }

    public void setAsRead(){
        this.isRead = true;
        this.readAt = LocalDateTime.now();
    }

    public boolean isRead(){
        return isRead;
    }

}
