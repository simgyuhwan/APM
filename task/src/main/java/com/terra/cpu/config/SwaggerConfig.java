package com.terra.cpu.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("APM")
        .pathsToMatch("/**")
        .build();
  }

  @Bean
  public GroupedOpenApi adminApi() {
    return GroupedOpenApi.builder()
        .group("APM-ADMIN")
        .pathsToMatch("/admin/**")
        .build();
  }

  @Bean
  public OpenAPI apmOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("APM")
            .description("APM Project")
            .version("v0.0.1")
            .license(new License().name("Apache 2.0").url("http://localhost"))
        )
        .externalDocs(new ExternalDocumentation()
            .description("test")
            .url("http://localhost")
        );
  }
}
