package com.lab4tech.hive.exception;

public class MissionNotFoundException extends RuntimeException{

    public MissionNotFoundException(Long id){
        super("Mission with id: %d is not found".formatted(id));
    }
}
