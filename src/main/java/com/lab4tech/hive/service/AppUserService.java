package com.lab4tech.hive.service;

import com.lab4tech.hive.controller.dto.AppUserRequest;
import com.lab4tech.hive.controller.dto.AppUserResponse;
import com.lab4tech.hive.exception.UserAlreadyExistsException;
import com.lab4tech.hive.exception.UserNotFoundException;
import com.lab4tech.hive.model.entity.AppUser;
import com.lab4tech.hive.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
        Injection dependency through args constructor annotation
        replace: UserService(UserRepository userRepository){
            this.userRepository = userRepository;
        }
    */

    public AppUserResponse findById(long id){
        return appUserRepository.findById(id)
                .map(appUser -> new AppUserResponse(appUser.getId(), appUser.getEmail(), appUser.getRole()))
                .orElseThrow(()-> new MissingResourceException("The user with id: "+id+" is missing", "User", "id"));
    }

    public AppUserResponse findByEmail(String email){
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(()-> new MissingResourceException("The user with email: "+email+" is missing", "User", "email"));
        return new AppUserResponse(appUser.getId(), appUser.getEmail(), appUser.getRole());
    }

    public AppUserResponse saveUser(AppUserRequest appUserRequest){
        AppUser appUser = new AppUser();
        appUser.setEmail(appUserRequest.email());
        appUser.setPassword(bCryptPasswordEncoder.encode(appUserRequest.password()));
        appUser.setRole(appUserRequest.role());
        if(appUserRepository.findByEmail(appUser.getEmail()).isPresent()){
            throw new UserAlreadyExistsException(appUserRequest.email());
        }
        AppUser savedAppUser = appUserRepository.save(appUser);
        return new AppUserResponse(savedAppUser.getId(), savedAppUser.getEmail(), savedAppUser.getRole());
    }

    public List<AppUserResponse> findAllUsers(){
        List<AppUser> appUsers = appUserRepository.findAll();
        List<AppUserResponse> result = appUsers.stream()
                .map(appUser -> new AppUserResponse(appUser.getId(), appUser.getEmail(), appUser.getRole()))
                .collect(Collectors.toList());
        return result;
    }

    public AppUserResponse updateUser (long id, AppUserRequest user){
        AppUser existingAppUser = appUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if(user.email()!= null && !user.email().isBlank()){
            existingAppUser.setEmail(user.email());
        }
        if(user.password() != null && !user.password().isBlank()) {
            existingAppUser.setPassword(bCryptPasswordEncoder.encode(user.password()));
        }
        if(user.role() != null){
            existingAppUser.setRole(user.role());
        }
        AppUser updatedAppUser = appUserRepository.save(existingAppUser);
        return new AppUserResponse(updatedAppUser.getId(), updatedAppUser.getEmail(), updatedAppUser.getRole());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(long id){
        AppUser foundAppUser = appUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        appUserRepository.delete(foundAppUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser userEntity = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The user with email "+email+" is not found"));
        return User.withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().name())
                .build();
    }
}
