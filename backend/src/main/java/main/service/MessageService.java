package main.service;

import main.model.message.Message;
import org.springframework.http.ResponseEntity;


import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {

    ResponseEntity<Message> saveMessage(String messageContent);
    ResponseEntity deleteMessage(int id);
    ResponseEntity<Message> getMessage(int id);
    ResponseEntity updateMessage(Message message, int id);
    ResponseEntity<List<Message>> getNextOlderMessages(LocalDateTime last);
    ResponseEntity<List<Message>> getAllMessages();

}
