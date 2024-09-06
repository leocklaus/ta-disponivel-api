package domain.service;

import domain.entity.Item;
import domain.entity.NotificationType;
import domain.entity.User;
import domain.exception.UserNotAuthorizedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReserveItemUseCase {

    private final ItemService itemService;
    private final UserService userService;
    private final NotificationService notificationService;

    @Transactional
    public void reserve(UUID itemId, UUID reserveTo){

        Item item = itemService.getItemByIdOrThrowsException(itemId);
        User loggedUser = userService.getLoggedUserOrThrowsExceptionIfNotExists();
        User user = userService.getUserByIdOrThrowsExceptionIfNotExists(reserveTo);

        if(loggedUserDoesNotOwnItem(item, loggedUser)){
            throw new UserNotAuthorizedException();
        }

        item.setReservation(user);
        sendNotification(loggedUser, user);

    }

    private boolean loggedUserDoesNotOwnItem(Item item, User loggedUser){

        return !item.getOriginalOwner().equals(loggedUser);
    }

    private void sendNotification(User from, User to){
        notificationService.createNotification(from, to, NotificationType.RESERVED);
    }

}
