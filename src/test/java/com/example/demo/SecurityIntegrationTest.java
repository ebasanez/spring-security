package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.controller.UserController;

import reactor.test.StepVerifier;

/**
 * @author ebasanez
 * @since 2021-02-25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SecurityIntegrationTest {

	@Autowired
	private UserController userController;

	@Test
	public void whenDeleting_thenAccessDenied(){
		StepVerifier.create(userController.delete(1)).expectError(AccessDeniedException.class).verify();
	}

	@Test
	@WithMockUser(roles = "USER")
	public void whenDeleting_withUserRole_thenAccessDenied(){
		StepVerifier.create(userController.delete(1)).expectError(AccessDeniedException.class).verify();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void whenDeleting_withAdminRole_thenAccessGranted(){
		StepVerifier.create(userController.delete(1)).verifyComplete();
	}

}
