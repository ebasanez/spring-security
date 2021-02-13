package com.example.demo.configuration.security;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.example.demo.filter.MyCustomFilter;
import com.example.demo.service.LockedUserService;

/**
 * @author ebasanez
 * @since 2021-01-14
 */
@Configuration
// Lesson 9.3 Use AOP to configure method security instead of default way
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private LockedUserService lockedUserService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	MyThirdPartyAuthenticationProvider myThirdPartyAuthenticationProvider;

	@Autowired
	private DataSource dataSource;

	@Autowired
	MyCustomFilter myCustomFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.addFilterBefore(myCustomFilter, AnonymousAuthenticationFilter.class)
				.authorizeRequests()
				.anyRequest().permitAll()
				//.accessDecisionManager(unanimous()) // 9.4 Custom decision manager
				.and()
				.csrf().disable()
				.formLogin()
				.and()
				.rememberMe().tokenRepository(persistentTokenRepository())
				.and()
				.logout()
				.and()
				.sessionManagement().maximumSessions(100).sessionRegistry(sessionRegistry()).and().sessionFixation().none();
	}

	/**
	 * To handle number of active sessions, sessions per user, etc....
	 */
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	/**
	 * Multiple authentication providers
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth
				/**
				 .eraseCredentials(false)
				 .authenticationProvider(myThirdPartyAuthenticationProvider)
				 .authenticationProvider(daoAuthenticationProvider());
				 **/
				.parentAuthenticationManager(providerManager());
	}

	/**
	 * Podemos declarar nuestra propia instancia del ProviderManager, en lugar de dejar que la cree la configuración (por si quisiéramos personalizar parte de su comportamiento).
	 * Es algo que no suele tener que hacerse
	 */
	@Bean
	ProviderManager providerManager() {
		ProviderManager providerManager = new ProviderManager(List.of(myThirdPartyAuthenticationProvider, daoAuthenticationProvider()));
		providerManager.setEraseCredentialsAfterAuthentication(false);
		return providerManager;
	}

	/**
	 * Repository for remember-me cookie tokens
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		final JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}

	/**
	 * We should:
	 * - Hash
	 * - Salt:
	 * -	- Fixed length
	 * -	- Credentials unique (not only user unique)
	 * -	- Long
	 * - Key stretching, to make hash function slow so brute forces are unpractical (around 500ms)
	 * -	* Stress test should be made against hashing service to check is it will be able to handle all authentication requests (or a DoS attack).
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

// 9.4 Custom AccessDecisionVoter

	/**
	 * Unanimous voter for all three voters (role, authenticated and web expression)
	 * The accessDecisionManager is invoker from {@link org.springframework.security.access.intercept.AbstractSecurityInterceptor}
	 */
	@Bean
	public AccessDecisionManager unanimous() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = List.of(new RoleVoter(), new AuthenticatedVoter(), new WebExpressionVoter(),
				new RealTimeLockVoter(lockedUserService));
		return new UnanimousBased(decisionVoters);
	}


}
