package main.controller;

import main.model.user.User;
import main.model.user.UserRepository;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    /*
    TODO:
        -method to find user by ip. If he doesn't find show auth page, else go to chat
     */

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers(){
        return userService.getUsersList();
    }

    @PostMapping("/")
    public ResponseEntity<User> addUser(@PathVariable User user){
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        return userService.getUser(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateUser(User user, @PathVariable int id){
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable int id){
        return userService.deleteUser(id);
    }
}
