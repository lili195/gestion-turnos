package com.TurnsManagement.TurnServices.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    private ServiceType type;
    private String room;
    private String dependent;
}
