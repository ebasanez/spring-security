package es.basa.security.authserver.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

// TODO Migrar a Spring Security 5

/**
 * @author ebasanez
 * @since 2021-02-13
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Value("${security.oauth2.jwt.signing-key}")
	private String jwtSigningKey;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	//@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Value("${server.servlet.context-path}")
	private String appContextPath;

	// JWT

	/**
	 * Encodes JWT to have all information OAuth2 requires
	 * The Resource Server will need have a similar token converter (including the same signing key), in order to decrypt the token (symmetric cypher).
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey(jwtSigningKey);
		return accessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	/**
	 * Needed to enable refresh tokens.
	 * Replaces default Spring OAuth2 token services, in order to use our JWT token store.
	 */
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
				.tokenStore(tokenStore())
				.authenticationManager(authenticationManager)
				.accessTokenConverter(accessTokenConverter()) // Be careful to inject this instead of TokenStore
				.userDetailsService(userDetailsService)
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
	}


	/**
	 * 12.3 Here is where refresh token functionality is enabled, by adding refresh_token to authorizationGrantTypes.
	 * Refresh token has more life that access token, as the refresh token is used to request new access token when the original access token expires.
	 * Configure how clients connect to this authorization server.
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients
				.inMemory()

				.withClient("live-test")
				.secret(passwordEncoder().encode("1234qwer"))
				.authorizedGrantTypes("password", "refresh_token")
				.scopes(appContextPath) // Define url path with client will be valid for. Normaly this sholud match application basic context path
				.autoApprove(appContextPath)
				.accessTokenValiditySeconds(3600) // In seconds
				.refreshTokenValiditySeconds(3600 * 24); // In seconds
	}

	/**
	 * Enable (and expose) endpoint to check token validity
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("permitAll()");
		super.configure(security);
	}
	
}
