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

public class PostApiTest extends BaseClass
{
	static String sheetName;
	static ArrayList<String> arr;
	@BeforeTest
	public void setup()
	{
		sheetName=""; //pass the sheetname where Test Data Available.
		arr = DataFromExcel.getDataHeader(csv_path,sheetName);
		
	}
	
	
	@DataProvider
	public static Object[][] getDataFromExcel() 
	{
	    return DataFromExcel.getData(csv_path,sheetName); 
	}
	
    @Test(dataProvider = "getDataFromExcel")
    public void registerUser(String[] parameters) 
    {   
    	String path = "/api/users";
    	HashMap<String, String> body = BaseClass.SetupResquestBody(arr, parameters);

    	Response response = LaunchApi.PostAPI(body,path);
    	
    	BaseClass.responseCodeVerify(response.getStatusCode(),201);
    	BaseClass.contentTypeVerify(response.contentType(),"application/json");
    	BaseClass.responseTimeVerify(response.getTime(),1500);
    	
    	Object value=response.jsonPath().get("id"); // put the value to verify 
    	BaseClass.responseValueVerify(value,150);
    	
    }
	
	
	// ******************* In Case of Complex API Body, Use this 1*********************
//	@Test(dataProvider = "getDataFromExcel")
//    public void registerUser(String[] parameters) 
//    {   
//    	BaseClass.SetupResquestBody(arr, parameters);
//    	
//    	setupApiRequestBody(arr.get(0),parameters[0]);
//		setupApiRequestBody(arr.get(1),parameters[1]);
//		setupApiSubRequestBody(arr.get(2),parameters[2]);
//		setupApiSubRequestBody(arr.get(3),parameters[3]);
//		setupApiRequestBody("subject",subrequestBody);
//		
//		System.out.println(requestBody.toString());
//    }

}

























