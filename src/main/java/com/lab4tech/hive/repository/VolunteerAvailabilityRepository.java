package com.lab4tech.hive.repository;

import com.lab4tech.hive.model.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface VolunteerAvailabilityRepository extends JpaRepository<Availability, Long> {
    boolean existsByVolunteerProfileIdAndDateAndStartTimeAndEndTime(Long volunteerId, LocalDate date, LocalTime startTime, LocalTime endTime);
}
