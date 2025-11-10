package com.lab4tech.hive.exception;

public class SkillAlreadyExistsException extends RuntimeException{

    public SkillAlreadyExistsException(String name){
        super("The skill :"+name+ " is already existing");
    }
}
