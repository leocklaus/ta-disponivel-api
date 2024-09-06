package domain.service;

import domain.entity.Question;
import domain.exception.QuestionNotFoundException;
import domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FAQService {

    private final QuestionRepository questionRepository;

    public void getItemFAQPaged(){}

    public Question getQuestionByIdOrThrowsExceptionIfNotExists(UUID questionId){
        return questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
    }

}
