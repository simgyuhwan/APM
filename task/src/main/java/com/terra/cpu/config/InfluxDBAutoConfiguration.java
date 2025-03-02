package com.terra.cpu.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(InfluxDBProperties.class)
public class InfluxDBAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public InfluxDBClient influxDBClient(InfluxDBProperties prop) {
    return InfluxDBClientFactory.create(prop.getUrl(), prop.getToken().toCharArray(), prop.getOrg(),
        prop.getBucket());
  }

}
