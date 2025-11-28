package com.lab4tech.hive.service;

import com.lab4tech.hive.controller.dto.MissionRequest;
import com.lab4tech.hive.controller.dto.MissionResponse;
import com.lab4tech.hive.exception.MissionAlreadyExistsException;
import com.lab4tech.hive.exception.MissionNotFoundException;
import com.lab4tech.hive.model.entity.Mission;
import com.lab4tech.hive.repository.MissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    public MissionResponse createMission(MissionRequest missionRequest){

        if(missionRepository.existsByTitleAndDateAndStartTime(missionRequest.title(), missionRequest.date(), missionRequest.startTime())) throw new MissionAlreadyExistsException(String.format("title: %s, description: %s", missionRequest.title(), missionRequest.description()));

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

    public MissionResponse getMission(Long missionId){
        Mission foundMission = missionRepository.findById(missionId).orElseThrow(() -> new MissionNotFoundException(missionId));
        return new MissionResponse(foundMission.getId(),
                foundMission.getTitle(),
                foundMission.getDescription(),
                foundMission.getLocation(),
                foundMission.getDate(),
                foundMission.getStartTime(),
                foundMission.getEndTime(),
                foundMission.getCapacity()
        );
    }

    public List<MissionResponse> getAllMissions() {
        return missionRepository.findAll().stream().map(mission -> new MissionResponse(mission.getId(),
                mission.getTitle(),
                mission.getDescription(),
                mission.getLocation(),
                mission.getDate(),
                mission.getStartTime(),
                mission.getEndTime(),
                mission.getCapacity()))
                .collect(Collectors.toList());
    }

    public void deleteMission(Long missionId) {
        if(!missionRepository.existsById(missionId)) throw new MissionNotFoundException(missionId);
        missionRepository.deleteById(missionId);
    }

    @Transactional // dirty checking
    public MissionResponse updateMission(Long missionId, MissionRequest request) {
        Mission missionToUpdate = missionRepository.findById(missionId).orElseThrow(()-> new MissionNotFoundException(missionId));

        missionToUpdate.setTitle(request.title());
        missionToUpdate.setDescription(request.description());
        missionToUpdate.setLocation(request.location());
        missionToUpdate.setDate(request.date());
        missionToUpdate.setStartTime(request.startTime());
        missionToUpdate.setEndTime(request.endTime());
        missionToUpdate.setCapacity(request.capacity());

        return new MissionResponse(missionToUpdate.getId(),
                missionToUpdate.getTitle(),
                missionToUpdate.getDescription(),
                missionToUpdate.getLocation(),
                missionToUpdate.getDate(),
                missionToUpdate.getStartTime(),
                missionToUpdate.getEndTime(),
                missionToUpdate.getCapacity()
        );
    }
}
