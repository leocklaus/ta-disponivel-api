package domain.service;

import api.dto.NotificationOutput;
import domain.entity.Notification;
import domain.entity.NotificationType;
import domain.entity.User;
import domain.exception.NotificationNotFoundException;
import domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public Notification getNotificationByIdOrThrowsException(UUID id){
        return notificationRepository.findById(id)
                .orElseThrow(NotificationNotFoundException::new);
    }

    public void createNotification(User from, User to, NotificationType type){
        Notification notification = new Notification(from, to, type);
        notificationRepository.save(notification);
    }

    public List<NotificationOutput> getLoggedUserLastNotifications(){
        User user = userService.getLoggedUserOrThrowsExceptionIfNotExists();
        List<Notification> notifications = notificationRepository
                .getLastFiveUserNotifications(user.getId());

        return notifications.stream()
                .map(Notification::getNotification)
                .toList();
    }
    public void setNotificationAsRead(UUID id){
        Notification notification = getNotificationByIdOrThrowsException(id);
        notification.setAsRead();
        notificationRepository.save(notification);
    }

}
