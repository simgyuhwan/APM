package com.terra.task;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class Test {

  public static void main(String[] args) {
    // 새로운 스레드를 생성하여 CPU 사용량을 늘리는 부하 작업을 실행합니다.
    new Thread(() -> {
      while (true) {
        double value = Math.random() * Math.random();
      }
    }).start();

    // 메인 스레드에서 CPU 사용량을 모니터링 합니다.
    OperatingSystemMXBean systemMXBean =
        (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    while (true) {
      // 시스템 전체의 CPU 사용량을 가져옵니다.
      double systemCpuLoad = systemMXBean.getSystemCpuLoad();

      // 프로세스의 CPU 사용량을 가져옵니다.
      double processCpuLoad = systemMXBean.getProcessCpuLoad();

      System.out.printf("System CPU Load: %.2f%%\n", systemCpuLoad * 100);
      System.out.printf("Process CPU Load: %.2f%%\n", processCpuLoad * 100);

      // 1초마다 업데이트 합니다.
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

}
