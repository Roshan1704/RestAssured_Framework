package com.restAssuredFramework.testCaseClass;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.restAssuredFramework.CommonClass.BaseClass;
import com.restAssuredFramework.CommonClass.LaunchApi;
import com.restAssuredFramework.testData.DataFromExcel;

import io.restassured.response.Response;

public class DeleteApiTest extends BaseClass
{
	@Test
	public void deleteUser() 
    {   
    	int user_id=2;
    	String path = "/api/users/"+user_id;
    	Response response = LaunchApi.deleteAPI(path);
    	
    	BaseClass.responseCodeVerify(response.getStatusCode(),204);
    	BaseClass.responseTimeVerify(response.getTime(),1500);
    	
    	// As user delete, Get request should respond 404, As now user Not exist.
    	
    	BaseClass.responseCodeVerifyFail(LaunchApi.getAPI(path).getStatusCode(),404);
    	
    }

}
