package com.example.demo.configuration.security;

import java.util.Collections;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.domain.Principal;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * In this scenario, we need tenant information to retrieve user from ddbb.
 * As userDetailsService only uses username to retrieve that, we will need to implement our own user retrieval method, so we can not extend DaoAuthenticationProvider but its parent class.
 * To make it easier, we are going to copy the code of {@link DaoAuthenticationProvider} and modify when needed.
 *
 * @author ebasanez
 * @since 2021-02-22
 */
@RequiredArgsConstructor
public class TenantAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private final PasswordEncoder passwordEncoder;
	private final UserService userService;

	/**
	 * The password used to perform {@link PasswordEncoder#matches(CharSequence, String)}
	 * on when the user is not found to avoid SEC-2056. This is necessary, because some
	 * {@link PasswordEncoder} implementations will short circuit if the password is not
	 * in a valid format.
	 */
	private volatile String userNotFoundEncodedPassword = "userNotFoundPassword";

	@Override
	@SuppressWarnings("deprecation")
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			this.logger.debug("Failed to authenticate since no credentials provided");
			throw new BadCredentialsException(this.messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
		String presentedPassword = authentication.getCredentials().toString();
		if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			this.logger.debug("Failed to authenticate since password does not match stored value");
			throw new BadCredentialsException(this.messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	@Override
	protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		try {
			String authTenant = (String) authentication.getDetails();
			UserService.UserBean loadedUser = userService.loadUserByUsernameAndTenant(username, authTenant);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"UserDetailsService returned null, which is an interface contract violation");
			}

			return toPrincipal(loadedUser);
		} catch (UsernameNotFoundException ex) {
			mitigateAgainstTimingAttack(authentication);
			throw ex;
		} catch (InternalAuthenticationServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() != null) {
			String presentedPassword = authentication.getCredentials().toString();
			this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
		}
	}

	private Principal toPrincipal(UserService.UserBean entity) {
		return Principal.builder()
				.username(entity.getUsername())
				.password(entity.getPassword())
				.tenant(entity.getTenant())
				.authorities(Collections.emptySet())
				.build();
	}

}
