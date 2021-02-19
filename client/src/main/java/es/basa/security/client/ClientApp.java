package es.basa.security.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author ebasanez
 * @since 2021-02-14
 */
@SpringBootApplication
public class ClientApp {
	public static void main(String[] args) {
		SpringApplication.run(ClientApp.class, args);
	}
}
