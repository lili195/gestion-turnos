package com.turnsManagement.TransactionService.repository;

import com.TurnsManagement.TransactionsServices.model.Dependent;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DependentRepository extends Neo4jRepository <Dependent, String> {
    
}
