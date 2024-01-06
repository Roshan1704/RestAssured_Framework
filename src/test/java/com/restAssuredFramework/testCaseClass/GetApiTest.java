package com.restAssuredFramework.testCaseClass;

import org.testng.annotations.Test;

import com.restAssuredFramework.CommonClass.BaseClass;
import com.restAssuredFramework.CommonClass.LaunchApi;
import io.restassured.response.Response;

public class GetApiTest extends BaseClass
{
    @Test
	public void getUser() 
    {   
    	int user_id=2;
    	String path = "/api/users/"+user_id;
    	Response response = LaunchApi.getAPI(path);
    	
    	BaseClass.responseCodeVerify(response.getStatusCode(),200);
    	BaseClass.contentTypeVerify(response.contentType(),"application/json");
    	BaseClass.responseTimeVerify(response.getTime(),1500);
    	
    	Object value=response.jsonPath().get("data.id"); // put the value to verify 
    	BaseClass.responseValueVerify(value,user_id);
    	
    }
    

}
