package com.TurnsManagement.ShiftService.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TurnsManagement.ShiftService.domain.model.ShiftEntity;
import com.TurnsManagement.ShiftService.persistence.ShiftRepository;

@Service
public class ShiftService {
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    SequenceGenerator sequenceGenerator;

    public List<ShiftEntity> getAllShifts() {
        return shiftRepository.findAll();
    }

    public Optional<ShiftEntity> getShiftById(Long id) {
        return shiftRepository.findById(id);
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
        return shiftRepository.save(shift);
    }

    public void deleteShiftById(Long id) {
        shiftRepository.deleteById(id);
    }

    public boolean existsShiftByDateTimeAndService(LocalDate date, String time, String service) {
        return shiftRepository.existsByDateAndTimeAndService(date, time, service);
    }
}
