package com.lab4tech.hive.controller;

import com.lab4tech.hive.controller.dto.AvailabilityRequest;
import com.lab4tech.hive.controller.dto.AvailabilityResponse;
import com.lab4tech.hive.service.VolunteerAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteers")
@RequiredArgsConstructor
public class VolunteerAvailabilityController {

    private final VolunteerAvailabilityService volunteerAvailabilityService;

    @PostMapping("/{id}/availability")
    public ResponseEntity<AvailabilityResponse> createVolunteerAvailability(@PathVariable Long id, @RequestBody AvailabilityRequest request){
        AvailabilityResponse response = volunteerAvailabilityService.createVolunteerAvailability(id, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<List<AvailabilityResponse>> getVolunteerAvailabilities(@PathVariable Long id) {
        List<AvailabilityResponse> result = volunteerAvailabilityService.getVolunteerAvailabilities(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{volunteerId}/availability/{availabilityId}")
    public ResponseEntity<Void> deleteVolunteerAvailability(@PathVariable Long volunteerId, @PathVariable Long availabilityId){
        volunteerAvailabilityService.deleteVolunteerAvailability(volunteerId, availabilityId);
        return ResponseEntity.noContent().build();
    }


}
