package com.terra.task.cpu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terra.task.cpu.service.CpuUsageService;

import lombok.RequiredArgsConstructor;

@Tag(name = "Cpu Usage")
@RequestMapping("/cpu")
@RestController
@RequiredArgsConstructor
public class CpuUsageController {

	private final CpuUsageService cpuUsageService;

	@Operation(summary = "CPU 사용량 조회", description = " cpu 사용량 조회 기능")
	@GetMapping("/usage")
	public double getCpuUsage() {
		return cpuUsageService.getCpuUsage();
	}

}
