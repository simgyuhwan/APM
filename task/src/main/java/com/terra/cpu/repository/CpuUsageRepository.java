package com.terra.cpu.repository;

import com.terra.cpu.domain.CpuStats;
import com.terra.cpu.domain.CpuUsage;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CpuUsageRepository extends JpaRepository<CpuUsage, Long> {

  /**
   * 지정한 기간 내의 분당 CPU 사용률을 조회할 수 있습니다.
   *
   * @param startDateTime 시작 날짜
   * @param endDateTime   종료 날짜
   * @return 분당 CPU 사용률 List
   */
  @Query("SELECT c FROM CpuUsage c WHERE c.timestamp BETWEEN :startDateTime AND :endDateTime")
  List<CpuUsage> findMinuteCpuUsageByDate(@Param("startDateTime") LocalDateTime startDateTime,
      @Param("endDateTime") LocalDateTime endDateTime);

  /**
   * 지정한 기간 내의 시간별 CPU 사용률의 최대값, 최소값, 평균값을 조회할 수 있습니다.
   *
   * @param startDateTime 시작 날짜
   * @param endDateTime   종료 날짜
   * @return 시간별 CPU 정보 List
   */
  @Query(
      "SELECT new com.terra.cpu.domain.CpuStats("
          + "CAST(DATE_FORMAT(c.timestamp, '%Y-%m-%d %H') AS LocalDateTime), "
          + "MAX(c.cpuPercentage), MIN(c.cpuPercentage), CAST(AVG(c.cpuPercentage) AS BigDecimal))"
          + " FROM CpuUsage c"
          + " WHERE c.timestamp BETWEEN :startDateTime AND :endDateTime"
          + " GROUP BY CAST(DATE_FORMAT(c.timestamp, '%Y-%m-%d %H') AS LocalDateTime)"
          + " ORDER BY CAST(DATE_FORMAT(c.timestamp, '%Y-%m-%d %H') AS LocalDateTime)")
  List<CpuStats> findHourlyCpuUsageStatsByDate(@Param("startDateTime") LocalDateTime startDateTime,
      @Param("endDateTime") LocalDateTime endDateTime);


  /**
   * 지정한 기간 내의 일별 CPU 사용률의 최대값, 최소값, 평균값을 조회할 수 있습니다.
   *
   * @param startDateTime 시작 날짜
   * @param endDateTime   종료 날짜
   * @return 일별 CPU 정보 List
   */
  @Query(
      "SELECT new com.terra.cpu.domain.CpuStats("
          + "CAST(DATE_FORMAT(c.timestamp, '%Y-%m-%d') AS LocalDateTime), "
          + "MAX(c.cpuPercentage), MIN(c.cpuPercentage), CAST(AVG(c.cpuPercentage) AS BigDecimal))"
          + " FROM CpuUsage c"
          + " WHERE c.timestamp BETWEEN :startDateTime AND :endDateTime"
          + " GROUP BY CAST(DATE_FORMAT(c.timestamp, '%Y-%m-%d') AS LocalDateTime)"
          + " ORDER BY CAST(DATE_FORMAT(c.timestamp, '%Y-%m-%d') AS LocalDateTime)")
  List<CpuStats> findDailyCpuUsageStatsByDate(@Param("startDateTime") LocalDateTime startDateTime,
      @Param("endDateTime") LocalDateTime endDateTime);

}
