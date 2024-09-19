package domain.service;

import api.dto.MessageInput;
import domain.entity.Message;
import domain.entity.User;
import domain.exception.UserNotAuthorizedException;
import domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SendMessageUseCase {

    private final UserService userService;
    private final MessageRepository repository;

    public Message send(MessageInput messageInput){
        User from = userService.getLoggedUserOrThrowsExceptionIfNotExists();
        User to = userService.getUserByIdOrThrowsExceptionIfNotExists(messageInput.userId());

        if(isUserSendingAMessageToHimself(from, to)){
            throw new UserNotAuthorizedException();
        }

        Message message = new Message(from, to, messageInput.message());

        return repository.save(message);

    }

    private boolean isUserSendingAMessageToHimself(User from, User to){
        return from.equals(to);
    }

}
