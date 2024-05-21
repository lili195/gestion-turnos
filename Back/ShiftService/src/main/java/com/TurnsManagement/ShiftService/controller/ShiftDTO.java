package com.TurnsManagement.ShiftService.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShiftDTO {
    private String user;
    private String date;
    private String time;
    private String service;
    private String dependent;
    private String room;
}
