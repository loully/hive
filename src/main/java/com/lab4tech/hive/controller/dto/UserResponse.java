package com.lab4tech.hive.controller.dto;
import com.lab4tech.hive.model.entity.Role;

public record UserResponse(long id, String email, Role role) {}
