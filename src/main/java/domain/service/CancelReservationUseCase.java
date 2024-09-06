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
public class CancelReservationUseCase {

    private final ItemService itemService;
    private final UserService userService;
    private final NotificationService notificationService;

    @Transactional
    public void cancel(UUID itemId){
        Item item = itemService.getItemByIdOrThrowsException(itemId);
        User loggedUser = userService.getLoggedUserOrThrowsExceptionIfNotExists();

        if(loggedUserDoesNotOwnItem(item, loggedUser)){
            throw new UserNotAuthorizedException();
        }

        User wasReservedTo = item.getReservedBy();

        item.cancelReservation();
        sendNotification(loggedUser, wasReservedTo);
    }

    private boolean loggedUserDoesNotOwnItem(Item item, User loggedUser){

        return !item.getOriginalOwner().equals(loggedUser);
    }

    private void sendNotification(User from, User to){
        notificationService.createNotification(from, to, NotificationType.RESERVED);
    }

}
