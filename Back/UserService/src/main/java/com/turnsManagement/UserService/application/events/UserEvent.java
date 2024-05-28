package com.turnsManagement.UserService.application.events;

import com.turnsManagement.UserService.domain.model.enums.UserStatus;

public record UserEvent(String username, String email, UserStatus userStatus) {
    
}
