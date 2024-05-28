package com.turnsManagement.TransactionService.repository;

import com.turnsManagement.TransactionService.model.Dependent;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DependentRepository extends Neo4jRepository <Dependent, String> {
    
}
