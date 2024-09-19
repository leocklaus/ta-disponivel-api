package api.controller;

import api.dto.MessageInput;
import domain.entity.Message;
import domain.service.MessageService;
import domain.service.SendMessageUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    private final SendMessageUseCase sendMessageUseCase;
    private final MessageService messageService;

    public MessageController(SendMessageUseCase sendMessageUseCase, MessageService messageService) {
        this.sendMessageUseCase = sendMessageUseCase;
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<?> getUserChats(){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/chat/{secondUserId}")
    public ResponseEntity<?> getChatWithUser(@PathVariable UUID secondUserID){
        List<Message> chatMessages = messageService.getChatMessages(secondUserID);

        return ResponseEntity.ok(chatMessages);
    }

    @GetMapping(value = "/new")
    public ResponseEntity<?> getNewMessages(@RequestHeader("X-Last-Message-Id") Long id){

        List<Message> newMessages = messageService.getNewMessages(id);

        if(newMessages.isEmpty()){
            return ResponseEntity.status(304).build();
        }

        return ResponseEntity.ok(newMessages);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id){
        Message message = messageService.getMessageById(id);
        return ResponseEntity.ok(message);
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(@Valid @RequestBody MessageInput messageInput){

        Message message = sendMessageUseCase.send(messageInput);

        URI uri = URI.create("/api/v1/message/" + message.getId());

        return ResponseEntity.created(uri).build();
    }



}
