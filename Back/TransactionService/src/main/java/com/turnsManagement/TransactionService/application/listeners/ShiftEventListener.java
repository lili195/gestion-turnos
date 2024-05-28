package com.turnsManagement.TransactionService.application.listeners;

import com.turnsManagement.TransactionService.application.events.ShiftEvent;
import com.turnsManagement.TransactionService.application.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShiftEventListener {
    @KafkaListener(topics = "shifts-topic")
    public void handleShiftsNotifications(String message) {
        System.out.println("Json string recibido: \n" + message);
        var shiftEvent = JsonUtils.fromJson(message, ShiftEvent.class);
        log.info("Evento {} de turno recibido para ususario: {} con dependiente {} y servicio {} ",
                shiftEvent.shiftStatus(), shiftEvent.user(), shiftEvent.dependent(), shiftEvent.service());
    }
}
