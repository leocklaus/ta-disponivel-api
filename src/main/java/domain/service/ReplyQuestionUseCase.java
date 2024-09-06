package domain.service;

import domain.entity.Question;
import domain.entity.Reply;
import domain.entity.User;
import domain.exception.QuestionNotFoundException;
import domain.repository.QuestionRepository;
import domain.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReplyQuestionUseCase {

    private final UserService userService;
    private final ReplyRepository repository;
    private final FAQService faqService;

    @Transactional
    public void reply(UUID questionId, String text){
        Question question = faqService
                .getQuestionByIdOrThrowsExceptionIfNotExists(questionId);
        User user = userService.getLoggedUserOrThrowsExceptionIfNotExists();

        Reply reply = new Reply(user, text, question);
        repository.save(reply);

    }

}
