package main.controller;

import main.model.message.Message;
import main.model.message.MessageRepository;
import main.model.user.User;
import main.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/message")
    public List<Message> getMessages(){
        ArrayList<Message> messages = new ArrayList<>();
        messageRepository.findAll().forEach(m -> {
            messages.add(m);
        });
        return messages;
    }

    @PostMapping("/message")
    public ResponseEntity<Message> addMessage(@PathVariable Message newMessage){
        Optional<User> user = userRepository.findById(newMessage.getUser().getId());
        if(user.isPresent()){
            Message message = new Message(newMessage.getMessage(), LocalDateTime.now(), user.get());
            int id = messageRepository.save(message).getId();
            Optional<Message> optionalMessage = messageRepository.findById(id);
            if (optionalMessage.isPresent()){
                return ResponseEntity.ok(optionalMessage.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/message/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable int id){
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()){
            return ResponseEntity.ok(optionalMessage.get());
        } else return ResponseEntity.notFound().build();
    }

    @PatchMapping("/message/{id}")
    public ResponseEntity updateMessage(Message message, @PathVariable int id){
        Optional<Message> optionalMessage = messageRepository.findById(id);

        if(optionalMessage.isPresent()){
            Message updatedMessage = optionalMessage.get();
            if(message.getMessage() != null){
                updatedMessage.setMessage(message.getMessage());
                updatedMessage.setDateTime(LocalDateTime.now());
            }
            messageRepository.save(updatedMessage);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/message/{id}")
    public ResponseEntity deleteMessage(@PathVariable int id){
        messageRepository.deleteById(id);
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(!optionalMessage.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
