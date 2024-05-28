package com.turnsManagement.UserService.application.Services;

import com.turnsManagement.UserService.application.events.UserEvent;
import com.turnsManagement.UserService.application.utils.JsonUtils;
import com.turnsManagement.UserService.domain.model.dtos.*;
import com.turnsManagement.UserService.domain.model.entities.User;
import com.turnsManagement.UserService.domain.model.enums.UserStatus;
import com.turnsManagement.UserService.domain.repositories.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;
    private final KafkaTemplate<String, String> kafkaTemplate;


    public void addUser(UserRequest userRequest){
        Optional<User> existingUser = userRepo.findByEmail(userRequest.getEmail());
        if (existingUser.isPresent()) {
            System.out.println("User with email " + userRequest.getEmail() + " already exists.");
            this.kafkaTemplate.send("users-topic", JsonUtils.toJson(
                new UserEvent(userRequest.getUsername(), userRequest.getEmail(), UserStatus.LOGEADO)));
            return;
        }

        // If user does not exist, proceed to add
        var user = User.builder()
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .build();

        var savedUser = userRepo.save(user);
        this.kafkaTemplate.send("users-topic", JsonUtils.toJson(
            new UserEvent(savedUser.getUsername(), savedUser.getEmail(), UserStatus.REGISTRADO)));

        log.info("User added: {}", user);
    }

    public List<UserResponse> getAllUsers() {
        var users = userRepo.findAll();
        return users.stream().map(this::mapToUserResponse).toList();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
