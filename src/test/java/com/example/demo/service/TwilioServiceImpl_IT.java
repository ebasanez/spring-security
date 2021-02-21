package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.util.TwilioProperties;

/**
 * @author ebasanez
 * @since 2021-02-21
 */
@WebMvcTest
@TestPropertySource(locations = {"classpath:credentials.properties"})
@ContextConfiguration(classes = {TwilioProperties.class, TwilioServiceImpl.class})
public class TwilioServiceImpl_IT {

	@Autowired
	private TwilioServiceImpl twilioService;

	@Test
	public void sendSMS(){
		SMSService.SMSVerificationCodeSendRequest request = SMSService.SMSVerificationCodeSendRequest.builder()
				.destinationPhone("+34669392424")
				.secret("E5N5U6FGYDPNMT563AQP7M3QUJFCAPYO")
				.build();
		twilioService.sendVerificationCode(request);
	}


}