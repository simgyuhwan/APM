package com.terra.task.cpu.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cpu_usage", indexes = @Index(name = "idx_timestamp", columnList = "timestamp"))
@NoArgsConstructor
public class CpuUsage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;

	@Column(name = "cpu_percentage", nullable = false)
	private Double cpuPercentage;

	private CpuUsage(Double cpuPercentage) {
		this.cpuPercentage = cpuPercentage;
	}

	private CpuUsage(LocalDateTime timestamp, Double cpuPercentage) {
		this.timestamp = timestamp;
		this.cpuPercentage = cpuPercentage;
	}

	public static CpuUsage of(Double cpuPercentage) {
		return new CpuUsage(LocalDateTime.now(),cpuPercentage);
	}

	public static CpuUsage of(LocalDateTime timestamp, Double cpuPercentage) {
		return new CpuUsage(timestamp,cpuPercentage);
	}
}
