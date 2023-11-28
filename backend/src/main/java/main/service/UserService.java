package main.service;

import main.model.user.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<List<User>> getUsersList();
    ResponseEntity<User> saveUser(User user);
    ResponseEntity deleteUser(int id);
    ResponseEntity<User> getUser(int id);
    ResponseEntity updateUser(User user, int id);
}
