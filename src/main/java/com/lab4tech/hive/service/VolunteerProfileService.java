package com.lab4tech.hive.service;

import com.lab4tech.hive.controller.dto.MissionResponse;
import com.lab4tech.hive.controller.dto.SkillResponse;
import com.lab4tech.hive.controller.dto.VolunteerRequest;
import com.lab4tech.hive.controller.dto.VolunteerResponse;
import com.lab4tech.hive.exception.*;
import com.lab4tech.hive.model.entity.Availability;
import com.lab4tech.hive.model.entity.Mission;
import com.lab4tech.hive.model.entity.Skill;
import com.lab4tech.hive.model.entity.VolunteerProfile;
import com.lab4tech.hive.repository.MissionRepository;
import com.lab4tech.hive.repository.SkillRepository;
import com.lab4tech.hive.repository.VolunteerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VolunteerProfileService {
    //injection dependance of repository
    private final VolunteerProfileRepository volunteerRepo;
    private final SkillRepository skillRepository;
    private final MissionRepository missionRepository;

    public VolunteerResponse createVolunteerProfile(VolunteerRequest request) {
        if(volunteerRepo.findByFirstnameAndLastname(request.firstname(), request.lastname()).isPresent())
            throw new VolunteerProfileAlreadyExistsException(Strings.concat(request.firstname(), request.lastname()));
        VolunteerProfile createdVolunteer = new VolunteerProfile();
        createdVolunteer.setFirstname(request.firstname());
        createdVolunteer.setLastname(request.lastname());
        if(createdVolunteer.getPhoneNumber() != null) createdVolunteer.setPhoneNumber(request.phoneNumber());
        if(createdVolunteer.getCity() != null) createdVolunteer.setCity(request.city());
        if(request.skillIds() != null && !request.skillIds().isEmpty()){
            List<Skill> skills = skillRepository.findAllById(request.skillIds());
            createdVolunteer.setSkills(skills);
        }
        createdVolunteer = volunteerRepo.save(createdVolunteer);
        return mapToVolunteerResponse(createdVolunteer);
    }

    public VolunteerResponse getVolunteerProfile(long id){
        VolunteerProfile volunteer = volunteerRepo.findById(id).orElseThrow(() -> new VolunteerProfileNotFoundException("id="+id));
        return mapToVolunteerResponse(volunteer);
    }

    public void deleleteVolunteerProfile(Long id) {
        VolunteerProfile volunteerProfile = volunteerRepo.findById(id).orElseThrow(()-> new VolunteerProfileNotFoundException("id= "+ id));
        volunteerRepo.delete(volunteerProfile);
    }

    public List<VolunteerResponse> getAllVolunteerProfiles() {
        List<VolunteerResponse> allVolunteerProfiles = volunteerRepo.findAll()
                .stream()
                .map(this::mapToVolunteerResponse)
                .collect(Collectors.toList());

        return allVolunteerProfiles;
    }

    public VolunteerResponse updateVolunteerProfile(Long id, VolunteerRequest request) {
        VolunteerProfile volunteerProfile = volunteerRepo.findById(id).orElseThrow(() -> new VolunteerProfileNotFoundException("id="+id+", firstname="+request.firstname()+", lastname="+request.lastname()));
        if(request.phoneNumber() != null ) volunteerProfile.setPhoneNumber(request.phoneNumber());
        if(request.city() != null ) volunteerProfile.setCity(request.city());
        if(!request.skillIds().isEmpty()) {
            volunteerProfile.getSkills().clear();
            List<Skill> newSkills = skillRepository.findAllById(request.skillIds());
            volunteerProfile.setSkills(newSkills);
        }
        VolunteerProfile saveVolunteerProfile = volunteerRepo.save(volunteerProfile);
        return mapToVolunteerResponse(saveVolunteerProfile);
    }


    public VolunteerResponse addMissionToVolunteerProfile(Long volunteerId, Long missionId) {
        VolunteerProfile foundVolunteer = volunteerRepo.findById(volunteerId)
                .orElseThrow(()-> new VolunteerProfileNotFoundException("with id: %d".formatted(volunteerId)));
        Mission newMissionToAdd = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionNotFoundException(missionId));

        if(foundVolunteer.getMissions().contains(newMissionToAdd))
            throw new MissionAlreadyAssignedException(missionId, volunteerId);
        if (newMissionToAdd.getCapacity() != null && newMissionToAdd.getVolunteerProfileList().size() >= newMissionToAdd.getCapacity()) {
            throw new MissionFullException("Mission titled : '" + newMissionToAdd.getTitle() + "' is already fulled.");
        }

        foundVolunteer.getMissions().add(newMissionToAdd);

        foundVolunteer = volunteerRepo.save(foundVolunteer);

        return mapToVolunteerResponse(foundVolunteer);
    }

    /*************************
     ** Utilitary functions **
     *************************/

    private VolunteerResponse mapToVolunteerResponse(VolunteerProfile volunteer) {
        List<SkillResponse> volunteerSkillsResponse = mapToSkillResponseList(volunteer.getSkills());
        List<MissionResponse> volunteerMissionResponse = volunteer.getMissions().stream().map((mission) -> new MissionResponse(
                mission.getId(),
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
        return new VolunteerResponse(
                volunteer.getId(),
                volunteer.getFirstname(),
                volunteer.getLastname(),
                volunteer.getCity(),
                volunteerSkillsResponse,
                volunteerMissionResponse
        );
    }

    private List<SkillResponse> mapToSkillResponseList(List<Skill> skills){
        return skills.stream()
                .map(skill ->
                        new SkillResponse(
                                skill.getId(),
                                skill.getName(),
                                skill.getDescription()))
                .collect(Collectors.toList());
    }
}
