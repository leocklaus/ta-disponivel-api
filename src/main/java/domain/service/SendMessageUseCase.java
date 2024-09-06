package domain.service;

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

    private void send(String msg, UUID toUser){
        User from = userService.getLoggedUserOrThrowsExceptionIfNotExists();
        User to = userService.getUserByIdOrThrowsExceptionIfNotExists(toUser);

        if(isUserSendingAMessageToHimself(from, to)){
            throw new UserNotAuthorizedException();
        }

        Message message = new Message(from, to, msg);

        repository.save(message);

    }

    private boolean isUserSendingAMessageToHimself(User from, User to){
        return from.equals(to);
    }

}
