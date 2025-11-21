package com.lab4tech.hive.controller.dto;
import com.lab4tech.hive.model.entity.Role;

public record AppUserRequest(long id, String email, String password, Role role) {
}
