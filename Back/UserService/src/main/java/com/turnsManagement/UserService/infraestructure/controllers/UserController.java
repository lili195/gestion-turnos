package com.turnsManagement.UserService.infraestructure.controllers;

import com.turnsManagement.UserService.application.Services.UserService;
import com.turnsManagement.UserService.domain.model.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

//    @PostMapping("/addUser")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void addUser(@RequestBody UserRequest userRequest) {
//        System.out.println(userRequest);
//        this.userService.addUser(userRequest);
//    }

    @PostMapping("/addUser")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(Authentication authentication) {
        System.out.println("Solicitud de agregar usuario recibida");
        this.userService.addUser(buildUserRequest(authentication));
    }

    @GetMapping("/listUsers")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers() {
        return this.userService.getAllUsers();
    }

    private String getNameFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaim("name");
        }
        return "Unknown name";
    }

    private String getUsernameFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaim("preferred_username");
        }
        return "Unknown username";
    }

    private String getEmailFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaim("email");
        }
        return "Unknown email";
    }

    private UserRequest buildUserRequest(Authentication authentication) {
        String name = getNameFromAuthentication(authentication);
        String username = getUsernameFromAuthentication(authentication);
        String email = getEmailFromAuthentication(authentication);

        String inputData = String.format("{\"name\":\"%s\",\"username\":\"%s\",\"email\":\"%s\"}", name, username, email);

        System.out.println("Solicitud con datos: "+ inputData);

        return UserRequest.builder()
                .name(name)
                .username(username)
                .email(email)
                .build();
    }
}
