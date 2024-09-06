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
class DonateItemUseCaseTest {

    @Mock
    ItemService itemService;
    @Mock
    UserService userService;
    @Mock
    NotificationService notificationService;
    @InjectMocks
    DonateItemUseCase donateItemUseCase;

    @Test
    void shouldDonateItem() {
        User user = new UserBuilder().build();
        User reservedTo = new UserBuilder().build();

        Item item = new ItemBuilder()
                .withOriginalOwner(user)
                .reservedTo(reservedTo)
                .build();

        doReturn(item).when(itemService).getItemByIdOrThrowsException(any());
        doReturn(user).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();
        doReturn(reservedTo).when(userService).getUserByIdOrThrowsExceptionIfNotExists(any());

        donateItemUseCase.donate(item.getId(), reservedTo.getId());

        assertThat(item.isDonated())
                .isTrue();

        assertThat(item.getNewOwner())
                .isEqualTo(reservedTo);

        verify(notificationService, times(1)).createNotification(user, reservedTo, NotificationType.DONATED);
    }

    @Test
    void shouldThrowExceptionIfUserDoNotOwnItem(){
        User user = new UserBuilder().build();
        User reservedTo = new UserBuilder().build();
        User differentUser = new UserBuilder().build();

        Item item = new ItemBuilder()
                .withOriginalOwner(user)
                .reservedTo(reservedTo)
                .build();

        doReturn(item).when(itemService).getItemByIdOrThrowsException(any());
        doReturn(differentUser).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();
        doReturn(reservedTo).when(userService).getUserByIdOrThrowsExceptionIfNotExists(any());

        assertThrows(UserNotAuthorizedException.class, ()->{
            donateItemUseCase.donate(item.getId(), reservedTo.getId());
        });
    }
}