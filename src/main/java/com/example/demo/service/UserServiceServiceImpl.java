package com.example.demo.service;

import java.util.Collection;
import java.util.Collections;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RegisterDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-02-04
 */
@Service
@RequiredArgsConstructor
public class UserServiceServiceImpl implements UserService, UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void register(@NotNull @Valid RegisterDto dto) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User example = new User();
		example.setUsername(username);
		return userRepository.findOne(Example.of(example)).map(this::toUserDetails).orElse(null);
	}

	private UserDetails toUserDetails(User entity) {
		return MyUserDetails.builder()
				.username(entity.getUsername())
				.password(entity.getPassword())
				.authorities(Collections.emptySet())
				.build();
	}

	@Getter
	@Builder
	private static class MyUserDetails implements UserDetails {

		private final String username;
		private final String password;
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

}
