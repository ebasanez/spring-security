package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.MyService;

import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-01-14
 */
@Controller
@RequiredArgsConstructor
public class DemoController {

	private final MyService myService;

	@GetMapping("hi")
	@PreAuthorize("isAuthenticated()")
	public String helloWorld() {
		myService.myAsyncMethod();
		System.out.println(Thread.currentThread().getName());
		return "hello";
	}

	// Lesson 9.3 Secured via AOP defined in SecurityConfiguration.MethodSecurityConfig
	@GetMapping("bye")
	@PreAuthorize("isAuthenticated()")
	public String byeWorld() {
		return "hello";
	}

}
