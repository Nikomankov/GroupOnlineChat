package main.controller;

import main.model.user.User;
import main.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller("/user/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    /*
    TODO:
        -method to find user by ip. If he doesn't find show auth page, else go to chat
     */

    @GetMapping("/")
    public List<User> getUsers(){
        Iterable<User> iterableUsers = userRepository.findAll();
        ArrayList<User> users = new ArrayList<>();
        iterableUsers.forEach(u -> {
            users.add(u);
        });
        return users;
    }

    @PostMapping("/")
    public ResponseEntity<User> addUser(@PathVariable User newUser){
        User user = new User(newUser.getName(), newUser.getIp(), newUser.getSessionId());
        int id = userRepository.save(user).getId();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        } else return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateUser(User user, @PathVariable int id){
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            User updatedUser = optionalUser.get();
            if(user.getIp() != null){
                updatedUser.setIp(user.getIp());
            }
            if(user.getName() != null){
                updatedUser.setName(user.getName());
            }
            if(user.getSessionId() != null){
                updatedUser.setSessionId(user.getSessionId());
            }
            userRepository.save(updatedUser);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
