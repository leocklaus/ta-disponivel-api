package domain.entity;

import builders.UserBuilder;
import domain.exception.ItemAlreadyDonatedException;
import domain.exception.ItemNotAvailableException;
import domain.exception.ItemReservedByDifferentUserException;
import domain.exception.UserCannotReserveOwnItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemTest {

    private User originalOwner;
    private User reservedBy;
    private User newOwner;
    private Item item;
    private Item alreadyDonatedItem;
    private Item alreadyReservedItem;

    @BeforeEach
    public void setUp(){
        this.originalOwner = new UserBuilder().build();
        this.reservedBy = new UserBuilder()
                .withId(UUID.randomUUID())
                .build();
        this.newOwner = new UserBuilder()
                .withId(UUID.randomUUID())
                .build();

        String name = "Item";
        String description = "Novo Item";

        this.item = new Item(name, description, originalOwner);

        this.alreadyReservedItem = new Item(
                UUID.randomUUID(),
                name,
                description,
                originalOwner,
                reservedBy,
                null,
                ItemStatus.RESERVED,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );

        this.alreadyDonatedItem = new Item(
                UUID.randomUUID(),
                name,
                description,
                originalOwner,
                reservedBy,
                reservedBy,
                ItemStatus.DONATED,
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    public void shouldCreateAnItemIfValidInput(){

        String name = "Item";
        String description = "Novo Item";

        assertThat(item.getName())
                .isEqualTo(name);

        assertThat(item.getDescription())
                .isEqualTo(description);

        assertThat(item.getOriginalOwner())
                .isEqualTo(originalOwner);

        assertThat(item.getItemStatus())
                .isEqualTo(ItemStatus.AVAILABLE);
    }

    @Test
    public void shouldReserveItemIfDifferentUser(){

        item.setReservation(reservedBy);

        assertThat(item.getReservedBy())
                .isEqualTo(reservedBy);

        assertThat(item.isReserved())
                .isTrue();
    }

    @Test
    public void shouldThrowExceptionIfTryingToReserveToOwner(){
        assertThrows(UserCannotReserveOwnItemException.class, ()->{
            item.setReservation(originalOwner);
        });

        assertThat(item.isAvailable())
                .isTrue();

    }

    @Test
    public void shouldThrowExceptionIfTryingToReserveItemAlreadyDonated(){
        assertThrows(ItemNotAvailableException.class, ()->{
            alreadyDonatedItem.setReservation(newOwner);
        });
    }

    @Test
    public void shouldSetItemAsDonated(){
        this.alreadyReservedItem.donate(reservedBy);

        assertThat(alreadyReservedItem.isDonated())
                .isTrue();

        assertThat(alreadyReservedItem.getNewOwner())
                .isEqualTo(this.alreadyReservedItem.getReservedBy());
    }

    @Test
    public void shouldThrowExceptionIfTryingToDonateItemReservedByDifferentUser(){
        assertThrows(ItemReservedByDifferentUserException.class, ()->{
            alreadyReservedItem.donate(newOwner);
        });

        assertThat(alreadyReservedItem.isDonated())
                .isFalse();
    }

    @Test
    public void shouldThrowExceptionIfItemAlreadyDonated(){

        assertThrows(ItemAlreadyDonatedException.class, ()->{
            alreadyDonatedItem.donate(reservedBy);
        });

    }

    @Test
    public void shouldCancelReservation(){
        alreadyReservedItem.cancelReservation();

        assertThat(alreadyReservedItem.isAvailable())
                .isTrue();
    }

    @Test
    public void shouldThrowExceptionIfTryingToCancelItemAlreadyDonated(){
        assertThrows(ItemAlreadyDonatedException.class, ()->{
           alreadyDonatedItem.cancelReservation();
        });
    }

}