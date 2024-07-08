package com.terra.task.cpu.config;

import java.util.Random;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CommonConfig {

  @Bean
  public Random random(){
    return new Random();
  }

}
