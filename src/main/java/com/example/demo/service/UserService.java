package com.example.demo.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.example.demo.dto.RegisterDto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ebasanez
 * @since 2021-02-05
 */
public interface UserService {

	/**
	 * Register user and generates 2FA QR Image
	 */
	void register(
			@NotNull @Valid RegisterDto dto);

	UserBean findByUsername(
			@NotNull String username);

	@Getter
	@Builder
	@ToString
	@EqualsAndHashCode
	class UserBean {
		private final Integer id;
		private final String username;
		private final String secret;
		private final String phone;
	}

}
