package com.lab4tech.hive.exception;

import com.lab4tech.hive.model.entity.Mission;

public class MissionAlreadyAssignedException extends RuntimeException {

    public MissionAlreadyAssignedException (Long missionId, Long volunteerId){
        super ("The mission with id : %d is already assigned to volunteer id: %d".formatted(missionId, volunteerId));
    }
}
