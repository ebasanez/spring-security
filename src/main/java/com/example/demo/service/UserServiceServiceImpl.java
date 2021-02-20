package com.example.demo.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
	private final ToTPService toTPService;

	@Override
	public String register(@NotNull @Valid RegisterDto dto) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setSecret(toTPService.generateSecret());
		userRepository.save(user);
		return toTPService.getUriForImage(user.getSecret(), user.getUsername());
	}

}
