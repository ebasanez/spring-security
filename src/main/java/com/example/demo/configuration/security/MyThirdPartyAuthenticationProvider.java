package com.example.demo.configuration.security;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author ebasanez
 * @since 2021-02-08
 */
@Component
public class MyThirdPartyAuthenticationProvider implements AuthenticationProvider {


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String username = authentication.getName();
		final String password = authentication.getCredentials().toString();

		if (!supportsAuthentication(authentication)) {
			return null;
		}
		if (doAuthenticationAgainstThirdPartySystem(username, password)) {
			// Example return token
			return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
		} else {
			throw new BadCredentialsException("Authentication against third party system failed");
		}
	}

	private boolean doAuthenticationAgainstThirdPartySystem(String username, String password) {
		// Check credentials against third party system
		// [...]
		// For now, this provider will always return false
		return false;
	}


	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}


	private boolean supportsAuthentication(Authentication authentication) {
		// Check any specific details of the authentication, to know if this provider can be used
		// [...]
		return true;
	}


}
