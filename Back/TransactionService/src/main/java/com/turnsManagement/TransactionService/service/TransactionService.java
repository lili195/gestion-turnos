package com.TurnsManagement.TransactionsServices.service;

import com.TurnsManagement.TransactionsServices.model.Transaction;
import com.TurnsManagement.TransactionsServices.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
        private final TransactionRepository TransactionRepository;

    public TransactionService(TransactionRepository TransactionRepository) {
        this.TransactionRepository = TransactionRepository;
    }

    @Transactional("transactionManager")
    public Transaction add(Transaction Transaction) {
        return TransactionRepository.save(Transaction);
    }
}
