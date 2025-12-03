package com.lab4tech.hive.controller.dto;

import com.lab4tech.hive.model.entity.MissionStatus;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;

public record MissionUpdateRequest(
        @Nullable String title,
        @Nullable String description,
        @Nullable String location,
        @Nullable LocalDate date,
        @Nullable LocalTime startTime,
        @Nullable LocalTime endTime,
        @Nullable Integer capacity,
        @Nullable MissionStatus status
) {
}
