package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ebasanez
 * @since 2021-02-20
 */
@RestController
public class MainController {

	@GetMapping("/hello")
	public String hello(){
		return "hello";
	}

}
