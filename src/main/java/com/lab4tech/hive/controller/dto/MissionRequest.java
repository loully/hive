package com.lab4tech.hive.controller.dto;

import com.lab4tech.hive.model.entity.MissionStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalTime;

public record MissionRequest(
        @NotBlank(message = "A title is required")
        String title,
        String description,
        @NotBlank(message = "A location is required")
        String location,
        @NotBlank(message = "A date is  required")
        LocalDate date,
        @NotBlank(message = "A start time is required")
        LocalTime startTime,
        @NotBlank(message = "An end time is required")
        LocalTime endTime,
        @NotBlank(message = "A capacity is required")
        Integer capacity,
        @NotBlank(message = "A status is required")
        MissionStatus status) {
}
