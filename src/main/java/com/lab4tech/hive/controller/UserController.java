package com.lab4tech.hive.controller;

import com.lab4tech.hive.controller.dto.UserRequest;
import com.lab4tech.hive.controller.dto.UserResponse;
import com.lab4tech.hive.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable long id){
        return userService.findById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam("email") String email){
        UserResponse user = userService.findByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user){
        UserResponse createdUser = userService.saveUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllUsers (){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

}
