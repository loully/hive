package com.lab4tech.hive.service;

import com.lab4tech.hive.controller.dto.UserRequest;
import com.lab4tech.hive.controller.dto.UserResponse;
import com.lab4tech.hive.exception.UserAlreadyExistsException;
import com.lab4tech.hive.model.entity.User;
import com.lab4tech.hive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /*
        Injection dependency through args construtor annotation
        replace: UserService(UserRepository userRepository){
            this.userRepository = userRepository;
        }
    */

    public UserResponse findById(long id){
        return userRepository.findById(id)
                .map(user -> new UserResponse(user.getId(), user.getEmail(), user.getRole()))
                .orElseThrow(()-> new MissingResourceException("The user with id: "+id+" is missing", "User", "id"));
    }

    public UserResponse findByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new MissingResourceException("The user with email: "+email+" is missing", "User", "email"));
        return new UserResponse(user.getId(), user.getEmail(), user.getRole());
    }

    public UserResponse saveUser(UserRequest userRequest){
        User user = new User();
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        user.setRole(userRequest.role());
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new UserAlreadyExistsException(userRequest.email());
        }
        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
    }

    public List<UserResponse> findAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserResponse> result = users.stream()
                .map(user -> new UserResponse(user.getId(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
        return result;
    }
}
