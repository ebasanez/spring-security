package com.example.demo.configuration.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ebasanez
 * @since 2021-02-20
 */
@Component
public class CustomWebAuthenticationDetailSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

	@Override
	public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
		return new CustomWebAuthenticationDetails(context);
	}

	/**
	 * This component will be injected to the authentication object in its details field, and will be retrieved using {@link Authentication#getDetails()}
	 *
	 * @author ebasanez
	 * @since 2021-02-20
	 */
	@Getter
	@Setter
	@ToString
	@EqualsAndHashCode
	public static class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

		private final String verificationCode;

		public CustomWebAuthenticationDetails(HttpServletRequest request) {
			super(request);
			this.verificationCode = request.getParameter("code");
		}
	}

}
