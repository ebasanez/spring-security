package com.example.demo.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.RegisterDto;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-02-05
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("register")
public class RegisterController {

	private final UserService userService;

	@GetMapping
	ModelAndView registerForm() {
		return new ModelAndView("register", Map.of("user", new RegisterDto()));
	}

	@PostMapping
	String register(@Valid @ModelAttribute("user") RegisterDto user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "register";
		}
		userService.register(user);
		return "redirect:hi";
	}

}
