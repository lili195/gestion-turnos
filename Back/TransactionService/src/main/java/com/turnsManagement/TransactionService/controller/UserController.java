package com.turnsManagement.TransactionService.controller;

import com.turnsManagement.TransactionService.application.listeners.ShiftEventListener;
import com.turnsManagement.TransactionService.model.User;
import com.turnsManagement.TransactionService.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
        private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user){
        User newUser = userService.add(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
