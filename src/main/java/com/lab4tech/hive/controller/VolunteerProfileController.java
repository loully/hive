package com.lab4tech.hive.controller;

import com.lab4tech.hive.controller.dto.VolunteerRequest;
import com.lab4tech.hive.controller.dto.VolunteerResponse;
import com.lab4tech.hive.service.VolunteerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteers")
@RequiredArgsConstructor
public class VolunteerProfileController {

    private final VolunteerProfileService volunteerProfileService;

    //create volunteer
    @PostMapping
    public ResponseEntity<VolunteerResponse> createVolunteerProfile(@Valid @RequestBody VolunteerRequest request){
        VolunteerResponse result = volunteerProfileService.createVolunteerProfile(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    //Get volunteer
    @GetMapping("/{id}")
    public ResponseEntity<VolunteerResponse> getVolunteerProfile(@PathVariable Long id){
        VolunteerResponse foundVolunteer = volunteerProfileService.getVolunteerProfile(id);
        return new ResponseEntity<>(foundVolunteer, HttpStatus.FOUND);
    }

    @GetMapping("")
    public ResponseEntity<List<VolunteerResponse>> getAllVolunteerProfiles(){
        List<VolunteerResponse> allVolunteerProfiles =  volunteerProfileService.getAllVolunteerProfiles();
        return new ResponseEntity<>(allVolunteerProfiles, HttpStatus.OK);
    }

    //Delete volunteer
    @DeleteMapping("/{id}")
    public ResponseEntity deleteVolunteerProfile(@PathVariable Long id){
        volunteerProfileService.deleleteVolunteerProfile(id);
        return ResponseEntity.noContent().build();
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<VolunteerResponse> updateVolunteerProfile(@PathVariable Long id, @RequestBody VolunteerRequest request){
        VolunteerResponse volunteerResponse = volunteerProfileService.updateVolunteerProfile(id, request);
        return new ResponseEntity<>(volunteerResponse, HttpStatus.OK);
    }

    //Add mission
    @PostMapping("/{volunteerId}/missions/{missionId}")
    public ResponseEntity<VolunteerResponse> addMissionToVolunteerProfile(@PathVariable Long volunteerId, @PathVariable Long missionId){
        VolunteerResponse result = volunteerProfileService.addMissionToVolunteerProfile(volunteerId, missionId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
