package main.service;

import jakarta.servlet.http.HttpServletRequest;
import main.model.user.User;
import main.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

@Service
public class DefaultServiceImpl implements DefaultService{
    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity init(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr().trim();
        System.out.println("ip = " + ipAddress);
        Optional<User> userOptional = userRepository.findByIp(ipAddress);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        } else return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity auth(String name, HttpServletRequest request) {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        User user = new User(name, request.getRemoteAddr().trim(), sessionId);
        userRepository.save(user);
        if(userRepository.findById(user.getId()).isPresent()){
            return ResponseEntity.ok().build();
        } else return ResponseEntity.notFound().build();
    }
}
