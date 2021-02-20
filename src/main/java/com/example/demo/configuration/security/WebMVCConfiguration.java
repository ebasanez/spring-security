package com.example.demo.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ebasanez
 * @since 2021-02-20
 */
@Configuration
public class WebMVCConfiguration implements WebMvcConfigurer {

	// Simple way to enable our login endpoint
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login")
				.setViewName("login");
		// registry.addViewController("/profile");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

}

