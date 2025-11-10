package com.lab4tech.hive.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(long id) {
        super("This user with id:"+id+" does not exist");
    }
}
