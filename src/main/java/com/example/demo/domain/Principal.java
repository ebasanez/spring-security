package com.example.demo.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author ebasanez
 * @since 2021-02-22
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Principal implements UserDetails {

	@NonNull
	private final String username;
	@NonNull
	private final String password;
	@NonNull
	private final String tenant;

	private final Collection<? extends GrantedAuthority> authorities;
	private final boolean accountNonExpired = true;

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
