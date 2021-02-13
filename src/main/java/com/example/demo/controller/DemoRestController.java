package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

/**
 * @author ebasanez
 * @since 2021-01-14
 */
@RestController
public class DemoRestController {

	@GetMapping("/healthcheck")
	public MyDto healthcheck() {
		return new MyDto("ok");
	}

	@Data
	public class MyDto {
		private final String status;
	}

}
