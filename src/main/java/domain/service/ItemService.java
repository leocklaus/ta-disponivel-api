package domain.service;

import domain.entity.Item;
import domain.exception.ItemNotFoundException;
import domain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item getItemByIdOrThrowsException(UUID id){
        return itemRepository.findById(id)
                .orElseThrow(()-> new ItemNotFoundException(id));

    }


}
