package com.turnsManagement.TransactionService.repository;

import com.turnsManagement.TransactionService.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository <User, String> {
    
}
