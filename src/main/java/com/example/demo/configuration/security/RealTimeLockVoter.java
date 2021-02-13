package com.example.demo.configuration.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import com.example.demo.service.LockedUserService;

import lombok.RequiredArgsConstructor;

/**
 * Custom Access Voter that vote checking authorized user in a list of locked users.
 *
 * @author ebasanez
 * @since 2021-02-10
 */
@RequiredArgsConstructor
public class RealTimeLockVoter implements AccessDecisionVoter<Object> {

	private final LockedUserService lockedUserService;

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		if (lockedUserService.isLocked(authentication.getName())) {
			return ACCESS_DENIED;
		} else {
			return ACCESS_GRANTED;
		}
	}

}
