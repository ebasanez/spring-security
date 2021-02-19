package es.basa.security.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.basa.security.server.model.Possession;
import es.basa.security.server.repository.PossessionRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/api/possessions")
@RequiredArgsConstructor
public class PossessionController {

	private final PossessionRepository possessionRepository;

	@GetMapping
	public List<Possession> list() {
		return possessionRepository.findAll();
	}

	@GetMapping("/{id}")
	public Possession get(@PathVariable Integer id) {
		return possessionRepository.getOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Possession create(@Valid Possession possession) {
		return possessionRepository.save(possession);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		possessionRepository.deleteById(id);
	}


}
