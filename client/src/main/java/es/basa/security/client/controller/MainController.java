package es.basa.security.client.controller;

import java.util.List;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.basa.security.client.dto.Possession;
import es.basa.security.client.util.ApiProperties;
import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-02-18
 */
@Controller
@RequiredArgsConstructor
public class MainController {

	private final ApiProperties apiProperties;
	private final OAuth2RestTemplate restTemplate;

	@RequestMapping("/possessions")
	public ModelAndView list(){
		final List<Possession> users = restTemplate.getForObject(apiProperties+"/possessions", List.class);
		return new ModelAndView("list","possessions",users);
	}

}
