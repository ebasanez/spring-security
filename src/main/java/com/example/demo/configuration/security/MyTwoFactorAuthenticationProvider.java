package com.example.demo.configuration.security;

/**
 * @since 2021-02-20
 */

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.configuration.security.CustomWebAuthenticationDetailSource.CustomWebAuthenticationDetails;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ToTPService;

/**
 * This class perform two factor authentication:
 * - Check password, in a way very similar to a DaoAuthenticationProvider.
 * - Check verification token using totp
 */
@Component
public class MyTwoFactorAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private ToTPService toTPService;

	@Override
	public Authentication authenticate(Authentication auth) {
		// 1. Fist factor: username-password
		final String username = auth.getName();
		final String password = auth.getCredentials().toString();
		// Obtain the verification code from the details object we injected to the authorization
		final String verificationCode = ((CustomWebAuthenticationDetails) auth.getDetails()).getVerificationCode();
		final User user = userRepository.findByUsername(username);
		if ((user == null) || !encoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}
		// Second factor: Check verification code is valid:
		// We use a totp (time-based one time password) external library from jboss.
		try {
			if (!toTPService.verifyCode(user.getSecret(), verificationCode)) {
				throw new BadCredentialsException("Invalid verification code");
			}
		} catch (final Exception e) {
			throw new BadCredentialsException("Invalid verification code");
		}

		return new UsernamePasswordAuthenticationToken(user, password, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}