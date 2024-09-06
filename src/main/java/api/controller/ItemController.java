package api.controller;

import api.dto.ItemInput;
import domain.entity.Item;
import domain.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ManageItemUseCase manageItemUseCase;
    private final ItemService itemService;
    private final ReserveItemUseCase reserveItemUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;
    private final DonateItemUseCase donateItemUseCase;

    public ItemController(ManageItemUseCase manageItemUseCase, ItemService itemService, ReserveItemUseCase reserveItemUseCase, CancelReservationUseCase cancelReservationUseCase, DonateItemUseCase donateItemUseCase) {
        this.manageItemUseCase = manageItemUseCase;
        this.itemService = itemService;
        this.reserveItemUseCase = reserveItemUseCase;
        this.cancelReservationUseCase = cancelReservationUseCase;
        this.donateItemUseCase = donateItemUseCase;
    }

    @PostMapping("/add")
    public ResponseEntity<Item> addItem(@RequestBody ItemInput input){
        Item item = manageItemUseCase.addNewITem(input);
        URI uri = URI.create("/api/v1/item/" + item.getId());
        return  ResponseEntity.created(uri).body(item);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable UUID id){
        Item item = itemService.getItemByIdOrThrowsException(id);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID id){
        manageItemUseCase.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateItem(@PathVariable UUID id, @RequestBody ItemInput input){
        Item item = manageItemUseCase.updateItem(id, input);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{itemId}/reserve/{userId}")
    public ResponseEntity<?> reserveItem(@PathVariable UUID itemId, @PathVariable UUID userId){
        reserveItemUseCase.reserve(itemId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{itemId}/reserve")
    public ResponseEntity<?> cancelReservation(@PathVariable UUID itemId){
        cancelReservationUseCase.cancel(itemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{itemId}/donate/{userId}")
    public ResponseEntity<?> donateItem(@PathVariable UUID itemId, @PathVariable UUID userId){
        donateItemUseCase.donate(itemId, userId);
        return ResponseEntity.noContent().build();
    }


}
