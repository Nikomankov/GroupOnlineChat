package main.service;

import main.model.message.Message;
import main.model.user.User;
import org.springframework.http.ResponseEntity;


import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {

    public ResponseEntity<Message> saveMessage(Message message);
    public ResponseEntity deleteMessage(int id);
    public ResponseEntity<Message> getMessage(int id);
    public ResponseEntity updateMessage(Message message, int id);
    public ResponseEntity<List<Message>> getMessages(LocalDateTime last);
    public ResponseEntity<List<Message>> getMessages();
}
