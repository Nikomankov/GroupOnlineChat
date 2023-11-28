package main.service;

import main.model.message.Message;
import main.model.message.MessageRepository;
import main.model.user.User;
import main.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public ResponseEntity<List<Message>> getNextOlderMessages(LocalDateTime last) {
        System.out.println("last from Impl = " + last);
        List<Message> messages = messageRepository.getMessagesOlderThanLast(last);
        if(messages.size()>0){
            return ResponseEntity.ok(messages);
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        if(messages.size()>0){
            return ResponseEntity.ok(messages);
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Message> saveMessage(String messageContent) {
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        Optional<User> user = userRepository.findBySessionId(sessionId);
        Message message = new Message(messageContent);
        if(user.isPresent()){
            message.setDateTime(LocalDateTime.now());
            message.setUser(user.get());
            int id = messageRepository.save(message).getId();
            Optional<Message> optionalMessage = messageRepository.findById(id);
            if (optionalMessage.isPresent()){
                return ResponseEntity.ok(optionalMessage.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity deleteMessage(int id) {
        messageRepository.deleteById(id);
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(!optionalMessage.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Override
    public ResponseEntity<Message> getMessage(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()){
            return ResponseEntity.ok(optionalMessage.get());
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity updateMessage(Message message, int id) {
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
}
