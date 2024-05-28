package com.turnsManagement.TransactionService.repository;

import com.turnsManagement.TransactionService.model.Transaction;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TransactionRepository extends Neo4jRepository <Transaction, String> {
    
}
