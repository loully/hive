package com.lab4tech.hive.repository;

import com.lab4tech.hive.model.entity.VolunteerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerProfileRepository extends JpaRepository<VolunteerProfile, Long> {
}
