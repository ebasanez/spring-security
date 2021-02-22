package com.example.demo.configuration.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.domain.Principal;

/**
 * @author ebasanez
 * @since 2021-02-22
 */
public class TenantDaoAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		String userTenant = ((Principal)userDetails).getTenant();
		String authTenant = (String)authentication.getDetails();
		if(!userTenant.equals(authTenant)) {
			throw new BadCredentialsException("Incorrect tenant information");
		}
		super.additionalAuthenticationChecks(userDetails, authentication);
	}

}
