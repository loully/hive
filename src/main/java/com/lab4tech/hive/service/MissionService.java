package com.lab4tech.hive.service;

import com.lab4tech.hive.controller.dto.MissionRequest;
import com.lab4tech.hive.controller.dto.MissionResponse;
import com.lab4tech.hive.model.entity.Mission;
import com.lab4tech.hive.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    public MissionResponse createMission(MissionRequest missionRequest){
        Mission newMission = new Mission();
        newMission.setTitle(missionRequest.title());
        newMission.setDescription(missionRequest.description());
        newMission.setLocation(missionRequest.location());
        newMission.setDate(missionRequest.date());
        newMission.setStartTime(missionRequest.startTime());
        newMission.setEndTime(missionRequest.endTime());
        newMission.setCapacity(missionRequest.capacity());

        newMission = missionRepository.save(newMission);
        return new MissionResponse(newMission.getId(),
                newMission.getTitle(),
                newMission.getDescription(),
                newMission.getLocation(),
                newMission.getDate(),
                newMission.getStartTime(),
                newMission.getEndTime(),
                newMission.getCapacity());
    }
}
