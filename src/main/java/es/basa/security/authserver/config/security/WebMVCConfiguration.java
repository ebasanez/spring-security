package es.basa.security.authserver.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author ebasanez
 * @since 2021-02-19
 */
@Configuration
@EnableWebMvc
public class WebMVCConfiguration extends WebSecurityConfigurerAdapter {
}
