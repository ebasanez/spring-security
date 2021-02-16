package es.basa.security.resourcesserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * The only difference between this OAuth2 security configuration is the fact that we are extending ResourceServerConfigurerAdapter and enabling resource server via annotation EnableResourceServer
 *
 * @author ebasanez
 * @since 2021-02-14
 */
@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourcesServerConfiguration extends ResourceServerConfigurerAdapter {

	// Gobal security concerns: user retrievals and checks

	@Value("${security.oauth2.jwt.signing-key}")
	private String jwtSigningKey;

	@Autowired
	private UserDetailsService userDetailsService;

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
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		7876922,
				8237934,
				7876961,
				7876963,
				7876956,
				7876953,
				7876938,
				8237985,
				7877687,
				7877667,
				8238654,
				7877730,
				7877731,
				7877718,
				7877702,
				7877691,
				7877579,
				7877575,
				7877535,
				7877540,
				7877652,
				7877621,
				7877402,
				7877349,
				7877394,
				7877321,
				7877223,
				7877492,
				7877475,
				7877440,
				8238189,
				8238171,
				8238135,
				8238137,
				7877214,
				8238212,
				8238217,
				7877188,
				7877192,
				7878045,
				7878052,
				7878038,
				7878096,
				7878090,
				7877967,
				8238939,
				7877960,
				7878013,
				7878030,
				7877988,
				7877969,
				8238846,
				7877847,
				7877947,
				7877945,
				7877895,
				8238757,
				7877737,
				7877786,
				7877803,
				7877783,
				7877761,
				7877764,
				7878417,
				7878425,
				7878319,
				8239289,
				7878317,
				8239276,
				8239275,
				7878406,
				7878375,
				7878351,
				7878243,
				7878206,
				7878198,
				7878302,
				7878292,
				7878267,
				7878290,
				7878250,
				7878256,
				7878148,
				7878131,
				7878134,
				7878113,
				7878172,
				7878162,
				7878160,
				7876179,
				8237260,
				7876135,
				7876133,
				7876187,
				7876194,
				7876185,
				7876022,
				7876106,
				7876019,
				8237074,
				7876131,
				8237198,
				7876125,
				7876107,
				7875956,
				7875991,
				7875990,
				8237032,
				7875975,
				7875981,
				7875916,
				7875877,
				7875915,
				7875952,
				7875935,
				8236976,
				7876390,
				7876377,
				7876406,
				7876392,
				7876355,
				7876367,
				7876345,
				7876376,
				7876369,
				8237449,
				7876368,
				7876310,
				7876297,
				8237406,
				7876339,
				7876207,
				7876210,
				8237359,
				7876231,
				7876237,
				7876213,
				7876691,
				7876695,
				7876686,
				7999883,
				8237732,
				7876710,
				8237768,
				7876706,
				7876571,
				7876547,
				7876565,
				7876546,
				8237582,
				7876640,
				7876683,
				7876620,
				7876521,
				7876520,
				7876494,
				7876534,
				7876533,
				7876450,
				7876435,
				7876439,
				7876478,
				7876491,
				8237520,
				7876893,
				7876877,
				7876860,
				7876804,
				7876811,
				7876715,
				7876763,
				7876727,
				7874991,
				7875050,
				7875059,
				7875018,
				7875030,
				7874940,
				7874973,
				7874967,
				7874898,
				7874823,
				8235871,
				7874802,
				8235854,
				7874786,
				7874809,
				7875328,
				7875323,
				7875313,
				8236350,
				7875283,
				7875261,
				7875290,
				7875249,
				7875210,
				7875258,
				7875197,
				7875169,
				8236230,
				7875118,
				7875124,
				7875072,
				8236596,
				7875533,
				7875578,
				7875558,
				7875551,
				8236512,
				7875471,
				7875461,
				7875464,
				7875427,
				7875456,
				8236468,
				7875497,
				7875511,
				7875489,
				8236532,
				8236517,
				7875483,
				7875412,
				8236461,
				7875407,
				8236446,
				8236464,
				7875388,
				7875385,
				7875352,
				7875338,
				8236445,
				7875395,
				7875845,
				7875839,
				7875836,
				7875864,
				8236912,
				8236913,
				7875856,
				7875851,
				7999235,
				7875833,
				8236883,
				8236885,
				8236873,
				7875754,
				7875758,
				7875670,
				7875719,
				7875802,
				7875807,
				7875800,
				7875779,
				7875776,
				8236663,
				7875581,
				7875636,
				7875638,
				7875617,
				7873808,
				7873806,
				7873842,
				7873792,
				7873790,
				7873775,
				8234881,
				8234878,
				7873798,
				7873734,
				7873746,
				7873764,
				7873759,
				7873761,
				7873641,
				7873617,
				7873624,
				7873699,
				7873700,
				7873685,
				7873660,
				7874113,
				7874109,
				7874097,
				7874075,
				7874141,
				7874133,
				7874125,
				7874029,
				8235108,
				8235062,
				8235064,
				7873965,
				7873976,
				7874073,
				7874071,
				7874061,
				8235114,
				7873934,
				7873910,
				7873951,
				7873942,
				8234947,
				7873856,
				7873854,
				7873896,
				7873893,
				7873890,
				7874533,
				7874514	return authenticationProvider;
	}

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());

	}

	// http security concerns

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.
				authorizeRequests().anyRequest().authenticated()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.csrf().disable(); // No need of csrf, as this is API oriented, and all urls are secured
	}
}
