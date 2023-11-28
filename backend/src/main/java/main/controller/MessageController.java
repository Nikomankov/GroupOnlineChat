package main.controller;

import main.model.message.Message;
import main.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    /*
    TODO:
        -get messages from a list in a specific range (i-j)
        -get messages from a specific conversation

     */

    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public ResponseEntity<List<Message>> getAllMessages(){
        return messageService.getMessages();
    }

    @GetMapping("/{last}")
    public ResponseEntity<List<Message>> getNextOlderMessages(@PathVariable LocalDateTime last){
        return messageService.getMessages(last);
    }

    @PostMapping("/")
    public ResponseEntity<Message> addMessage(@RequestBody String messageContent){
        return messageService.saveMessage(messageContent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable int id){
        return messageService.getMessage(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateMessage(@RequestBody Message message, @PathVariable int id){
        return messageService.updateMessage(message,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMessage(@PathVariable int id){
        return messageService.deleteMessage(id);
    }
}
