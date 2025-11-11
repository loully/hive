package com.lab4tech.hive.controller;

import com.lab4tech.hive.controller.dto.SkillRequest;
import com.lab4tech.hive.controller.dto.SkillResponse;
import com.lab4tech.hive.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(@RequestBody SkillRequest skillRequest){
        SkillResponse createdSkill = skillService.createSkill(skillRequest);
        return new ResponseEntity<>(createdSkill, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getSkillById(@PathVariable int id){
        SkillResponse skillResponse = skillService.findSkillById(id);
        return new ResponseEntity<>(skillResponse, HttpStatus.FOUND);
    }

    @GetMapping("")
    public ResponseEntity<List<SkillResponse>> getAllSkills() {
        List<SkillResponse> result = skillService.getAllSkills();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<SkillResponse> updateSkill(@PathVariable int id, @RequestBody SkillRequest skill){
        SkillResponse updateSkill = skillService.updateSkill(id, skill);
        return new ResponseEntity<>(updateSkill, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSkill(@PathVariable int id){
        skillService.deleteSkillById(id);
        return ResponseEntity.noContent().build();
    }

}
