package main.controller;

import main.model.message.Message;
import main.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@Controller
@RequestMapping("/message")
public class MessageController {

    /*
    TODO:
        -get messages from a specific conversation
     */

    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public ResponseEntity<List<Message>> getAllMessages(){
        return messageService.getAllMessages();
    }

    @GetMapping("/nextOlder")
    public ResponseEntity<List<Message>> getNextOlderMessages(@RequestParam String last){
        LocalDateTime converted = LocalDateTime.parse(last, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnn"));;
        return messageService.getNextOlderMessages(converted);
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
