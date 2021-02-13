package com.example.demo;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;

import static org.junit.Assert.*;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.FormAuthConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * 11.3.2
 *
 * @author ebasanez
 * @since 2021-02-13
 */
public class AclLiveTest {

	private static String APP_ROOT = "http://localhost:8082/demo";
	private final FormAuthConfig formAuthConfig = new FormAuthConfig(APP_ROOT + "/doLogin", "username", "password");

	// tests

	@Test
	public void givenOwnerUser_whenGetPossession_thenOk() {
		final Response response = givenAuth("juan", "juan").get(APP_ROOT + "/possessions/2");
		assertEquals(200, response.getStatusCode());
		assertTrue(response.asString().contains("id"));
	}

	@Test
	public void givenOwnerUser_whenGetPossession_thenNotOk() {
		final Response response = givenAuth("juan", "juan").get(APP_ROOT + "/possessions/1");
		assertEquals(403, response.getStatusCode());
	}
	//

	private RequestSpecification givenAuth(String username, String password) {
		return RestAssured.given().auth().form(username, password, formAuthConfig);
	}
}
