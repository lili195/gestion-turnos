package com.turnsManagement.TransactionService.application.listeners;

import com.turnsManagement.TransactionService.application.events.ShiftEvent;
import com.turnsManagement.TransactionService.application.utils.JsonUtils;
import com.turnsManagement.TransactionService.controller.TransactionController;
import com.turnsManagement.TransactionService.model.Dependent;
import com.turnsManagement.TransactionService.model.Transaction;
import com.turnsManagement.TransactionService.model.TransactionRequestDTO;
import com.turnsManagement.TransactionService.model.User;
import com.turnsManagement.TransactionService.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@Slf4j
public class ShiftEventListener {
    private final TransactionController transactionController;

    @Autowired
    public ShiftEventListener(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    @KafkaListener(topics = "shifts-topic")
    public void handleShiftsNotifications(String message) {
        try {
            System.out.println("Json string recibido: \n" + message);
            var shiftEvent = JsonUtils.fromJson(message, ShiftEvent.class);
            log.info("Evento {} de turno recibido para ususario: {} con dependiente {} y servicio {} ",
                    shiftEvent.shiftStatus(), shiftEvent.user(), shiftEvent.dependent(), shiftEvent.service());
            transactionController.add(new TransactionRequestDTO(shiftEvent.user(),shiftEvent.service(),shiftEvent.dependent(),String.valueOf(shiftEvent.shiftStatus())));
        } catch (Exception e) {
            log.error("Error al procesar el mensaje del evento de turno: {}", e.getMessage(), e);
        }
    }


}
