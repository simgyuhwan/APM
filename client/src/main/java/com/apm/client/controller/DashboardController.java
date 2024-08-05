package com.apm.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

  @GetMapping("/dashboard")
  public String dashboardToConnect(@RequestParam("org") String org, Model model) {
    return "connect.html";
  }
}
