package api.controller;

import api.dto.NotificationOutput;
import domain.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<?> getLoggedUserLast5Notifications(){

        List<NotificationOutput> notifications = notificationService
                .getLoggedUserLastNotifications();

        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLoggedUserNotifications(){

        List<NotificationOutput> notifications = notificationService
                .getAllLoggedUserNotifications();

        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> setNotificationAsRead(@PathVariable UUID notificationId){
        notificationService.setNotificationAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotifications(@PathVariable UUID notificationsId){
        notificationService.deleteNotifications(notificationsId);
        return ResponseEntity.noContent().build();
    }

}
