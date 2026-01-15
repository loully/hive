package com.lab4tech.hive.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record SkillRequest(
        @NotBlank(message = "A name is required")
        String name,
        @NotBlank(message = "A description is required")
        String description) {
}
