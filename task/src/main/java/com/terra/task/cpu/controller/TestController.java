package com.terra.task.cpu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terra.task.cpu.controller.response.TestResponse;

@RequestMapping("/test")
@RestController
public class TestController {

	@GetMapping
	public TestResponse getTest() {
		return new TestResponse(1L, "test");
	}
}
