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
        updateMissionFromRequest(missionRequest, newMission);

        newMission = missionRepository.save(newMission);
        return new MissionResponse(newMission.getId(),
                newMission.getTitle(),
                newMission.getDescription(),
                newMission.getLocation(),
                newMission.getDate(),
                newMission.getStartTime(),
                newMission.getEndTime(),
                newMission.getCapacity(),
                newMission.getMissionStatus()
        );
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
                foundMission.getCapacity(),
                foundMission.getMissionStatus()
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
                mission.getCapacity(),
                mission.getMissionStatus()
                ))
                .collect(Collectors.toList());
    }

    public void deleteMission(Long missionId) {
        if(!missionRepository.existsById(missionId)) throw new MissionNotFoundException(missionId);
        missionRepository.deleteById(missionId);
    }

    @Transactional // dirty checking
    public MissionResponse updateMission(Long missionId, MissionRequest request) {
        Mission missionToUpdate = missionRepository.findById(missionId).orElseThrow(()-> new MissionNotFoundException(missionId));

        missionToUpdate = updateMissionFromRequest(request, missionToUpdate);
        //missionToUpdate = missionRepository.save(missionToUpdate);

        return new MissionResponse(missionToUpdate.getId(),
                missionToUpdate.getTitle(),
                missionToUpdate.getDescription(),
                missionToUpdate.getLocation(),
                missionToUpdate.getDate(),
                missionToUpdate.getStartTime(),
                missionToUpdate.getEndTime(),
                missionToUpdate.getCapacity(),
                missionToUpdate.getMissionStatus()
        );
    }

    private Mission updateMissionFromRequest(MissionRequest request, Mission missionToUpdate) {
        if(request.title() != null) missionToUpdate.setTitle(request.title());
        if(request.description() != null) missionToUpdate.setDescription(request.description());
        if(request.location() != null) missionToUpdate.setLocation(request.location());
        if(request.date() != null) missionToUpdate.setDate(request.date());
        if(request.startTime() != null) missionToUpdate.setStartTime(request.startTime());
        if(request.endTime() != null) missionToUpdate.setEndTime(request.endTime());
        if(request.capacity() != null) missionToUpdate.setCapacity(request.capacity());
        if(request.status() != null) missionToUpdate.setMissionStatus(request.status());
        return missionToUpdate;
    }
}
