package com.restAssuredFramework.CommonClass;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class LaunchApi 
{
	public static Response PostAPI(HashMap<String, String> body,String path) 
    {
		Response response = given()
				.contentType(ContentType.JSON)
	            .body(body)
	        .when()
	            .post(path);
	            
	        return response;
    }
	
	public static Response getAPI(String path) 
	{
        Response response = given()
            .when()
            .get(path);
        return response;
    }
	public static Response deleteAPI(String path) 
	{
        Response response = given()
            .when()
            .delete(path);
        return response;
    }
	
	public static Response putAPI(HashMap<String, String> body,String path) 
    {
		Response response = given()
				.contentType(ContentType.JSON)
	            .body(body)
	        .when()
	            .put(path);
	            
	        return response;
    }
	
	public static Response patchAPI(HashMap<String, String> body,String path) 
    {
		Response response = given()
				.contentType(ContentType.JSON)
	            .body(body)
	        .when()
	            .put(path);
	            
	        return response;
    }

}
