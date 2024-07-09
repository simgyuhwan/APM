package com.terra.task.cpu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "cpu_usage", indexes = @Index(name = "idx_timestamp", columnList = "timestamp"))
@NoArgsConstructor
public class CpuUsage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime timestamp;

  @Column(name = "cpu_percentage", nullable = false, precision = 5, scale = 1)
  private BigDecimal cpuPercentage;

  private CpuUsage(BigDecimal cpuPercentage) {
    this.cpuPercentage = cpuPercentage;
  }

  private CpuUsage(LocalDateTime timestamp, BigDecimal cpuPercentage) {
    this.timestamp = timestamp;
    this.cpuPercentage = cpuPercentage;
  }

  public static CpuUsage of(BigDecimal cpuPercentage) {
    return new CpuUsage(LocalDateTime.now(), cpuPercentage);
  }

  public static CpuUsage of(LocalDateTime timestamp, BigDecimal cpuPercentage) {
    return new CpuUsage(timestamp, cpuPercentage);
  }
}
