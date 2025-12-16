package com.lab4tech.hive.controller;

import com.lab4tech.hive.controller.dto.MissionRequest;
import com.lab4tech.hive.controller.dto.MissionResponse;
import com.lab4tech.hive.controller.dto.MissionUpdateRequest;
import com.lab4tech.hive.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    public ResponseEntity<MissionResponse> createMission(@RequestBody MissionRequest request){
        MissionResponse response = missionService.createMission(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{missionId}")
    public ResponseEntity<MissionResponse> getMission(@PathVariable Long missionId){
        MissionResponse missionResponse = missionService.getMission(missionId);
        return new ResponseEntity<>(missionResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MissionResponse>> getAllMissions(){
        List<MissionResponse> missionResponseList = missionService.getAllMissions();
        return new ResponseEntity<>(missionResponseList, HttpStatus.OK);
    }


    @PutMapping("{missionId}")
    public ResponseEntity<MissionResponse> updateMission(@PathVariable Long missionId, @RequestBody MissionUpdateRequest request){
        MissionResponse result = missionService.updateMission(missionId, request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{missionId}")
    public ResponseEntity deleteMission(@PathVariable Long missionId){
        missionService.deleteMission(missionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /** Volunteer Missions **/
    @GetMapping("/volunteer/{volunteerId}")
    public ResponseEntity<List<MissionResponse>> getAllMissionsByVolunteer(@PathVariable Long volunteerId){
        List<MissionResponse> allMissionsByVolunteer =  missionService.getAllMissionsByVolunteer(volunteerId);
        return new ResponseEntity<>(allMissionsByVolunteer, HttpStatus.OK);
    }

}
