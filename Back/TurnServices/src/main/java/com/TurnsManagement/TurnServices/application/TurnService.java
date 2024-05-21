package com.TurnsManagement.TurnServices.application;

import java.util.List;
import java.util.Optional;

import com.TurnsManagement.TurnServices.domain.models.Service;
import com.TurnsManagement.TurnServices.domain.models.ServiceType;
import com.TurnsManagement.TurnServices.domain.repositories.ServiceRepository;

public class TurnService {
    private final ServiceRepository serviceRepository;

    public TurnService() {
        this.serviceRepository = new ServiceRepository();
    }

    public List<Service> listServices() {
        return serviceRepository.getAllServices();
    }

    public Optional<Service> getServiceInfo(ServiceType serviceType) {
        return serviceRepository.findByType(serviceType);
    }
}
