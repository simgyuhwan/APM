package com.terra.cpu.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.terra.cpu.ControllerTestSupport;
import com.terra.cpu.domain.CpuStats;
import com.terra.cpu.service.CpuUsageRsp;
import com.terra.cpu.service.CpuUsageService;
import com.terra.cpu.service.SystemStatsInfo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

class CpuUsageControllerTest extends ControllerTestSupport {

  @MockBean
  CpuUsageService cpuUsageService;

  @MockBean
  SystemStatsInfo systemStatsInfo;

  @Test
  void 지정한_시간의_분단위_CPU_사용률을_조회한다() throws Exception {
    // given
    CpuUsageRsp cpuUsageRsp = new CpuUsageRsp(LocalDateTime.now(), BigDecimal.TEN);
    List<CpuUsageRsp> responseBody = List.of(cpuUsageRsp);
    given(cpuUsageService.findMinutesCpuUsage(any(), any())).willReturn(responseBody);

    // when, then
    mockMvc.perform(
            get("/cpu/usage/minutes")
                .queryParam("startDateTime", String.valueOf(LocalDateTime.now()))
                .queryParam("endDateTime", String.valueOf(LocalDateTime.now())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].cpuPercentage").value(10.0));
  }

  @Test
  void 지정한_날짜의_시단위_CPU_최소_최대_평균_사용률을_조회한다() throws Exception {
    // given
    CpuStats cpuStats = new CpuStats(LocalDateTime.now(), BigDecimal.TEN, BigDecimal.TEN,
        BigDecimal.TEN);
    List<CpuStats> responseBody = List.of(cpuStats);
    given(cpuUsageService.findCpuUsage(any(), any(), any())).willReturn(responseBody);

    // when, then
    mockMvc.perform(
            get("/cpu/usage")
                .queryParam("startDateTime", String.valueOf(LocalDateTime.now()))
                .queryParam("endDateTime", String.valueOf(LocalDateTime.now()))
                .queryParam("dateType", "hours"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].maxUsage").value(10.0))
        .andExpect(jsonPath("$[0].minUsage").value(10.0))
        .andExpect(jsonPath("$[0].avgUsage").value(10.0));
  }

  @Test
  void 지정한_날짜_구간의_일단위_CPU_최소_최대_평균_사용률을_조회한다() throws Exception {
    // given
    CpuStats cpuStats = new CpuStats(LocalDateTime.now(), BigDecimal.TEN, BigDecimal.TEN,
        BigDecimal.TEN);
    List<CpuStats> responseBody = List.of(cpuStats);
    given(cpuUsageService.findCpuUsage(any(), any(), any())).willReturn(responseBody);

    // when, then
    mockMvc.perform(
            get("/cpu/usage")
                .queryParam("startDateTime", String.valueOf(LocalDateTime.now()))
                .queryParam("endDateTime", String.valueOf(LocalDateTime.now()))
                .queryParam("dateType", "days"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].maxUsage").value(10.0))
        .andExpect(jsonPath("$[0].minUsage").value(10.0))
        .andExpect(jsonPath("$[0].avgUsage").value(10.0));
  }

}