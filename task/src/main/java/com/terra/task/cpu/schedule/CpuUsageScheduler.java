package com.terra.task.cpu.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.terra.task.cpu.domain.CpuUsage;
import com.terra.task.cpu.repository.CpuUsageRepository;
import com.terra.task.cpu.service.CpuUsageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CpuUsageScheduler {
	private final CpuUsageRepository cpuUsageRepository;
	private final CpuUsageService cpuUsageService;

	@Scheduled(fixedDelay = 60000)
	public void run() {
		double cpuUsagePercent = cpuUsageService.getCpuUsage();
		CpuUsage cpuUsage = CpuUsage.of(cpuUsagePercent);
		cpuUsageRepository.save(cpuUsage);
	}
}