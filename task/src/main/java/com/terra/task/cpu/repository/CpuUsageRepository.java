package com.terra.task.cpu.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.terra.task.cpu.domain.CpuUsage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CpuUsageRepository extends JpaRepository<CpuUsage, Long> {

  @Query("SELECT c FROM CpuUsage c WHERE c.timestamp BETWEEN :startDateTime AND :endDateTime")
  List<CpuUsage> findMinuteCpuUsage(@Param("startDateTime") LocalDateTime startDateTime,
      @Param("endDateTime") LocalDateTime endDateTime);
}
