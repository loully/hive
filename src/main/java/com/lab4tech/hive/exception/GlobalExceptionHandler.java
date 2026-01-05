package com.lab4tech.hive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OverlappingAvailabilityException.class)
    public ResponseEntity<String> handleOverlappingAvailability(OverlappingAvailabilityException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(VolunteerAvailabilityAlreadyExistsException.class)
    public ResponseEntity<String> handleVolunteerAvailabilityAlreadyExists(VolunteerAvailabilityAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(VolunteerProfileNotFoundException.class)
    public ResponseEntity<String> handleVolunteerProfileNotFound(VolunteerProfileNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(VolunteerProfileAlreadyExistsException.class)
    public ResponseEntity<String> handleVolunteerProfileAlreadyExists(VolunteerProfileAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(MissionAlreadyExistsException.class)
    public ResponseEntity<String> handleMissionAlreadyExists(MissionAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(MissionNotFoundException.class)
    public ResponseEntity<String> handleMissionNotFound(MissionNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(MissionAlreadyAssignedException.class)
    public ResponseEntity<String> handleMissionAlreadyAssigned(MissionAlreadyAssignedException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(MissionFullException.class)
    public ResponseEntity<String> handleMissionFull(MissionFullException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(MissionExpiredException.class)
    public ResponseEntity<String> handleMissionExpired(MissionExpiredException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(VolunteerNotAvailableException.class)
    public ResponseEntity<String> handleVolunteerNotAvailable(VolunteerNotAvailableException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(SkillNotFoundException.class)
    public ResponseEntity<String> handleSkillNotFound(SkillNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(SkillAlreadyExistsException.class)
    public ResponseEntity<String> handleSkillAlreadyExists(SkillAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
}
