package com.turnsManagement.TransactionService.application.events;
import com.turnsManagement.TransactionService.domain.model.enums.ShiftStatus;

public record ShiftEvent(String user, String service, String dependent, ShiftStatus shiftStatus) {

}
