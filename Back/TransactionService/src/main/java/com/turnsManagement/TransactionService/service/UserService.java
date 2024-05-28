package com.TurnsManagement.TransactionsServices.service;

import com.TurnsManagement.TransactionsServices.model.User;
import com.TurnsManagement.TransactionsServices.repository.UserRepository;

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
