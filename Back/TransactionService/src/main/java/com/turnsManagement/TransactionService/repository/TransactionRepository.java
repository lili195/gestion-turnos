package com.turnsManagement.TransactionService.repository;

import com.TurnsManagement.TransactionsServices.model.Transaction;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TransactionRepository extends Neo4jRepository <Transaction, String> {
    
}
