package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    @RequestMapping("/init")
    public void init(){

    }

    @PostMapping("/auth")
    public boolean auth(){
        return true;
    }
}
