package com.turnsManagement.UserService.controllers;

import com.turnsManagement.UserService.Services.UserService;
import com.turnsManagement.UserService.model.dtos.UserRequest;
import com.turnsManagement.UserService.model.dtos.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/addUser")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody UserRequest userRequest) {
        System.out.println(userRequest);
        this.userService.addUser(userRequest);
    }

    @GetMapping("/listUsers")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers() {
        return this.userService.getAllUsers();
    }
}
