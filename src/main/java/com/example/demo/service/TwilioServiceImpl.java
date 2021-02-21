package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.stereotype.Service;

import com.example.demo.util.TwilioProperties;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import lombok.SneakyThrows;

/**
 * @author ebasanez
 * @since 2021-02-21
 */
@Service
public class TwilioServiceImpl implements SMSService {

	private final String senderNumber;
	private final TwilioRestClient twilioRestClient;

	public TwilioServiceImpl(TwilioProperties twilioProperties) {
		this.twilioRestClient = new TwilioRestClient(twilioProperties.getAccountId(), twilioProperties.getSecret());
		this.senderNumber = twilioProperties.getSender();
	}

	@Override
	@SneakyThrows
	public void sendVerificationCode(SMSVerificationCodeSendRequest request) {
		String code = new Totp(request.getSecret()).now();

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("Body", "The verification code is " + code));
		params.add(new BasicNameValuePair("To", request.getDestinationPhone()));
		params.add(new BasicNameValuePair("From", senderNumber));

		MessageFactory messageFactory = twilioRestClient.getAccount().getMessageFactory();
		Message message = messageFactory.create(params);
	}

}
