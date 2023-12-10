package main.controller;

import jakarta.servlet.http.HttpServletRequest;
import main.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/")
@Controller
@RequestMapping("/")
public class DefaultController {

//    @Autowired
//    DefaultService defaultService;
//
//    @GetMapping
//    public ResponseEntity init(HttpServletRequest request){
//        return defaultService.init(request);
//    }
//
//    @PostMapping
//    public ResponseEntity auth(@RequestBody String name, HttpServletRequest request){
//        return defaultService.auth(name, request);
//    }
}
