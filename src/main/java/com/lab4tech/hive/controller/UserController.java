package com.lab4tech.hive.controller;

import com.lab4tech.hive.controller.dto.UserResponse;
import com.lab4tech.hive.model.entity.User;
import com.lab4tech.hive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public UserResponse getUserById(long id){
        User user = userService.findById(id);
        return new UserResponse(user.getId(), user.getEmail(), user.getRole());
    }

    public UserResponse getUserByEmail(String email){
        User user = userService.findByEmail(email);
        return new UserResponse(user.getId(), user.getEmail(), user.getRole());
    }

}
