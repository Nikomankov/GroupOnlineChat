package main.controller;

import main.model.user.User;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@Controller
@RequestMapping("/user")
public class UserController {

    /*
    TODO:
    */

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(){
        return userService.getUsersList();
    }

    @PostMapping("/")
    public ResponseEntity<User> addUser(@RequestBody User user){
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
