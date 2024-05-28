package com.turnsManagement.TransactionService.model;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TransactionRequestDTO {
    private String user;
    private String service;
    private String dependent;
    private String shiftStatus;

    public TransactionRequestDTO(String user, String service, String dependent, String s) {
        this.user = user;
        this.service = service;
        this.dependent = dependent;
        this.shiftStatus = s;
    }
}
