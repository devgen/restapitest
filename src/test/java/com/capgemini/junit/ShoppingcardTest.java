package com.capgemini.junit;

import org.junit.BeforeClass;
import org.junit.Test;

import  io.restassured.RestAssured;
import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;






public class ShoppingcardTest {

	@BeforeClass
	public static void init() {

		RestAssured.baseURI = "https://api.tillhub.com";
		RestAssured.port = 80;
	}

	@Test
	public void testTransferShoppuingCard() {
		
		
		
		get("/junitrestweb/webapi/employee/12")
		.then()
		.body("id", equalTo(12))
		.body("firstName", equalTo("Vinod"))
		.body("lastName", equalTo("Kashyap"))
		.body("designation", equalTo("CEO"));

	}

	private String login() {
		
		
		RestAssured.given().queryParam("", "").when().get("api/v0/users/login/").then().
		extract().response().as(TokenResponse.class);

	}

}
