package es.basa.security.client.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ebasanez
 * @since 2021-02-18
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

	private String endpoint;

}
