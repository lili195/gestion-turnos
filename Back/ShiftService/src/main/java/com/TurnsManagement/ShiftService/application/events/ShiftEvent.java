package com.TurnsManagement.ShiftService.application.events;

import com.TurnsManagement.ShiftService.domain.model.enums.ShiftStatus;

public record ShiftEvent(String user, String service, String dependent, ShiftStatus shiftStatus) {

}
