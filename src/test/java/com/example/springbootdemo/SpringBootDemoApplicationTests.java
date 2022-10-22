package com.example.springbootdemo;

import com.example.springbootdemo.dto.Sample;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootDemoApplicationTests {

	// APIを発行するためのMockオブジェクトを生成
	@Autowired
	private MockMvc mockMvc;
	@Test
	void contextLoads() throws  Exception{
		// JavaのObjectをJSONに変換するためのクラスを生成
		ObjectMapper objectMapper = new ObjectMapper();

		// 結果を検証するためのクラスを生成して、期待値をセット
		Sample sample = new Sample();
		sample.setId(100);
		sample.setName("taro");

		// 「/hello」パスのAPIを実行してレスポンスを検証
		this.mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(sample)));
	}
}
