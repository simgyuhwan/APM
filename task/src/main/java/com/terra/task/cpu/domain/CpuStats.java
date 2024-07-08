package com.terra.task.cpu.domain;

import java.math.BigDecimal;

public record CpuStats(BigDecimal maxUsage, BigDecimal minUsage, BigDecimal avgUsage) {

}
