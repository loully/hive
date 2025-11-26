package com.lab4tech.hive.repository;

import com.lab4tech.hive.model.entity.Availability;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface VolunteerAvailabilityRepository extends JpaRepository<Availability, Long> {
    boolean existsByVolunteerProfileIdAndDateAndStartTimeAndEndTime(Long volunteerId, LocalDate date, LocalTime startTime, LocalTime endTime);
    List<Availability> findAllByVolunteerProfileId(Long id);
    void deleteByIdAndVolunteerProfileId(Long availabilityId, Long volunteerId);

    @Query("""
    SELECT CASE WHEN COUNT(DISTINCT a.id) > 0 THEN TRUE ELSE FALSE END 
    FROM Availability a
    WHERE a.volunteerProfile.id =:volunteerProfileId
    AND (
    (a.startTime < :endTime) AND (a.endTime > :startTime)
    AND a.date = :date)
    """)
    boolean existsOverlappingAvailability(Long volunteerProfileId, LocalDate date, LocalTime startTime, LocalTime endTime);
}
