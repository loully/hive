package com.lab4tech.hive.service;

import com.lab4tech.hive.model.entity.User;
import com.lab4tech.hive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<User> findById(long id){
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
}
