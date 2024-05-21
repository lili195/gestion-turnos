package com.TurnsManagement.TurnServices.domain.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.TurnsManagement.TurnServices.domain.models.Service;
import com.TurnsManagement.TurnServices.domain.models.ServiceType;

public class ServiceRepository {
    private final List<Service> services;

    public ServiceRepository() {
        services = new ArrayList<>();
        services.add(new Service(ServiceType.CONSULTAS, "Sala 1", "Ana Perez"));
        services.add(new Service(ServiceType.PAGOS, "Sala 2", "Santiago Gomez"));
        services.add(new Service(ServiceType.RECLAMOS, "Sala 3", "Luis Martinez"));
    }

    public List<Service> getAllServices() {
        return services;
    }

    public Optional<Service> findByType(ServiceType serviceType) {
        return services.stream()
                       .filter(service -> service.getType() == serviceType)
                       .findFirst();
    }
}
