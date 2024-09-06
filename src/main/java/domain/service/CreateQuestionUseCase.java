package domain.service;

import domain.entity.Item;
import domain.entity.Question;
import domain.entity.User;
import domain.exception.ItemNotAvailableException;
import domain.exception.UserNotAuthorizedException;
import domain.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateQuestionUseCase {

    private final UserService userService;
    private final ItemService itemService;
    private final QuestionRepository questionRepository;

    @Transactional
    public void create(UUID itemId, String text){
        User user = userService.getLoggedUserOrThrowsExceptionIfNotExists();
        Item item = itemService.getItemByIdOrThrowsException(itemId);

        if(item.isDonated()){
            throw new ItemNotAvailableException();
        }

        if(questionCreatorOwnsProduct(user, item)){
            throw new UserNotAuthorizedException();
        }

        Question question = new Question(user, text, item);

        questionRepository.save(question);
    }

    private boolean questionCreatorOwnsProduct(User user, Item item){
        return item.getOriginalOwner().equals(user);
    }

}
