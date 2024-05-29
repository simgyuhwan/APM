package com.terra.task.cpu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terra.task.cpu.service.CpuUsageService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/cpu")
@RestController
@RequiredArgsConstructor
public class CpuUsageTestController {

	private final CpuUsageService cpuUsageService;

	@GetMapping
	public double getCpuUsage() {
		return cpuUsageService.getCpuUsage();
	}
}
