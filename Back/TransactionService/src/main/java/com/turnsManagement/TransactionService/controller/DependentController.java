package com.TurnsManagement.TransactionsServices.controller;

import com.TurnsManagement.TransactionsServices.model.Dependent;
import com.TurnsManagement.TransactionsServices.service.DependentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/typeTransaction")
public class DependentController {
        private final DependentService typTranService;

    public DependentController(DependentService typTranService) {
        this.typTranService = typTranService;
    }
    
    @PostMapping("/add")
    public ResponseEntity<Dependent> add(@RequestBody Dependent typeTransaction){
        Dependent newTyTrans = typTranService.add(typeTransaction);
        return new ResponseEntity<>(newTyTrans, HttpStatus.CREATED);
    }
}
