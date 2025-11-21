package com.lab4tech.hive.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalTime;

public record AvailabilityResponse (@NotBlank Long id,
                                    @NotBlank LocalDate date,
                                    @NotBlank LocalTime startTime,
                                    @NotBlank LocalTime endTime,
                                    Long volunteerProfileId)
{}
