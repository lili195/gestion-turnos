package com.turnsManagement.UserService.domain.repositories;

import com.turnsManagement.UserService.domain.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
