package domain.entity;

import domain.exception.ItemAlreadyDonatedException;
import domain.exception.ItemNotAvailableException;
import domain.exception.ItemReservedByDifferentUserException;
import domain.exception.UserCannotReserveOwnItemException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Setter
    private UUID id;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false)
    @Setter
    private String description;

    @Column(nullable = false)
    @Setter
    private User originalOwner;

    private User reservedBy;
    private User newOwner;

    @Column(nullable = false)
    private ItemStatus itemStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime reservedAt;
    private LocalDateTime donatedAt;

    public Item(String name, String description, User originalOwner){
        this.name = name;
        this.description = description;
        this.originalOwner = originalOwner;
        setAsAvailable();
    }


    public void setReservation(User reservedBy) {

        if(this.originalOwner.equals(reservedBy)){
            throw new UserCannotReserveOwnItemException();
        }

        setAsReserved();
        this.reservedBy = reservedBy;
    }

    public void donate(User newOwner){

        if(!this.reservedBy.equals(newOwner)){
            throw new ItemReservedByDifferentUserException();
        }

        setAsDonated();
        this.newOwner = newOwner;

    }

    public void cancelReservation(){
        setAsAvailable();
        this.reservedBy = null;
    }

    public boolean isAvailable(){
        return this.itemStatus == ItemStatus.AVAILABLE;
    }

    public boolean isReserved(){
        return this.itemStatus == ItemStatus.RESERVED;
    }

    public boolean isDonated(){
        return this.itemStatus == ItemStatus.DONATED;
    }

    private void setAsAvailable(){

        if(isDonated()){
            throw new ItemAlreadyDonatedException();
        }

        this.itemStatus = ItemStatus.AVAILABLE;
    }

    private void setAsReserved(){

        if(!isAvailable()){
            throw new ItemNotAvailableException();
        }

        this.itemStatus = ItemStatus.RESERVED;
        this.reservedAt = LocalDateTime.now();
    }

    private void setAsDonated(){

        if(isDonated()){
            throw new ItemAlreadyDonatedException();
        }

        this.itemStatus = ItemStatus.DONATED;
        this.donatedAt = LocalDateTime.now();
    }
}
