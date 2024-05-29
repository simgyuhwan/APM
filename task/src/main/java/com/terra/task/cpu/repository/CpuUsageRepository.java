package com.terra.task.cpu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.terra.task.cpu.domain.CpuUsage;

public interface CpuUsageRepository extends JpaRepository<CpuUsage, Long> {
}
