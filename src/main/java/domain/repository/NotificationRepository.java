package domain.repository;

import domain.entity.Notification;
import domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    @Query(value = "SELECT * FROM notification n WHERE n.to = ?1 LIMIT 5", nativeQuery = true)
    List<Notification> getLastFiveUserNotifications(UUID to);

}
