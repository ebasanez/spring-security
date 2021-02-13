package com.example.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 * @author ebasanez
 * @since 2021-02-10
 */
@Service
public class LockedUserService {

	private final Set<String> lockedUsers = new HashSet<>();

	public boolean isLocked(String username) {
		return lockedUsers.contains(username);
	}

	public void lockUser(String username) {
		lockedUsers.add(username);
	}

}
