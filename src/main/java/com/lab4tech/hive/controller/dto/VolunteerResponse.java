package com.lab4tech.hive.controller.dto;

import com.lab4tech.hive.model.entity.Skill;

import java.util.List;

public record VolunteerResponse(Long id, String firstname, String lastname, String city, List<Skill> skills) {}
