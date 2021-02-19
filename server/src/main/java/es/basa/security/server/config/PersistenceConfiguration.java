package es.basa.security.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import es.basa.security.server.repository.PossessionRepository;

/**
 * @author ebasanez
 * @since 2021-02-17
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = PossessionRepository.class)
@EnableTransactionManagement
public class PersistenceConfiguration {
}