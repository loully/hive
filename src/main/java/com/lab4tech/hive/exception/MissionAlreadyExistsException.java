package com.lab4tech.hive.exception;

public class MissionAlreadyExistsException extends RuntimeException {

    public MissionAlreadyExistsException(String message) {
        super("The mission with infos %s already exists".formatted(message));
    }
}
