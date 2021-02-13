package com.example.demo.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.repository.UserRepository;

/**
 * @author ebasanez
 * @since 2021-02-04
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableTransactionManagement
public class PersistenceConfiguration {
}
