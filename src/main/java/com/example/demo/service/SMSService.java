package com.example.demo.service;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author ebasanez
 * @since 2021-02-21
 */
public interface SMSService {

	void sendVerificationCode(
			@NotNull SMSVerificationCodeSendRequest request);

	@Getter
	@Builder
	@ToString
	@EqualsAndHashCode
	class SMSVerificationCodeSendRequest {

		@NotNull
		private final String secret;
		@NonNull
		private final String destinationPhone;
	}
}
