package com.lab4tech.hive.service;

import com.lab4tech.hive.controller.dto.VolunteerRequest;
import com.lab4tech.hive.controller.dto.VolunteerResponse;
import com.lab4tech.hive.exception.VolunteerProfileNotFoundException;
import com.lab4tech.hive.model.entity.VolunteerProfile;
import com.lab4tech.hive.repository.VolunteerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VolunteerProfileService {
    //injection dependance of repository
    private final VolunteerProfileRepository volunteerRepo;

    public VolunteerResponse createVolunteerProfile(VolunteerRequest request) {
        if(volunteerRepo.findByFirstnameAndLastname(request.firstname(), request.lastname()).isPresent())
            throw new VolunteerProfileNotFoundException(Strings.concat(request.firstname(), request.lastname()));
        VolunteerProfile createdVolunteer = new VolunteerProfile();
        createdVolunteer.setFirstname(request.firstname());
        createdVolunteer.setLastname(request.lastname());
        if(!createdVolunteer.getPhoneNumber().isBlank()) createdVolunteer.setPhoneNumber(request.phoneNumber());
        if(!createdVolunteer.getCity().isBlank()) createdVolunteer.setCity(request.city());
        createdVolunteer = volunteerRepo.save(createdVolunteer);
        return new VolunteerResponse(
                createdVolunteer.getId(),
                createdVolunteer.getFirstname(),
                createdVolunteer.getLastname(),
                createdVolunteer.getCity());
    }

    public VolunteerResponse getVolunteerProfile(long id){
        VolunteerProfile volunteer = volunteerRepo.findById(id).orElseThrow(() -> new VolunteerProfileNotFoundException("id="+id));
        return new VolunteerResponse(volunteer.getId(), volunteer.getFirstname(), volunteer.getLastname(), volunteer.getCity());
    }

    public void deleleteVolunteerProfile(Long id) {
        VolunteerProfile volunteerProfile = volunteerRepo.findById(id).orElseThrow(()-> new VolunteerProfileNotFoundException("id= "+ id));
        volunteerRepo.delete(volunteerProfile);
    }

    public List<VolunteerResponse> getAllVolunteerProfiles() {
        List<VolunteerResponse> allVolunteerProfiles = volunteerRepo.findAll()
                .stream()
                .map(volunteerProfile -> new VolunteerResponse(
                        volunteerProfile.getId(),
                        volunteerProfile.getFirstname(),
                        volunteerProfile.getLastname(),
                        volunteerProfile.getCity()))
                .collect(Collectors.toList());

        return allVolunteerProfiles;
    }

    public VolunteerResponse updateVolunteerProfile(Long id, VolunteerRequest request) {
        VolunteerProfile volunteerProfile = volunteerRepo.findById(id).orElseThrow(() -> new VolunteerProfileNotFoundException("id="+id+", firstname="+request.firstname()+", lastname="+request.lastname()));
        System.out.println("Service -- update\n");
        System.out.println("Volunteer profile found in repository >> firstname:"+volunteerProfile.getFirstname()+" , lastname:"+volunteerProfile.getLastname());
/*        if(request.firstname() != null ) volunteerProfile.setFirstname(request.firstname());
        if(request.lastname() != null && !request.lastname().isBlank()) volunteerProfile.setLastname(request.lastname());*/
        if(request.phoneNumber() != null ) volunteerProfile.setPhoneNumber(request.phoneNumber());
        if(request.city() != null ) volunteerProfile.setCity(request.city());

        System.out.println("update -- volunteerProfile \n" +
                "firstname: "+volunteerProfile.getFirstname()
                +" lastname: "+volunteerProfile.getLastname()
                + " phoneNumber: "+volunteerProfile.getPhoneNumber());

        VolunteerProfile saveVolunteerProfile = volunteerRepo.save(volunteerProfile);
        return new VolunteerResponse(
                saveVolunteerProfile.getId(),
                saveVolunteerProfile.getFirstname(),
                saveVolunteerProfile.getLastname(),
                saveVolunteerProfile.getCity());
    }

}
