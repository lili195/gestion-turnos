package com.turnsManagement.TransactionService.controller;

import com.turnsManagement.TransactionService.model.Dependent;
import com.turnsManagement.TransactionService.model.Transaction;
import com.turnsManagement.TransactionService.model.TransactionRequestDTO;
import com.turnsManagement.TransactionService.model.User;
import com.turnsManagement.TransactionService.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Transaction")
public class TransactionController {
        private final TransactionService tranService;

    public TransactionController(TransactionService tranService) {
        this.tranService = tranService;
    }

    @PostMapping("/add")
    public ResponseEntity<Transaction> add(@RequestBody TransactionRequestDTO transactionRequestDTO){
        User user = new User();
        user.setNameUser(transactionRequestDTO.getUser());

        Dependent dependent = new Dependent();
        dependent.setNameDependent(transactionRequestDTO.getDependent());

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setNameService(transactionRequestDTO.getService());
        transaction.setDependent(dependent);
        transaction.setShiftStatus(transactionRequestDTO.getShiftStatus());

        Transaction newTrans = tranService.add(transaction);
        return new ResponseEntity<>(newTrans, HttpStatus.CREATED);
    }
    public void processShiftMessage(String message) {
        // Aquí puedes agregar la lógica para procesar el mensaje como lo desees
        System.out.println("Mensaje del evento de turno recibido en el controlador: " + message);
    }
}
