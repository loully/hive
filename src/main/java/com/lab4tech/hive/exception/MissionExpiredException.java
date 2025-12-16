package com.lab4tech.hive.exception;

public class MissionExpiredException extends RuntimeException {
    public MissionExpiredException(String message) {
        super(message);
    }
}
