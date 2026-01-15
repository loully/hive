package com.lab4tech.hive.controller.dto;
import com.lab4tech.hive.model.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AppUserRequest(
        long id,
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min=8, message = "8 characters minimum")
        String password,
        Role role
) {
}
