package com.lab4tech.hive.service;

import com.lab4tech.hive.controller.dto.UserRequest;
import com.lab4tech.hive.controller.dto.UserResponse;
import com.lab4tech.hive.exception.UserAlreadyExistsException;
import com.lab4tech.hive.exception.UserNotFoundException;
import com.lab4tech.hive.model.entity.User;
import com.lab4tech.hive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.Collectors;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
        Injection dependency through args constructor annotation
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

    public UserResponse updateUser (long id, UserRequest user){
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if(user.email()!= null && !user.email().isBlank()){
            existingUser.setEmail(user.email());
        }
        if(user.password() != null && !user.password().isBlank()) {
            existingUser.setPassword(bCryptPasswordEncoder.encode(user.password()));
        }
        if(user.role() != null){
            existingUser.setRole(user.role());
        }
        User updatedUser = userRepository.save(existingUser);
        return new UserResponse(updatedUser.getId(), updatedUser.getEmail(), updatedUser.getRole());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(long id){
        User foundUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(foundUser);
    }
}
