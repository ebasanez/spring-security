package com.example.demo.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RegisterDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-02-04
 */
@Service
@RequiredArgsConstructor
public class UserServiceServiceImpl implements UserService {

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
	public UserBean loadUserByUsernameAndTenant(String username, String tenant) throws UsernameNotFoundException {
		User example = new User();
		example.setUsername(username);
		example.setTenant(tenant);
		return userRepository.findOne(Example.of(example)).map(this::toUserDetails).orElse(null);
	}

	private UserBean toUserDetails(User entity) {
		return UserBean.builder()
				.username(entity.getUsername())
				.password(entity.getPassword())
				.tenant(entity.getTenant())
				.build();
	}

}
