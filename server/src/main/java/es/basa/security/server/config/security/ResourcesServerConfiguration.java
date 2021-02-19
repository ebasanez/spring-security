package es.basa.security.server.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Reseource Server configuration is in the same application that auth-server configuration
 *
 * @author ebasanez
 * @since 2021-02-14
 */
@Configuration
@EnableResourceServer
public class ResourcesServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.requestMatchers().antMatchers("/api/users/*")
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET,"/api/possession/**").access("#oauth2.hasScope('read')")
				.antMatchers(HttpMethod.POST,"/api/possession/**").access("#oauth2.hasScope('write')")
				.antMatchers(HttpMethod.DELETE,"/api/possession/**").access("#oauth2.hasScope('write')");

	}
}
