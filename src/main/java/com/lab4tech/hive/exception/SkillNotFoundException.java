package com.lab4tech.hive.exception;

public class SkillNotFoundException extends RuntimeException{

    public SkillNotFoundException(int id){
        super("Skill with id:"+id+" doesn't exist");
    }
}
