package com.TurnsManagement.TransactionsServices.service;

import com.TurnsManagement.TransactionsServices.model.Dependent;
import com.TurnsManagement.TransactionsServices.repository.DependentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DependentService {
        private final DependentRepository typeTransactionRepository;

    public DependentService(DependentRepository typeTransactionRepository) {
        this.typeTransactionRepository = typeTransactionRepository;
    }

    @Transactional("transactionManager")
    public Dependent add(Dependent typeTransaction) {
        return typeTransactionRepository.save(typeTransaction);
    }
}
