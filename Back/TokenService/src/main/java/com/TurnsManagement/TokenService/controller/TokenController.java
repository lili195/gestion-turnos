package com.TurnsManagement.TokenService.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {
    private String myToken = "";

    @PostMapping("/received")
    public String receivedToken(@RequestBody String token) {
       myToken = token;
       return "Token received";
    }

    @GetMapping("/getToken")
    public String getToken(){
        return myToken;
    }
}

