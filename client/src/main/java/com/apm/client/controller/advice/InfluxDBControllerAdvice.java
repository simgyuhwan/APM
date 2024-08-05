package com.apm.client.controller.advice;

import com.apm.client.controller.InfluxDBController;
import com.apm.client.controller.response.Response;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = InfluxDBController.class)
public class InfluxDBControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Response methodArgumentNotValidException(MethodArgumentNotValidException e) {
    return new Response(false, "influxDB 연결 요청 정보가 올바르지 않습니다.");
  }
}
