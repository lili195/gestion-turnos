package com.TurnsManagement.ShiftService.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.TurnsManagement.ShiftService.domain.model.ShiftEntity;
import com.TurnsManagement.ShiftService.service.ShiftService;

@RestController
@RequestMapping("/api/shifts")
@CrossOrigin
public class ShiftController {
    
    @Autowired
    private ShiftService shiftService;
    
    @GetMapping("/getShifts")
    @ResponseStatus(HttpStatus.OK)
    public List<ShiftEntity> getAllShifts(){
        return shiftService.getAllShifts();
    }

    @GetMapping("/date/{date}")
    public List<ShiftEntity> getShiftsByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return shiftService.getShiftsByDate(localDate);
    }

    @GetMapping("/user/{user}/date/{date}")
    public List<ShiftEntity> getTurnsByUserAndDate(@PathVariable String user, @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return shiftService.getShiftsByUserAndDate(user, localDate);
    }

    @GetMapping("/name/{shiftName}")
    public List<ShiftEntity> getShiftsByShiftName(@PathVariable String shiftName) {
        return shiftService.getShiftsByShiftName(shiftName);
    }

    @GetMapping("/{date}/{time}/{service}")
    public boolean existsShiftByDateTimeAndService(@PathVariable LocalDate date, @PathVariable String time, @PathVariable String service) {
        return shiftService.existsShiftByDateTimeAndService(date, time, service);
    }

    @PostMapping("/createShift")
    @ResponseStatus(HttpStatus.OK)
    public ShiftEntity createShift(@RequestBody ShiftDTO shiftDTO){
        ShiftEntity shift = new ShiftEntity();
        shift.setUser(shiftDTO.getUser());
        shift.setDate(LocalDate.parse(shiftDTO.getDate()));
        shift.setTime(shiftDTO.getTime());
        shift.setService(shiftDTO.getService());
        shift.setDependent(shiftDTO.getDependent());
        shift.setRoom(shiftDTO.getRoom());
        return shiftService.saveShift(shift);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteShift(@PathVariable Long id) {
        shiftService.deleteShiftById(id);
    }

}
