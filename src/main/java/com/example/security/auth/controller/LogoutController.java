package com.example.security.auth.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class LogoutController {
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        System.out.println("Logout!");
        return ResponseEntity.ok("Logout!");
    }
}
