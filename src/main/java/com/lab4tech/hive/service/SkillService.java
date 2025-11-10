package com.lab4tech.hive.service;

import com.lab4tech.hive.controller.dto.SkillRequest;
import com.lab4tech.hive.controller.dto.SkillResponse;
import com.lab4tech.hive.exception.SkillAlreadyExistsException;
import com.lab4tech.hive.exception.SkillNotFoundException;
import com.lab4tech.hive.model.entity.Skill;
import com.lab4tech.hive.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;


    public SkillResponse createSkill(SkillRequest skillRequest) {
        if(skillRepository.findByName(skillRequest.name()).isPresent()) throw new SkillAlreadyExistsException(skillRequest.name());
        Skill createdSkill = new Skill();
        createdSkill.setName(skillRequest.name());
        createdSkill.setDescription(skillRequest.description());
        Skill result = skillRepository.save(createdSkill);
        return new SkillResponse(result.getId(), result.getName(), result.getDescription());
    }

    public SkillResponse findSkillById(int id) {
        Skill result = skillRepository.findById(id).orElseThrow(()-> new SkillNotFoundException(id));
        return new SkillResponse(result.getId(), result.getName(), result.getDescription());
    }

    public List<SkillResponse> getAllSkills() {
        List<SkillResponse>result = skillRepository.findAll().stream().map((skill) -> new SkillResponse(skill.getId(), skill.getName(), skill.getDescription())).collect(Collectors.toList());
        return result;
    }
}
