package domain.service;

import domain.entity.Message;
import domain.entity.User;
import domain.exception.MessageNotFoundException;
import domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final UserService userService;
    private final MessageRepository messageRepository;

    public void setMessageAsRead(Long messageId){
        Message message = getMessageById(messageId);
        User user = userService.getLoggedUserOrThrowsExceptionIfNotExists();

        message.setAsReadBy(user);

        messageRepository.save(message);

    }

    public boolean isThereNewMessages(Long lastMessageReceivedId){
        User user = userService.getLoggedUserOrThrowsExceptionIfNotExists();

        //query db

        return true;
    }

    public void getNewMessages(Long lastMessageReceivedId){
        User user = userService.getLoggedUserOrThrowsExceptionIfNotExists();

        List<Message> newMessages = messageRepository.getNewMessages(user.getId(), lastMessageReceivedId);
    }

    public void getUserChats(){
        User user = userService.getLoggedUserOrThrowsExceptionIfNotExists();

    }

    public void getChatMessages(UUID secondUserId){
        User loggedUser = userService.getLoggedUserOrThrowsExceptionIfNotExists();
        User talkingTo = userService.getUserByIdOrThrowsExceptionIfNotExists(secondUserId);

        //query db
    }

    public Message getMessageById(Long id){
        return messageRepository.findById(id)
                .orElseThrow(MessageNotFoundException::new);
    }

}
