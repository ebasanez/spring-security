package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * @author ebasanez
 * @since 2021-02-24
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

		private final UserRepository userRepository;

		@GetMapping
		public Mono<String> list(final Model model) {
			// @formatter:off
			return userRepository.findAll()
					.collectList()
					.doOnNext(users -> model.addAttribute("users", users))
					.map(users -> "users/list");
			// @formatter:on
		}

		@RequestMapping("{id}")
		public Mono<String> view(@PathVariable("id") Integer id, final Model model) {
			// @formatter:off
			return userRepository.findUser(id)
					.doOnNext(u -> model.addAttribute("user", u))
					.map(x -> "users/view");
			// @formatter:on
		}

		@GetMapping(params = "form")
		public Mono<String> createForm(@ModelAttribute User user) {
			return Mono.just("users/form");
		}

		@PostMapping
		public Mono<String> create(@Valid User user, BindingResult result, final Model model) {
			if (result.hasErrors()) {
				model.addAttribute("formErrors", result.getAllErrors());
				return Mono.just("users/form");
			}

			// @formatter:off
			return userRepository.save(user)
					.map(x -> {
						try {
							model.addAttribute("user.id", user.getId());
							return "redirect:/user/{user.id}?globalMessage=" + URLEncoder.encode("Successfully created a new user", "UTF-8");
						} catch (UnsupportedEncodingException e) {
							throw new IllegalStateException(String.format("Could not save user: %s", user.getUsername()));
						}
					});
			// @formatter:on
		}

		@RequestMapping("foo")
		public String foo() {
			throw new RuntimeException("Expected exception in controller");
		}

		@PreAuthorize("hasRole('ADMIN')")
		@GetMapping("delete/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public Mono<Void> delete(@PathVariable Integer id) {
			this.userRepository.deleteUser(id);
			return Mono.empty();
		}

		@GetMapping("modify/{id}")
		public Mono<String> modifyForm(@PathVariable("id") Integer id, final Model model) {
				return userRepository.findUser(id)
					.doOnNext(user -> model.addAttribute("user", user))
					.map(x -> "users/form");

		}

		@GetMapping(value = "events")
		public String getAllUsers(final Model model) {
			// @formatter:off
			final ReactiveDataDriverContextVariable dataDriver = new ReactiveDataDriverContextVariable(Flux.zip(Flux.interval(Duration.ofMillis(1000)), Flux.fromStream(Stream.generate(() -> "New Notification" + new Date())))
					.map(Tuple2::getT2));
			// @formatter:on

			model.addAttribute("notifications", dataDriver);
			return "users/events";
		}

	}