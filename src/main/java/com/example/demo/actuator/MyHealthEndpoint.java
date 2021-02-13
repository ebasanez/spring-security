package com.example.demo.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.example.demo.service.ActiveUserService;

import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-02-09
 */
@Component
@RequiredArgsConstructor
public class MyHealthEndpoint implements HealthIndicator {

	private final ActiveUserService activeUserService;

	@Override
	public Health health() {
		try {
			activeUserService.countAllActiveUsers();
		} catch (Exception e) {
			Health.down().withDetail("error", "persistence read access error").build();
		}
		return Health.up().build();
	}
}
