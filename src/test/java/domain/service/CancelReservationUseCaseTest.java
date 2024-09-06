package domain.service;

import builders.ItemBuilder;
import builders.UserBuilder;
import domain.entity.Item;
import domain.entity.NotificationType;
import domain.entity.User;
import domain.exception.UserNotAuthorizedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelReservationUseCaseTest {

    @Mock
    ItemService itemService;
    @Mock
    UserService userService;
    @Mock
    NotificationService notificationService;
    @InjectMocks
    CancelReservationUseCase cancelReservationUseCase;

    @Test
    void shouldCancelReservation() {

        User user = new UserBuilder().build();
        User reservedTo = new UserBuilder().build();

        Item item = new ItemBuilder()
                .withOriginalOwner(user)
                .reservedTo(reservedTo)
                .build();

        doReturn(item).when(itemService).getItemByIdOrThrowsException(any());
        doReturn(user).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();

        cancelReservationUseCase.cancel(item.getId());

        assertThat(item.isReserved())
                .isFalse();

        verify(notificationService, times(1)).createNotification(user, reservedTo, NotificationType.RESERVED);
    }

    @Test
    void shouldThrowExceptionIfUserDoNotOwnItem(){

        Item item = new ItemBuilder().build();
        User differentUser = new UserBuilder().build();

        doReturn(item).when(itemService).getItemByIdOrThrowsException(any());
        doReturn(differentUser).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();

        assertThrows(UserNotAuthorizedException.class, ()->{
            cancelReservationUseCase.cancel(item.getId());
        });
    }
}