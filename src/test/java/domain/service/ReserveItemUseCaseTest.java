package domain.service;

import builders.ItemBuilder;
import builders.UserBuilder;
import domain.entity.Item;
import domain.entity.NotificationType;
import domain.entity.User;
import domain.exception.UserCannotReserveOwnItemException;
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
class ReserveItemUseCaseTest {

    @Mock
    ItemService itemService;
    @Mock
    UserService userService;
    @Mock
    NotificationService notificationService;
    @InjectMocks
    ReserveItemUseCase reserveItemUseCase;

    @Test
    void shouldReserveItem() {

        User user = new UserBuilder().build();
        User reserveTo = new UserBuilder().build();

        Item item = new ItemBuilder()
                .withOriginalOwner(user)
                .build();

        doReturn(item).when(itemService).getItemByIdOrThrowsException(any());
        doReturn(user).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();
        doReturn(reserveTo).when(userService).getUserByIdOrThrowsExceptionIfNotExists(any());

        reserveItemUseCase.reserve(item.getId(), user.getId());

        assertThat(item.isReserved())
                .isTrue();

        assertThat(item.getReservedBy())
                .isEqualTo(reserveTo);

        verify(notificationService, times(1)).createNotification(user, reserveTo, NotificationType.RESERVED);
    }

    @Test
    void shouldThrowExceptionIfTryingToReserveToItemOwner(){

        User user = new UserBuilder().build();

        Item item = new ItemBuilder()
                .withOriginalOwner(user)
                .build();

        doReturn(item).when(itemService).getItemByIdOrThrowsException(any());
        doReturn(user).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();
        doReturn(user).when(userService).getUserByIdOrThrowsExceptionIfNotExists(any());

        assertThrows(UserCannotReserveOwnItemException.class, ()->{
            reserveItemUseCase.reserve(item.getId(), user.getId());
        });

    }
}