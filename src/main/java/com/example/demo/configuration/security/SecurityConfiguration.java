package com.example.demo.configuration.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.service.UserService;

/**
 * @author ebasanez
 * @since 2021-01-14
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Bean
	AuthenticationDetailsSource<HttpServletRequest, String> authenticationDetailsSource() {
		return new TenantDetailSource();
	}

	@Bean
	AuthenticationProvider tenantAuthenticationProvider() {
		return new TenantAuthenticationProvider(passwordEncoder(), userService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/register").permitAll()
				.anyRequest().authenticated()
				.and()
				.csrf().disable()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.loginProcessingUrl("/doLogin")
				.defaultSuccessUrl("/hi")
				.authenticationDetailsSource(authenticationDetailsSource())
				.and()
				.logout();
	}


	/**
	 * Multiple authentication providers
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		auth
				.authenticationProvider(tenantAuthenticationProvider());
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
