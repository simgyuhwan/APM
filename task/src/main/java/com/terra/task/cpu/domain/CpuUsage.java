package com.terra.task.cpu.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "cpu_usage", indexes = @Index(name = "idx_timestamp", columnList = "timestamp"))
public class CpuUsage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;

	@Column(name = "cpu_percentabe", nullable = false)
	private Double cpuPercentage;

	public CpuUsage(Double cpuPercentage) {
		this.timestamp = LocalDateTime.now();
		this.cpuPercentage = Math.round(cpuPercentage * 100) / 100.0;
	}
}
