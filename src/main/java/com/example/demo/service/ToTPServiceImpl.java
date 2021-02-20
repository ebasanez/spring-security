package com.example.demo.service;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import lombok.extern.slf4j.Slf4j;

/**
 * Created using guide: https://github.com/amrkhaledccd/two-factor-authentication/blob/master/auth-service/src/main/java/com/clone/instagram/authservice/service/TotpManager.java
 * @author ebasanez
 * @since 2021-02-20
 */
@Slf4j
@Service
public class ToTPServiceImpl implements ToTPService {

	private final SecureRandom randomBytes = new SecureRandom();
	private static final Base32 encoder = new Base32();

	@Value("${security.2fa.issuer}")
	private String issuer;

	@Value("${security.2fa.secret.length:32}")
	private int secretLength;

	@Override
	public String generateSecret() {
		byte[] bytes = new byte[secretLength * 5 / 8];
		this.randomBytes.nextBytes(bytes);
		return new String(encoder.encode(bytes));
	}

	@Override
	public String getUriForImage(String secret, String username) {

		QrData data = new QrData.Builder()
				.label(username)
				.secret(secret)
				.issuer(issuer)
				.algorithm(HashingAlgorithm.SHA1)
				.digits(6)
				.period(30)
				.build();

		QrGenerator generator = new ZxingPngQrGenerator();
		byte[] imageData = new byte[0];

		try {
			imageData = generator.generate(data);
		} catch (QrGenerationException e) {
			log.error("unable to generate QrCode");
		}

		String mimeType = generator.getImageMimeType();

		return getDataUriForImage(imageData, mimeType);
	}

	public boolean verifyCode(String secret,String code) {
		TimeProvider timeProvider = new SystemTimeProvider();
		CodeGenerator codeGenerator = new DefaultCodeGenerator();
		CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
		return verifier.isValidCode(secret, code);
	}
}
