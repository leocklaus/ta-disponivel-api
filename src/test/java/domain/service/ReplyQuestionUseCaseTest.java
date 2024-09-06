package domain.service;

import builders.UserBuilder;
import domain.entity.Question;
import domain.entity.User;
import domain.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyQuestionUseCaseTest {

    @Mock
    UserService userService;

    @Mock
    ReplyRepository repository;

    @Mock
    FAQService faqService;

    @InjectMocks
    ReplyQuestionUseCase replyQuestionUseCase;

    @Test
    void shouldReplyToQuestion() {

        Question question = new Question();
        question.setId(UUID.randomUUID());

        User user = new UserBuilder().build();

        doReturn(user).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();
        doReturn(question).when(faqService).getQuestionByIdOrThrowsExceptionIfNotExists(any());

        replyQuestionUseCase.reply(question.getId(), "text");

        verify(repository, times(1)).save(any());

    }
}