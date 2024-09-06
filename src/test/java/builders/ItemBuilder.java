package builders;

import domain.entity.Item;
import domain.entity.ItemStatus;
import domain.entity.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

public class ItemBuilder {

    private UUID id = UUID.randomUUID();
    private String name = "Item";
    private String description = "Description";
    private User originalOwner = new UserBuilder().build();
    private User reservedBy;
    private User newOwner;
    private ItemStatus itemStatus = ItemStatus.AVAILABLE;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime reservedAt;
    private LocalDateTime donatedAt;

    public ItemBuilder withId(UUID id){
        this.id = id;
        return this;
    }

    public ItemBuilder withName(String name){
        this.name = name;
        return this;
    }

    public ItemBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public ItemBuilder withOriginalOwner(User user){
        this.originalOwner = user;
        return this;
    }

    public ItemBuilder reservedTo(User user){
        this.reservedBy = user;
        this.itemStatus = ItemStatus.RESERVED;
        this.reservedAt = LocalDateTime.now();
        return this;
    }

    public ItemBuilder donatedTo(User user){
        this.newOwner = user;
        this.itemStatus = ItemStatus.DONATED;
        this.donatedAt = LocalDateTime.now();
        return this;
    }

    public Item build(){
        return new Item(id, name, description, originalOwner, reservedBy, newOwner, itemStatus, createdAt, reservedAt, donatedAt);
    }
}
