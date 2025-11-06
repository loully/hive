package com.lab4tech.hive.service;

import com.lab4tech.hive.controller.dto.UserRequest;
import com.lab4tech.hive.controller.dto.UserResponse;
import com.lab4tech.hive.exception.UserAlreadyExistsException;
import com.lab4tech.hive.exception.UserNotFoundException;
import com.lab4tech.hive.model.entity.AppUser;
import com.lab4tech.hive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.Collectors;

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
                .map(appUser -> new UserResponse(appUser.getId(), appUser.getEmail(), appUser.getRole()))
                .orElseThrow(()-> new MissingResourceException("The user with id: "+id+" is missing", "User", "id"));
    }

    public UserResponse findByEmail(String email){
        AppUser appUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new MissingResourceException("The user with email: "+email+" is missing", "User", "email"));
        return new UserResponse(appUser.getId(), appUser.getEmail(), appUser.getRole());
    }

    public UserResponse saveUser(UserRequest userRequest){
        AppUser appUser = new AppUser();
        appUser.setEmail(userRequest.email());
        appUser.setPassword(userRequest.password());
        appUser.setRole(userRequest.role());
        if(userRepository.findByEmail(appUser.getEmail()).isPresent()){
            throw new UserAlreadyExistsException(userRequest.email());
        }
        AppUser savedAppUser = userRepository.save(appUser);
        return new UserResponse(savedAppUser.getId(), savedAppUser.getEmail(), savedAppUser.getRole());
    }

    public List<UserResponse> findAllUsers(){
        List<AppUser> appUsers = userRepository.findAll();
        List<UserResponse> result = appUsers.stream()
                .map(appUser -> new UserResponse(appUser.getId(), appUser.getEmail(), appUser.getRole()))
                .collect(Collectors.toList());
        return result;
    }

    public UserResponse updateUser (long id, UserRequest user){
        AppUser existingAppUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if(user.email()!= null && !user.email().isBlank()){
            existingAppUser.setEmail(user.email());
        }
        if(user.password() != null && !user.password().isBlank()) {
            existingAppUser.setPassword(bCryptPasswordEncoder.encode(user.password()));
        }
        if(user.role() != null){
            existingAppUser.setRole(user.role());
        }
        AppUser updatedAppUser = userRepository.save(existingAppUser);
        return new UserResponse(updatedAppUser.getId(), updatedAppUser.getEmail(), updatedAppUser.getRole());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(long id){
        AppUser foundAppUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(foundAppUser);
    }
}
