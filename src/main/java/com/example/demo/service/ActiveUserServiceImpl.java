package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-02-09
 */
@Service

@RequiredArgsConstructor
public class ActiveUserServiceImpl implements ActiveUserService {

	private final SessionRegistry sessionRegistry;

	@Override
	public List<String> getAllActiveUsers() {
		return sessionRegistry.getAllPrincipals().stream()
				.filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty()) // Remove users without non-expired sessions
				.map(UserDetails.class::cast)
				.map(UserDetails::getUsername)
				.collect(Collectors.toList());
	}

	@Override
	public int countAllActiveUsers() {
		return sessionRegistry.getAllPrincipals().size();
	}

}