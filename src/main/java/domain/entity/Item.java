package domain.entity;

import api.dto.ItemInput;
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

    @Setter
    @ManyToOne
    @JoinColumn(name = "original_owner_id")
    private User originalOwner;

    @Setter
    @ManyToOne
    @JoinColumn(name = "reserved_by_id")
    private User reservedBy;

    @Setter
    @ManyToOne
    @JoinColumn(name = "new_owner_id")
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

    public Item(ItemInput input, User originalOwner){
        this.name = input.name();
        this.description = input.description();
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
