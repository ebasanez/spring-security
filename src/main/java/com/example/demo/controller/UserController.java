package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ActiveUserService;
import com.example.demo.service.LockedUserService;

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
	private final LockedUserService lockedUserService;

	@GetMapping
	public List<String> getActiveUsers() {
		return activeUserService.getAllActiveUsers();
	}

	@PostMapping("/locked/{username}")
	public void lockUser(@PathVariable String username) {
		lockedUserService.lockUser(username);
	}

}
