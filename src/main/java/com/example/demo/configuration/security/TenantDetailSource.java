package com.example.demo.configuration.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

/**
 * Will extract a parameter from  the authorization request.
 * We could have used any other way (encrypted header) to send the tenant.
 * @author ebasanez
 * @since 2021-02-22
 */
public class TenantDetailSource implements AuthenticationDetailsSource<HttpServletRequest,String> {

	@Override
	public String buildDetails(HttpServletRequest request) {
		return request.getParameter("tenant");
	}

}
