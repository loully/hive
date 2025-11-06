package com.lab4tech.hive.repository;

import com.lab4tech.hive.controller.dto.UserRequest;
import com.lab4tech.hive.controller.dto.UserResponse;
import com.lab4tech.hive.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
