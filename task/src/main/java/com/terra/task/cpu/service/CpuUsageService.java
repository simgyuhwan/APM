package com.terra.task.cpu.service;

import java.lang.management.ManagementFactory;

import org.springframework.stereotype.Service;

import com.sun.management.OperatingSystemMXBean;

@Service
public class CpuUsageService {
	private final OperatingSystemMXBean systemMXBean;

	public CpuUsageService() {
		this.systemMXBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
	}

	public double getCpuUsage() {
		return systemMXBean.getProcessCpuLoad() * 100;
	}
}

