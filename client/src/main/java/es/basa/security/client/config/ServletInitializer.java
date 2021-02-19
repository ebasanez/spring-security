package es.basa.security.client.config;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * @author ebasanez
 * @since 2021-02-18
 */
public class ServletInitializer extends AbstractDispatcherServletInitializer {

	@Override
	protected WebApplicationContext createServletApplicationContext() {
		final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebMVCConfiguration.class);
		return context;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		return null;
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		registerProxyFilter(servletContext, "oauth2ClientContextRedirect");

	}

	void registerProxyFilter(ServletContext servletContext, String name) {
		final DelegatingFilterProxy filter = new DelegatingFilterProxy(name);
		filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
		servletContext.addFilter(name, filter).addMappingForUrlPatterns(null, false, "/*");
	}
}