package com.TurnsManagement.TransactionsServices.controller;

import com.TurnsManagement.TransactionsServices.model.Dependent;
import com.TurnsManagement.TransactionsServices.model.Transaction;
import com.TurnsManagement.TransactionsServices.model.TransactionRequestDTO;
import com.TurnsManagement.TransactionsServices.model.User;
import com.TurnsManagement.TransactionsServices.service.TransactionService;
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
}
