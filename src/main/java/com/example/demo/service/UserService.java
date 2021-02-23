package com.example.demo.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.example.demo.dto.RegisterDto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;


/**
 * @author ebasanez
 * @since 2021-02-05
 */
public interface UserService {

	UserBean loadUserByUsernameAndTenant(
			@NotNull String username,
			@NotNull String tenant);

	void register(
			@NotNull @Valid RegisterDto dto);

	@Getter
	@Builder
	@ToString
	@EqualsAndHashCode
	class UserBean {

		@NonNull
		private final String username;
		@NonNull
		private final String password;
		@NonNull
		private final String tenant;

	}

}
