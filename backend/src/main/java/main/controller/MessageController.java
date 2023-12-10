package main.controller;

import main.model.message.Message;
import main.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/message.get")
    @SendTo("/topic/public")
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }

    @MessageMapping("/message.add")
    @SendTo("/topic/public")
    public Message addMessage(@Payload Message message){
        return messageService.sendMessage(message);
    }

    @MessageMapping("/message.get/{id}")
    @SendTo("/topic/public")
    public Message getMessage(@DestinationVariable int id){
        return messageService.getMessage(id);
    }

    @MessageMapping("/message.update/{id}")
    @SendTo("/topic/public")
    public Message updateMessage(@Payload Message message, @DestinationVariable int id){
        return messageService.updateMessage(message,id);
    }

    @MessageMapping("/message.delete/{id}")
    @SendTo("/topic/public")
    public Message deleteMessage(@DestinationVariable int id){
        return messageService.deleteMessage(id);
    }

    @MessageMapping("/message.delete-forever/{id}")
    @SendTo("/topic/public")
    public boolean deleteMessageForever(@DestinationVariable int id){
        return messageService.deleteMessageForever(id);
    }

    @MessageMapping("/message.getOlder")
    @SendTo("/topic/public")
    public List<Message> getNextOlderMessages(@Payload String last){
        LocalDateTime converted = LocalDateTime.parse(last, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnn"));;
        return messageService.getNextOlderMessages(converted);
    }



}
