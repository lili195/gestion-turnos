package com.TurnsManagement.TurnServices.infraestructure.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TurnsManagement.TurnServices.application.TurnService;
import com.TurnsManagement.TurnServices.domain.models.Service;
import com.TurnsManagement.TurnServices.domain.models.ServiceType;

@RestController
@RequestMapping("/api/turn")
public class TurnContoller {
    private final TurnService turnService;

    public TurnContoller() {
        this.turnService = new TurnService();
    }

    @GetMapping("/services")
    public List<Service> listServices() {
        return turnService.listServices();
    }

    @GetMapping("/services/{type}")
    public Map<String, String> getServiceInfo(@PathVariable ServiceType type) {
        Optional<Service> serviceOpt = turnService.getServiceInfo(type);
        if (serviceOpt.isPresent()) {
            Service service = serviceOpt.get();
            Map<String, String> response = new HashMap<>();
            response.put("service", type.toString()); // Agregar el nombre del servicio
            response.put("dependent", service.getDependent());
            response.put("room", service.getRoom());
            return response;
        } else {
            throw new RuntimeException("Service not found");
        }
    }
}
