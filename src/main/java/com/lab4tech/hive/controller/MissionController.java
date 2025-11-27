package com.lab4tech.hive.controller;

import com.lab4tech.hive.controller.dto.MissionRequest;
import com.lab4tech.hive.controller.dto.MissionResponse;
import com.lab4tech.hive.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
