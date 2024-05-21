package com.TurnsManagement.ShiftService.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.TurnsManagement.ShiftService.domain.model.ShiftEntity;

public interface ShiftRepository extends MongoRepository<ShiftEntity, Long>{
    List<ShiftEntity> findByUser(String user);
    List<ShiftEntity> findByDate(LocalDate date);
    List<ShiftEntity> findByShiftName(String shiftName);
    List<ShiftEntity> findByUserAndDate(String user, LocalDate date);
    boolean existsByDateAndTimeAndService(LocalDate date, String time, String service);
}
