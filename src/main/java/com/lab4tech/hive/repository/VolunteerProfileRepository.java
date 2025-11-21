package com.lab4tech.hive.repository;

import com.lab4tech.hive.model.entity.VolunteerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerProfileRepository extends JpaRepository<VolunteerProfile, Long> {

    Optional<VolunteerProfile> findByFirstnameAndLastname(String firstname, String lastname);

}
