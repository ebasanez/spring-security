package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ActiveUserService;

import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-02-09
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

	private final ActiveUserService activeUserService;

	@GetMapping
	public List<String> getActiveUsers() {
		return activeUserService.getAllActiveUsers();
	}

}
