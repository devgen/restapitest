package com.capgemini.junit;

import org.junit.BeforeClass;
import org.junit.Test;

import  io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

import javax.ws.rs.POST;


public class ShoppingcardTest {

	@BeforeClass
	public static void init() {

		RestAssured.baseURI = "https://api.tillhub.com";
		RestAssured.port = 80;
	}

	@Test
	public void testTransferShoppuingCard() {
		
		
		
//		given().body().
//		post("/junitrestweb/webapi/employee/12");
		
	

	}



}
