package com.lab4tech.hive.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record MissionRequest(
        String title,
        String description,
        String location,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        Integer capacity) {
}
