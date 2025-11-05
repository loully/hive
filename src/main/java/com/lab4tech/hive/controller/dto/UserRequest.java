package com.lab4tech.hive.controller.dto;
import com.lab4tech.hive.model.entity.Role;

public record UserRequest(Long id, String email, String password, Role role) {
}
