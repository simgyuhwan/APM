//package com.terra.task.cpu.schedule;
//
//import static org.awaitility.Awaitility.*;
//import static org.mockito.Mockito.*;
//
//import java.time.Duration;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//
//@SpringBootTest
//class CpuUsageSchedulerIntegrationTest {
//
//	@SpyBean
//	CpuUsageScheduler cpuUsageScheduler;
//
//	@Test
//	public void verifyCpuUsageSchedulerRunsTwice() {
//		await().atMost(Duration.ofMinutes(2)).untilAsserted(() -> {
//			verify(cpuUsageScheduler, atLeast(2)).run();
//		});
//	}
//}