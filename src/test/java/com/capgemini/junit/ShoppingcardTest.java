package com.capgemini.junit;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShoppingcardTest {
	
	private static String tk = "";
	private static String shoppingCardId; // Must be set for validation in 03 test
	private static JSONObject shoppingCard; // must be set for valadation 02 test
	@BeforeClass
	public static void init() {

		RestAssured.baseURI = "https://api.tillhub.com/api/v0";
		// RestAssured.port = 80; not needed
		
		// Setze variablen shopping card
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		
		//Set shoppingCard 
		//TODO
				 shoppingCard = new JSONObject().
						put("Date", dtf.format(localDate))
						.put("client_id", "123")
						.put("branch_number", "123")
						.put("register_number", "123")
						.put("cashier_staff_number", "123")
						.put("currency_iso_code","EUR");
	}
	
	@AfterClass
	public static void tearDownAfterClass()  {
		// TODO clean up logout
	}

	@Test
	public  void test01Login() {

		/**
		 * Login
		 * 
		 * 
		 **/
		// TODO klären scheitert und token auslesen bei headern dann setzen
		Map<String, String> parameters = new HashMap<>();
		parameters.put("email", "bsh-cart@tillhub.de");
		parameters.put("password", "bsh1234");

		// need to read token
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.log().all().queryParams("", parameters).when().get("/users/login");
		JsonPath jsonPathEvaluator = response.jsonPath();
		System.out.println("Got token " + jsonPathEvaluator.getString("token"));
		tk = jsonPathEvaluator.getString("token");

		// TODO Remove then 
		given().log().all().queryParams("", parameters).when().get("/users/login").then().contentType(ContentType.JSON)
				.statusCode(200).body("msg", equalTo("authentication was good"));

	}

	/*
	 * Transfer shoppingCard and validate the transfer
	 */
	@Test
	public void test02TransferShoppingCard() {

		/**
		 * ShoppingCard setzen und abshicken
		 * 
		 * 
		 **/	
		given().header("Authorization", tk).contentType(ContentType.JSON).
		body(shoppingCard.toString()).when()
				.post("/cards")
				.then().contentType(ContentType.JSON).
				statusCode(200)
				.body("x.y", equalTo("z")); // Validierung überlegen

	}
	

	
	/*
	 * Read transfered shopping card and validate
	 */
	@Test
	//TODO
	public void test03CreatedShoppingCard() {
		given().header("Authorization", tk)
		.contentType(ContentType.JSON)
		.log().all()
		.pathParam("clientSavedCartID", "") //TODO
		.pathParam("id", "") //TODO
		.when()
		.get("/cards/{clientSavedCartID}/{id}") 
		.then()
		.contentType(ContentType.JSON).
		statusCode(200)
		.body("x.y", equalTo("z")); //TODO Validierung abhängig von created shoppingCard 
		
	}
	
	
	/**
	 * Transactionen / shoppingcards save to validate after payment
	 */
	//TODO
	private void putShoppingCardForLateValidation() {
		// hier speichern die cards um später payment zu validieren
	}

}
