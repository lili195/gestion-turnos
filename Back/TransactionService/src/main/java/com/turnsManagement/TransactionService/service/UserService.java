package com.turnsManagement.TransactionService.service;

import com.turnsManagement.TransactionService.model.User;
import com.turnsManagement.TransactionService.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
        private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional("transactionManager")
    public User add(User user) {
        return userRepository.save(user);
    }
}
