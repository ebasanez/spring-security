package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Simple filter just to show how a filter can be add to the filter chain
 *
 * @author ebasanez
 * @since 2021-02-08
 */
@Component
public class MyCustomFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String url = httpServletRequest.getRequestURL().toString();
		logger.info(String.format("Applying MyCustomFilter to URI: %s", url));
		filterChain.doFilter(request, response);
	}
}
