package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.SMSService;
import com.example.demo.service.SMSService.SMSVerificationCodeSendRequest;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-02-21
 */
@RestController
@RequiredArgsConstructor
public class VerificationCodeController {

	private final UserService userService;
	private final SMSService smsService;

	@GetMapping("/code")
	public void sendCode(@RequestParam String username) {
		UserService.UserBean user = userService.findByUsername(username);
		SMSVerificationCodeSendRequest request = SMSVerificationCodeSendRequest.builder()
				.secret(user.getSecret())
				.destinationPhone(user.getPhone())
				.build();
		smsService.sendVerificationCode(request);
	}

}
