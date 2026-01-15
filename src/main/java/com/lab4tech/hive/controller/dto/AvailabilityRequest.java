package com.lab4tech.hive.controller.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

public record AvailabilityRequest(
        @NotBlank(message = "Date is required")
        LocalDate date,
        @NotBlank(message = "Start time is required")
        LocalTime startTime,
        @NotBlank(message = "End time is required") LocalTime endTime,
        Long volunteerProfileId
) {
}
