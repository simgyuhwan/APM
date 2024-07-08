package com.terra.task.cpu.repository;

import com.terra.task.cpu.domain.CpuStats;
import com.terra.task.cpu.domain.CpuUsage;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CpuUsageRepository extends JpaRepository<CpuUsage, Long> {

  @Query("SELECT c FROM CpuUsage c WHERE c.timestamp BETWEEN :startDateTime AND :endDateTime")
  List<CpuUsage> findMinuteCpuUsage(@Param("startDateTime") LocalDateTime startDateTime,
      @Param("endDateTime") LocalDateTime endDateTime);

  @Query("SELECT new com.terra.task.cpu.domain.CpuStats("
      + "MAX(c.cpuPercentage), MIN(c.cpuPercentage), CAST(AVG(c.cpuPercentage) AS bigdecimal))"
      + " FROM CpuUsage c"
      + " WHERE timestamp BETWEEN :startDateTime AND :endDateTime"
      + " GROUP BY DATE(timestamp), HOUR(timestamp)"
      + " ORDER BY DATE(timestamp), HOUR(timestamp)")
  List<CpuStats> findHourlyCpuUsageStatsByDate(@Param("startDateTime") LocalDateTime startDateTime,
      @Param("endDateTime") LocalDateTime endDateTime);
}
