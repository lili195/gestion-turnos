package com.turnsManagement.UserService.Services;

import com.turnsManagement.UserService.model.dtos.UserRequest;
import com.turnsManagement.UserService.model.dtos.UserResponse;
import com.turnsManagement.UserService.model.entities.User;
import com.turnsManagement.UserService.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;
    public void addUser(UserRequest userRequest){
        Optional<User> existingUser = userRepo.findByEmail(userRequest.getEmail());
        if (existingUser.isPresent()) {
            System.out.println("User with email " + userRequest.getEmail() + " already exists.");
            return;
        }

        // If user does not exist, proceed to add
        var user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();
        userRepo.save(user);
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
                .email(user.getEmail())
                .build();
    }
}
