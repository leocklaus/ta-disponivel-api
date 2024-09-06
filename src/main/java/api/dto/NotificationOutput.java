package api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationOutput(
        UUID notificationId,
        String notificationType,
        String notificationMessage,
        boolean isRead,
        LocalDateTime createdAt
) {
}
