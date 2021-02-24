package com.example.demo.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * As we are using spring-boot-data-redis, there is already a RedisConnectionFactory registered in the context.
 * The same way, a filter that enables our web server (ie: tomcat) to use this session, is registered by spring-boot.
 * @author ebasanez
 * @since 2021-02-23
 */
@Configuration
@EnableRedisHttpSession
public class HttpSessionConfiguration {

	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory();
	}

}
