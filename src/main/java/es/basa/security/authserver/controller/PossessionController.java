package es.basa.security.authserver.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ebasanez
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/api/possessions")
public class PossessionController {

	@GetMapping
	public List getPossessions() {
		// Dummy response
		return Collections.emptyList();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Valid Possession dto) {
		// Dummy method
	}

	@Getter
	@Setter
	public static class Possession {
		private Integer id;
		private String name;
	}

}
