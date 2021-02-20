import org.junit.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ebasanez
 * @since 2021-02-19
 */
public class LiveTest {

	private static final String CLIENT_USERNAME = "my-test-client";
	private static final String CLIENT_READ_USERNAME = "my-read-client";
	private static final String CLIENT_WRITE_USERNAME = "my-write-client";
	private static final String CLIENT_PASSWORD = "1234qwer";
	private static final String APP_ROOT = "http://localhost:8082/server";

	@Test
	public void whenObtainAccessToken_thenOK() {
		final Response response = RestAssured.given().auth().preemptive().basic(CLIENT_USERNAME, CLIENT_PASSWORD).with().formParam("grant_type",
				"client_credentials").post(APP_ROOT + "/oauth/token");

		assertEquals(200, response.getStatusCode());
		assertNotNull(response.jsonPath().getString("access_token"));
	}

	@Test
	public void givenReadAndWriteToken_whenAccessUsers_thenOK() {
		String token = obtainAccessToken(CLIENT_USERNAME, CLIENT_PASSWORD);
		Response readResponse = RestAssured.given().header("Authorization", "Bearer " + token)
				.get(APP_ROOT + "/api/possessions");
		assertEquals(200, readResponse.getStatusCode());

		Response writeResponse = RestAssured.given().header("Authorization", "Bearer " + token).formParameters(buildPossession())
				.post(APP_ROOT + "/api/possessions");
		assertEquals(201, writeResponse.getStatusCode());
	}

	@Test
	public void givenReadToken_whenAccessRead_thenOK(){
		String token = obtainAccessToken(CLIENT_READ_USERNAME, CLIENT_PASSWORD);
		Response readResponse = RestAssured.given().header("Authorization", "Bearer " + token)
				.get(APP_ROOT + "/api/possessions");
		assertEquals(200, readResponse.getStatusCode());
	}

	@Test
	public void givenReadToken_whenAccessWrite_thenFAIL(){
		String token = obtainAccessToken(CLIENT_READ_USERNAME, CLIENT_PASSWORD);
		Response writeResponse = RestAssured.given().header("Authorization", "Bearer " + token).formParameters(buildPossession())
				.post(APP_ROOT + "/api/possessions");
		assertEquals(403, writeResponse.getStatusCode());
	}

	@Test
	public void givenWriteToken_whenAccessRead_thenFAIL(){
		String token = obtainAccessToken(CLIENT_WRITE_USERNAME, CLIENT_PASSWORD);
		Response readResponse = RestAssured.given().header("Authorization", "Bearer " + token)
				.get(APP_ROOT + "/api/possessions");
		assertEquals(403, readResponse.getStatusCode());
	}

	@Test
	public void givenWriteToken_whenAccessWrite_thenOK(){
		String token = obtainAccessToken(CLIENT_WRITE_USERNAME, CLIENT_PASSWORD);
		Response writeResponse = RestAssured.given().header("Authorization", "Bearer " + token).formParameters(buildPossession())
				.post(APP_ROOT + "/api/possessions");
		assertEquals(201, writeResponse.getStatusCode());
	}

	// Utility class to obtain token

	private String obtainAccessToken(String clientId, String secret) {
		final Response response = RestAssured.given().auth().preemptive().basic(clientId, secret).with().formParam("grant_type", "client_credentials")
				.post(APP_ROOT + "/oauth/token");
		return response.jsonPath().getString("access_token");
	}

	private Map<String, String> buildPossession() {
		final Map<String, String> params = new HashMap<>();
		params.put("name", randomAlphabetic(4));
		return params;
	}

}
