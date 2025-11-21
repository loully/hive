package com.lab4tech.hive.exception;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class VolunteerAvailabilityAlreadyExistsException extends RuntimeException{

    public VolunteerAvailabilityAlreadyExistsException(Long volunteerId, LocalDate date, LocalTime startTime, LocalTime endTime){
        super(String.format(
                "The volunteer with id: %d has already an availability %s from %s to %s",
                volunteerId, date.toString(), startTime.toString(), endTime.toString()));
    }
}
