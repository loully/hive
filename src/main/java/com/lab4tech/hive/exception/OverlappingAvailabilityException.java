package com.lab4tech.hive.exception;

import java.time.LocalDate;
import java.time.LocalTime;

public class OverlappingAvailabilityException extends RuntimeException {

    public OverlappingAvailabilityException(LocalDate date, LocalTime startTime, LocalTime endTime){
        super("Overlapping availability %s from %s to %s".formatted(date.toString(), startTime.toString(), endTime.toString()));
    }
}
