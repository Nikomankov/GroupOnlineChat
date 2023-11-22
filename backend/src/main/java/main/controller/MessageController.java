package main.controller;

import main.model.message.Message;
import main.model.message.MessageRepository;
import main.model.user.User;
import main.model.user.UserRepository;
import main.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MessageController {

    /*
    TODO:
        -get messages from a list in a specific range (i-j)
        -get messages from a specific conversation

     */


    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    private MessageService messageService;

    @GetMapping("/message")
    public ResponseEntity<List<Message>> getMessages(){
        return messageService.getMessages();
    }

    @GetMapping("/message/{last}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable LocalDateTime last){
        return messageService.getMessages(last);
    }

    @PostMapping("/message")
    public ResponseEntity<Message> addMessage(Message message){
        return messageService.saveMessage(message);
    }

    @GetMapping("/message/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable int id){
        return messageService.getMessage(id);
    }

    @PatchMapping("/message/{id}")
    public ResponseEntity updateMessage(Message message, @PathVariable int id){
        return messageService.updateMessage(message,id);
    }

    @DeleteMapping("/message/{id}")
    public ResponseEntity deleteMessage(@PathVariable int id){
        return messageService.deleteMessage(id);
    }
}
