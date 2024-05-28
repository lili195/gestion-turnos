package com.turnsManagement.TransactionService.application.events;

import com.turnsManagement.TransactionService.domain.model.enums.UserStatus;

public record UserEvent(String username, String email, UserStatus userStatus) {
    
}
