package com.terra.task.cpu.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestResponse {
	private Long id;
	private String name;

	public TestResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}

}
