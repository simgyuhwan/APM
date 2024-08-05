package com.apm.client.config;

import jakarta.validation.constraints.NotBlank;

public class InfluxDBConfig {
  @NotBlank
  private String url;
  @NotBlank
  private String token;
  @NotBlank
  private String org;
  @NotBlank
  private String bucket;

  public InfluxDBConfig() {
  }

  public InfluxDBConfig(String url, String token, String org,
      String bucket) {
    this.url = url;
    this.token = token;
    this.org = org;
    this.bucket = bucket;
  }

  public String getUrl() {
    return url;
  }

  public String getToken() {
    return token;
  }

  public String getOrg() {
    return org;
  }

  public String getBucket() {
    return bucket;
  }
}
