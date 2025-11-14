package com.lab4tech.hive.exception;

public class VolunteerProfileNotFoundException extends RuntimeException {
    public VolunteerProfileNotFoundException(String volunteerInfos){
        super("Volunteer Profile with infos: "+volunteerInfos+ " is not found.");
    }
}
