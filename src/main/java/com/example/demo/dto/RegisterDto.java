package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * @author ebasanez
 * @since 2021-02-05
 */
@Data
public class RegisterDto {

	@NotBlank
	private String username;

	@NotBlank
	@Length(min = 4)
	private String password;
}
