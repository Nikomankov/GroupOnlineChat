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
        String ip = request.getRemoteAddr().trim();
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Optional<User> optionalUser = userRepository.findByIp(ip);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setSessionId(sessionId);
            user.setName(name);
            userRepository.save(user);
        } else {
            User user = new User(name, ip, sessionId);
            userRepository.save(user);
        }
        return ResponseEntity.ok().build();
    }
}
