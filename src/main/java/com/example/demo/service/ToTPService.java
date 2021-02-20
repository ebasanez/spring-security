package com.example.demo.service;

import javax.validation.constraints.NotNull;

/**
 * @author ebasanez
 * @since 2021-02-20
 */
public interface ToTPService {

	String generateSecret();

	String getUriForImage(
			@NotNull String secret, String username);
	
	boolean verifyCode(
			@NotNull String secret,
			@NotNull String code);

}
