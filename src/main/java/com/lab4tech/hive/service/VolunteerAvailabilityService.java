package com.lab4tech.hive.service;

import com.lab4tech.hive.controller.dto.AvailabilityRequest;
import com.lab4tech.hive.controller.dto.AvailabilityResponse;
import com.lab4tech.hive.exception.VolunteerAvailabilityAlreadyExistsException;
import com.lab4tech.hive.exception.VolunteerProfileNotFoundException;
import com.lab4tech.hive.model.entity.Availability;
import com.lab4tech.hive.model.entity.VolunteerProfile;
import com.lab4tech.hive.repository.VolunteerAvailabilityRepository;
import com.lab4tech.hive.repository.VolunteerProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VolunteerAvailabilityService {

    private final VolunteerAvailabilityRepository volunteerAvailabilityRepository;
    private final VolunteerProfileRepository volunteerProfileRepository;

    public AvailabilityResponse createVolunteerAvailability (Long volunteerId, AvailabilityRequest availabilityRequest){
        if(volunteerAvailabilityRepository.existsByVolunteerProfileIdAndDateAndStartTimeAndEndTime(
                volunteerId,
                availabilityRequest.date(),
                availabilityRequest.startTime(),
                availabilityRequest.endTime()))
            throw new VolunteerAvailabilityAlreadyExistsException(volunteerId, availabilityRequest.date(), availabilityRequest.startTime(), availabilityRequest.endTime());

        VolunteerProfile volunteerProfile = volunteerProfileRepository.findById(volunteerId).orElseThrow(() -> new VolunteerProfileNotFoundException(String.format("id:%s",volunteerId)));

        Availability newAvailability = new Availability();
        newAvailability.setVolunteerProfile(volunteerProfile);
        newAvailability.setDate(availabilityRequest.date());
        newAvailability.setStartTime(availabilityRequest.startTime());
        newAvailability.setEndTime(availabilityRequest.endTime());

        newAvailability = volunteerAvailabilityRepository.save(newAvailability);

        return new AvailabilityResponse(
                newAvailability.getId(),
                newAvailability.getDate(),
                newAvailability.getStartTime(),
                newAvailability.getEndTime(),
                newAvailability.getVolunteerProfile().getId());
    }

    public List<AvailabilityResponse> getVolunteerAvailabilities(Long id) {
        List<Availability> allVolunteerAvailabilities;
        if(!volunteerProfileRepository.existsById(id)){
            throw new VolunteerProfileNotFoundException("id: %d".formatted(id));
        }
        allVolunteerAvailabilities = volunteerAvailabilityRepository.findAllByVolunteerProfileId(id);

        List<AvailabilityResponse> result = allVolunteerAvailabilities.stream()
                .map(availability ->
                        new AvailabilityResponse(
                                availability.getId(),
                                availability.getDate(),
                                availability.getStartTime(),
                                availability.getEndTime(),
                                availability.getVolunteerProfile().getId()))
        .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public void deleteVolunteerAvailability(Long volunteerId, Long availabilityId) {
        if(!volunteerProfileRepository.existsById(volunteerId)){
            throw new VolunteerProfileNotFoundException("id: %d".formatted(volunteerId));
        }
        volunteerAvailabilityRepository.deleteByIdAndVolunteerProfileId(availabilityId, volunteerId);
    }
}
