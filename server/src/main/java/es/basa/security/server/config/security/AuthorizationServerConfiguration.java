package es.basa.security.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

// TODO Migrar a Spring Security 5

/**
 * @author ebasanez
 * @since 2021-02-13
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients
				.inMemory()

				.withClient("live-test")
				.secret(passwordEncoder.encode("1234qwer"))
				.authorizedGrantTypes("authorization_code", "refresh_token") // Instead of 'password' grant type, we will use 'authorization_code'
				.scopes("read", "write");
	}


}
