package com.lab4tech.hive.service;

import com.lab4tech.hive.controller.dto.SkillRequest;
import com.lab4tech.hive.controller.dto.SkillResponse;
import com.lab4tech.hive.exception.SkillAlreadyExistsException;
import com.lab4tech.hive.exception.SkillNotFoundException;
import com.lab4tech.hive.model.entity.Skill;
import com.lab4tech.hive.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
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

    public SkillResponse updateSkill(int id, SkillRequest request){
        Skill foundSkill = skillRepository.findById(id).orElseThrow(() -> new SkillNotFoundException(id));
        if(request.name() != null && !request.name().isBlank()) foundSkill.setName(request.name());
        if(request.description() != null && !request.description().isBlank()) foundSkill.setDescription(request.description());
        // Dirty check by JPA hibernate -- replace the request.name().equals(foundSkill.getName())
        Skill updateSkill = skillRepository.save(foundSkill);
        return new SkillResponse(updateSkill.getId(), updateSkill.getName(), updateSkill.getDescription());
    }

    public void deleteSkillById(int id){
        Skill result = skillRepository.findById(id).orElseThrow(() -> new SkillNotFoundException(id));
        skillRepository.delete(result);
    }
}
