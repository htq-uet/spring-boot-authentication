package com.example.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
//    @GetMapping("/{id}")
//    public String getUserById(@PathVariable Integer id) {
//        System.out.println("User with id " + id);
//        return "User with id " + id;
//    }
    @GetMapping("")
    public String get(){
        return "GET :: /user";
    }

    @PostMapping("")
    public String post(){
        return "POST :: /user";
    }

    @PutMapping("")
    public String put(){
        return "PUT :: /user";
    }

    @DeleteMapping("")
    public String delete(){
        return "DELETE :: /user";
    }


}
