package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Population of authentication  is not enabled by default in Spring Security, as Security Context holder is threadLocal.
 * To enable its population, a VM argument must be set:
 * -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL
 *
 * @author ebasanez
 * @since 2021-02-08
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {

}
