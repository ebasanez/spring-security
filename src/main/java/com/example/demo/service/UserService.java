package com.example.demo.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.example.demo.dto.RegisterDto;

/**
 * @author ebasanez
 * @since 2021-02-05
 */
public interface UserService {

	/**
	 * Register user and generates 2FA QR Image
	 *
	 * @return 2FA image content
	 */
	String register(
			@NotNull @Valid RegisterDto dto);

}
