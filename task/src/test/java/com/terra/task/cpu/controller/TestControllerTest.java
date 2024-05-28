package com.terra.task.cpu.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.*;
import static com.epages.restdocs.apispec.ResourceSnippetParameters.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(TestController.class)
class TestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	ResultActions resultActions;

	String json;

	@DisplayName("Just Test")
	@Test
	void getTest() throws Exception {
		mockMvc.perform(
				get("/test")
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("test"))
			.andExpect(jsonPath("$.id").value(1L))
			.andDo(document("test-get",
				builder()
					.tag("테스트")
					.summary("Get Test")
					.description("오직 테스트")
					.responseSchema(Schema.schema("MainResponse.Get"))
				,
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("name")
				)
			));
	}

}