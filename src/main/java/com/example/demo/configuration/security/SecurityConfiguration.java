package com.example.demo.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.util.TwilioProperties;
import com.twilio.sdk.TwilioRestClient;

/**
 * @author ebasanez
 * @since 2021-01-14
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyTwoFactorAuthenticationProvider mySecondFactorAuthenticationProvider;

	@Autowired
	private CustomWebAuthenticationDetailSource customWebAuthenticationDetailSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/register", "/code").permitAll()
				.anyRequest().authenticated()
				//.accessDecisionManager(unanimous()) // 9.4 Custom decision manager
				.and()
				.csrf().disable()
				.formLogin()
				.loginPage("/login").permitAll() // We will need a custom login page, in order to get an additional "code" field in the form.
				.loginProcessingUrl("/doLogin")
				.defaultSuccessUrl("/hello")
				.authenticationDetailsSource(customWebAuthenticationDetailSource)
				.and()
				.logout();
	}

	/**
	 * Multiple authentication providers
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		auth
				.authenticationProvider(mySecondFactorAuthenticationProvider);
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
