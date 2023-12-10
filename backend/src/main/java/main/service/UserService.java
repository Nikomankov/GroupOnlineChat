package main.service;

import main.model.message.Message;
import main.model.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.List;

public interface UserService {
//    public ResponseEntity<List<User>> getUsersList();
//    public ResponseEntity<User> saveUser(User user);
//    public ResponseEntity deleteUser(int id);
//    public ResponseEntity<User> getUser(int id);
//    public ResponseEntity updateUser(User user, int id);

    public Message addUser(Message message, SimpMessageHeaderAccessor headerAccessor);
}
