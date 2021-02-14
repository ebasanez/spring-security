package es.basa.security.authserver.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

// TODO Migrar a Spring Security 5

/**
 * @author ebasanez
 * @since 2021-02-13
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	//@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Value("${server.servlet.context-path}")
	private String appContextPath;

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(10);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
				.tokenStore(tokenStore())
				.authenticationManager(authenticationManager)
				.allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
	}


	/**
	 * Configure how clients connect to this authorization server.
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients
				.inMemory()

				.withClient("live-test")
				.secret(passwordEncoder().encode("1234qwer"))
				.authorizedGrantTypes("password")
				.scopes(appContextPath) // Define url path with client will be valid for. Normaly this sholud match application basic context path
				.autoApprove(appContextPath)

				.accessTokenValiditySeconds(3600);
	}


}
