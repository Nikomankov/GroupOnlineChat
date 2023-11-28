package main.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface DefaultService {
    ResponseEntity init(HttpServletRequest request);
    ResponseEntity auth(String name, HttpServletRequest request);
}
