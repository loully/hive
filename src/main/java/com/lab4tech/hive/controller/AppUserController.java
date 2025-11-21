package com.lab4tech.hive.controller;

import com.lab4tech.hive.controller.dto.AppUserRequest;
import com.lab4tech.hive.controller.dto.AppUserResponse;
import com.lab4tech.hive.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/{id}")
    public AppUserResponse getUserById(@PathVariable long id){
        return appUserService.findById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<AppUserResponse> getUserByEmail(@RequestParam("email") String email){
        AppUserResponse user = appUserService.findByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<AppUserResponse> createUser(@Valid @RequestBody AppUserRequest user){
        AppUserResponse createdUser = appUserService.saveUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("")
    public ResponseEntity<List<AppUserResponse>> getAllUsers (){
        return new ResponseEntity<>(appUserService.findAllUsers(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<AppUserResponse> updateUser(@PathVariable long id, @RequestBody AppUserRequest user){
        AppUserResponse updateUser = appUserService.updateUser(id, user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        appUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
