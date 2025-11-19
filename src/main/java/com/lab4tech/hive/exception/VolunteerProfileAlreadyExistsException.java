package com.lab4tech.hive.exception;

public class VolunteerProfileAlreadyExistsException extends RuntimeException {

    public VolunteerProfileAlreadyExistsException (String volunteerInfos) {
        super("The volunteer profile with %s".formatted(volunteerInfos));
    }
}
