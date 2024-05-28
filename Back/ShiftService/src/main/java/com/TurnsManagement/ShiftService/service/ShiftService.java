package com.TurnsManagement.ShiftService.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.TurnsManagement.ShiftService.application.events.ShiftEvent;
import com.TurnsManagement.ShiftService.application.utils.JsonUtils;
import com.TurnsManagement.ShiftService.domain.model.enums.ShiftStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.TurnsManagement.ShiftService.domain.model.ShiftEntity;
import com.TurnsManagement.ShiftService.persistence.ShiftRepository;

@Service
@RequiredArgsConstructor
public class ShiftService {
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    SequenceGenerator sequenceGenerator;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public List<ShiftEntity> getAllShifts() {
        return shiftRepository.findAll();
    }

    public ShiftEntity getShiftById(Long id) {
        return shiftRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("ShiftEntity with id " + id + " not found"));
    }
    
    public List<ShiftEntity> getShiftsByDate(LocalDate date) {
        return shiftRepository.findByDate(date);
    }

    public List<ShiftEntity> getShiftsByUserAndDate(String user, LocalDate date) {
        return shiftRepository.findByUserAndDate(user, date);
    }

    public List<ShiftEntity> getShiftsByShiftName(String shiftName) {
        return shiftRepository.findByShiftName(shiftName);
    }

    public ShiftEntity saveShift(ShiftEntity shift) {
        Long newOrderID = sequenceGenerator.generateNextOrderId();
        shift.setId(newOrderID);
        String shiftName = shift.getService().charAt(0) + shift.getId().toString();
        shift.setShiftName(shiftName);
        this.kafkaTemplate.send("shifts-topic", JsonUtils.toJson(new ShiftEvent(
                shift.getUser(), shift.getService(), shift.getDependent(), ShiftStatus.CREADO
        )));

        return shiftRepository.save(shift);
    }

    public boolean deleteShiftById(Long id) {
        if (shiftRepository.existsById(id)) {
            var shift = getShiftById(id);
            shiftRepository.deleteById(id);
            this.kafkaTemplate.send("shifts-topic", JsonUtils.toJson(new ShiftEvent(
                    shift.getUser(), shift.getService(), shift.getDependent(), ShiftStatus.ELIMINADO
            )));
            return true;
        } else {
            return false;
        }
    }

    public boolean existsShiftByDateTimeAndService(LocalDate date, String time, String service) {
        return shiftRepository.existsByDateAndTimeAndService(date, time, service);
    }
}
