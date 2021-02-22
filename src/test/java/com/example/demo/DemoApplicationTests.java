package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;

@SpringBootTest
@TestPropertySource({"classpath:application.properties","classpath:credentials.properties"})
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
