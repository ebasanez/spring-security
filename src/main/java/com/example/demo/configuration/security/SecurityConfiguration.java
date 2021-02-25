package com.example.demo.configuration.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author ebasanez
 * @since 2021-01-14
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		UserDetails user = User.builder()
				.passwordEncoder(passwordEncoder()::encode)
				.username("user")
				.password("pass")
				.roles("USER")
				.build();
		UserDetails admin = User.builder()
				.passwordEncoder(passwordEncoder()::encode)
				.username("admin")
				.password("pass")
				.roles("ADMIN")
				.build();
		// Equivalent to InMemoryUserDetails
		return new MapReactiveUserDetailsService(user, admin);
	}

	@Bean
	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
		return httpSecurity
				.authorizeExchange()
				.pathMatchers("/user/delete").hasRole("ADMIN")
				.anyExchange().authenticated()
				.and()
				.httpBasic()
				.and()
				.csrf().disable()
				.build();
	}

	/**
	 * We should:
	 * - Hash
	 * - Salt:
	 * -	- Fixed length
	 * -	- Credentials unique (not only user unique)
	 * -	- Long
	 * - Key stretching, to make hash function slow so brute forces are unpractical (around 500ms)
	 * -	* Stress test should be made against hashing service to check is it will be able to handle all authentication requests (or a DoS attack).
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}


}
