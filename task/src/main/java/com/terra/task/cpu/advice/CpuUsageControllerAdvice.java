package com.terra.task.cpu.advice;

import com.terra.task.cpu.exception.InvalidDateRangeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CpuUsageControllerAdvice {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(InvalidDateRangeException.class)
  public String handleInvalidDateRangeException(InvalidDateRangeException e) {
    return e.getMessage();
  }
}
