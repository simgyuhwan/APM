package com.terra.cpu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping
  public String mainTest() {
    return "index.html";
  }
}
