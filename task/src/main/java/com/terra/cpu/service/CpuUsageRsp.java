package com.terra.cpu.service;

import com.terra.cpu.domain.CpuUsage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "CPU 사용량 응답 모델")
public record CpuUsageRsp(@Schema(description = "시간") LocalDateTime date,
                          @Schema(description = "사용량") BigDecimal cpuPercentage) {

  public static CpuUsageRsp from(CpuUsage cpuUsage) {
    return new CpuUsageRsp(cpuUsage.getTimestamp(), cpuUsage.getCpuPercentage());
  }
}
