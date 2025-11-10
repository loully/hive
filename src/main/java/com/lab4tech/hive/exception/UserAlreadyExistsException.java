package com.lab4tech.hive.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String email) {
        super("The user with email:"+email+" already exists");
    }
}
