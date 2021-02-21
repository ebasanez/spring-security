package com.example.demo.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ebasanez
 * @since 2021-02-21
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "twilio.api")
public class TwilioProperties {

	private String accountId;
	private String secret;
	private String sender;

}
