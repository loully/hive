package com.lab4tech.hive.repository;

import com.lab4tech.hive.model.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    boolean existsByTitleAndDateAndStartTime(String title, LocalDate date, LocalTime startTime);
}
