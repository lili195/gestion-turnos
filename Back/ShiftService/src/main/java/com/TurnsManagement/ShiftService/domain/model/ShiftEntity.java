package com.TurnsManagement.ShiftService.domain.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(value = "shift")
@Getter
@Setter
@NoArgsConstructor
public class ShiftEntity {
    @Transient
    public static final String SEQUENCE_NAME = "shift_sequence";
    
    @Id
    private Long id;
    private String user;
    private LocalDate date;
    private String time;
    private String service;
    private String dependent;
    private String room;
    private String shiftName;
}
