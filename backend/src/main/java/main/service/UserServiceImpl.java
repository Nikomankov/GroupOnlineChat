package main.service;

import main.model.user.User;
import main.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public ResponseEntity<List<User>> getUsersList() {
        Iterable<User> iterableUsers = userRepository.findAll();
        ArrayList<User> users = new ArrayList<>();
        iterableUsers.forEach(u -> {
            users.add(u);
        });
        if(users.size()>0){
           return ResponseEntity.ok(users);
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<User> saveUser(User user) {
        int id = userRepository.save(user).getId();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity deleteUser(int id) {
        userRepository.deleteById(id);
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Override
    public ResponseEntity<User> getUser(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity updateUser(User user, int id) {
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
}
