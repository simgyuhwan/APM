package com.terra.task.cpu.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CpuStats(LocalDateTime date, BigDecimal maxUsage, BigDecimal minUsage,
                       BigDecimal avgUsage) {

}
