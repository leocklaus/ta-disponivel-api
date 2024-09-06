package domain.service;

import builders.ItemBuilder;
import builders.UserBuilder;
import domain.entity.Item;
import domain.entity.Question;
import domain.entity.User;
import domain.exception.ItemNotAvailableException;
import domain.exception.UserNotAuthorizedException;
import domain.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateQuestionUseCaseTest {

    @Mock
    UserService userService;
    @Mock
    ItemService itemService;
    @Mock
    QuestionRepository questionRepository;
    @InjectMocks
    CreateQuestionUseCase createQuestionUseCase;
    @Captor
    ArgumentCaptor<Question> questionArgumentCaptor;

    @Test
    void shouldCreateQuestion() {

        User itemOwner = new UserBuilder().build();

        Item item = new ItemBuilder()
                .withOriginalOwner(itemOwner)
                .build();

        User questionUser = new UserBuilder().build();

        String text = "text";

        Question question = new Question(questionUser, text, item);

        doReturn(questionUser).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();
        doReturn(item).when(itemService).getItemByIdOrThrowsException(item.getId());

        createQuestionUseCase.create(item.getId(), "text");

        verify(questionRepository, times(1)).save(questionArgumentCaptor.capture());

        assertThat(questionArgumentCaptor.getValue().getText())
                .isEqualTo(text);

        assertThat(questionArgumentCaptor.getValue().getUser())
                .isEqualTo(questionUser);

        assertThat(questionArgumentCaptor.getValue().getItem())
                .isEqualTo(item);
    }

    @Test
    void shouldThrowExceptionIfItemIsAlreadyDonated(){
        User itemOwner = new UserBuilder().build();

        Item item = new ItemBuilder()
                .donatedTo(itemOwner)
                .build();

        User questionUser = new UserBuilder().build();

        String text = "text";

        doReturn(questionUser).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();
        doReturn(item).when(itemService).getItemByIdOrThrowsException(item.getId());

        assertThrows(ItemNotAvailableException.class, ()->{
            createQuestionUseCase.create(item.getId(), text);
        });
    }

    @Test
    void itemOwnerShouldNotBeAbleToCreateAQuestion(){
        User itemOwner = new UserBuilder().build();

        Item item = new ItemBuilder()
                .withOriginalOwner(itemOwner)
                .build();


        String text = "text";

        doReturn(itemOwner).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();
        doReturn(item).when(itemService).getItemByIdOrThrowsException(item.getId());

        assertThrows(UserNotAuthorizedException.class, ()->{
            createQuestionUseCase.create(item.getId(), text);
        });
    }
}