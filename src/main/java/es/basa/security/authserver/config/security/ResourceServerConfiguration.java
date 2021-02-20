package es.basa.security.authserver.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author ebasanez
 * @since 2021-02-19
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/**").access("#oauth2.hasScope('read')")
				.antMatchers(HttpMethod.POST, "/api/**").access("#oauth2.hasScope('write')")
				.antMatchers(HttpMethod.DELETE, "/api/**").access("#oauth2.hasScope('write')");
	}

}
