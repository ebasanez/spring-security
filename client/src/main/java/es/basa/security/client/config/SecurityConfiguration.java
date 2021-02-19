package es.basa.security.client.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * @author ebasanez
 * @since 2021-02-18
 */
@Configuration
@EnableOAuth2Client
public class SecurityConfiguration {

	@Bean
	public OAuth2RestTemplate oAuth2RestTemplate(final OAuth2ClientContext clientContext){
		return new OAuth2RestTemplate(resourceDetails(), clientContext);
	}

	@Bean
	@ConfigurationProperties(prefix = "oauth2.client")
	public OAuth2ProtectedResourceDetails resourceDetails(){
		return new AuthorizationCodeResourceDetails();
	}

}
