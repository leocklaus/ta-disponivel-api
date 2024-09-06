package domain.service;

import api.dto.ItemInput;
import domain.entity.Item;
import domain.entity.User;
import domain.exception.UserNotAuthorizedException;
import domain.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManageItemUseCase {

    private final UserService userService;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @Transactional
    public Item addNewITem(ItemInput input){
        User user = userService.getLoggedUserOrThrowsExceptionIfNotExists();

        Item item = new Item(input, user);

        item = itemRepository.save(item);

        return item;

    }

    @Transactional
    public Item updateItem(UUID itemId, ItemInput input){

        Item item = itemService.getItemByIdOrThrowsException(itemId);

        if(loggedUserDoesNotOwnItem(item)){
            throw new UserNotAuthorizedException();
        }

        updateItem(item, input);

        return itemRepository.save(item);

    }

    @Transactional
    public void deleteItem(UUID itemId){

        Item item = itemService.getItemByIdOrThrowsException(itemId);

        if(loggedUserDoesNotOwnItem(item)){
            throw new UserNotAuthorizedException();
        }

        itemRepository.delete(item);
    }

    private boolean loggedUserDoesNotOwnItem(Item item){

        User loggedUser = userService.getLoggedUserOrThrowsExceptionIfNotExists();

        return item.getOriginalOwner().equals(loggedUser);
    }

    private void updateItem(Item item, ItemInput itemInput){
        item.setName(itemInput.name());
        item.setDescription(itemInput.description());
    }

}
