package com.turnsManagement.TransactionService.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Data
@NoArgsConstructor
@Node("Transaction")
public class Transaction {
    @Id @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    private String nameService, shiftStatus;

    @Relationship(type = "IN_DEPENDENT", direction = Relationship.Direction.INCOMING)
    private Dependent dependent;

    @Relationship(type = "IN_USER", direction = Relationship.Direction.INCOMING)
    private User user;

 
}
