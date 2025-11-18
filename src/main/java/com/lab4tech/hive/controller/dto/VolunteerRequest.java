package com.lab4tech.hive.controller.dto;

import java.util.List;

public record VolunteerRequest(String firstname,
                               String lastname,
                               String phoneNumber,
                               String city,
                               List<Integer> skillIds
) { }
