package com.example.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("")

    public String get(){
        return "GET :: /admin";
    }

    @PostMapping("")
    public String post(){
        return "POST :: /admin";
    }

    @PutMapping("")
    public String put(){
        return "PUT :: /admin";
    }

    @DeleteMapping("")
    public String delete(){
        return "DELETE :: /admin";
    }
    
}
