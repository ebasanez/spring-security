package es.basa.security.resourcesserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ebasanez
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/resources")
public class ProtectedResourceController {

	@GetMapping
	public String getResource(){
		return "Resource";
	}

}
