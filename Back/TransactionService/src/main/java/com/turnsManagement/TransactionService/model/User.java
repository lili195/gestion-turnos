package com.turnsManagement.TransactionService.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Data
@NoArgsConstructor
@Node("User")
public class User {
    @Id @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    private String nameUser;


}
