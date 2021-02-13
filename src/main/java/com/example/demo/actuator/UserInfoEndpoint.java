package com.example.demo.actuator;

import java.util.List;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import com.example.demo.service.ActiveUserService;

import lombok.RequiredArgsConstructor;

/**
 * @author ebasanez
 * @since 2021-02-09
 */
@Component
@Endpoint(id = "sessions")
@RequiredArgsConstructor
public class UserInfoEndpoint {

	private final ActiveUserService activeUserService;

	@ReadOperation
	public String[] usersInfo(){
		return activeUserService.getAllActiveUsers().toArray(new String[0]);
	}

}
