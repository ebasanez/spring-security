package es.basa.security.authserver.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;


/**
 * One of the simplest flows in OAuth2
 * There is no user credentials, only OAuth2 client credentials, so those are the only necessary to request the token.
 * There is no such thing as resource owner.
 * This flow is designed for confidential clients (ej: server-to-server communication).
 *
 * @author ebasanez
 * @since 2021-02-13
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
				.inMemory()
				.withClient("my-test-client")
				.secret(passwordEncoder().encode("1234qwer"))
				.authorizedGrantTypes("client_credentials")
				.scopes("read", "write")
				.and()
				.withClient("my-read-client")
				.secret(passwordEncoder().encode("1234qwer"))
				.authorizedGrantTypes("client_credentials")
				.scopes("read")
				.and()
				.withClient("my-write-client")
				.secret(passwordEncoder().encode("1234qwer"))
				.authorizedGrantTypes("client_credentials")
				.scopes("write")
		;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

}