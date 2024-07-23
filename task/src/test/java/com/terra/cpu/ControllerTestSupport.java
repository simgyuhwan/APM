package com.terra.cpu;

import com.terra.cpu.controller.CpuUsageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    CpuUsageController.class
})
public abstract class ControllerTestSupport {

  @Autowired
  protected MockMvc mockMvc;
}
