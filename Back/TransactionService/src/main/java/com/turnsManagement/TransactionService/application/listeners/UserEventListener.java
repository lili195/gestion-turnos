package com.turnsManagement.TransactionService.application.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.turnsManagement.TransactionService.application.events.UserEvent;
import com.turnsManagement.TransactionService.controller.TransactionController;
import com.turnsManagement.TransactionService.application.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserEventListener {

    @KafkaListener(topics = "users-topic")
    public void handleUsersNotifications(String message) {
        System.out.println("Json string recibido: \n" + message);
        var userEvent = JsonUtils.fromJson(message, UserEvent.class);

        log.info("Evento {} de usuario recibido para ususario: {} con email {} ",
        userEvent.userStatus(), userEvent.username(), userEvent.email());
    }


}
