package com.lab4tech.hive.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record VolunteerRequest(@NotBlank(message = "Firstname is required")
                               String firstname,
                               @NotBlank(message = "Lastname is required")
                               String lastname,
                               @NotBlank(message = "Phone number is required")
                               String phoneNumber,
                               @NotBlank(message = "City is required")
                               String city,
                               List<Integer> skillIds
) { }
