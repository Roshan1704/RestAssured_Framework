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

public class PatchApiTest extends BaseClass
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
    public void updateUser(String[] parameters) 
    {   
    	int user_id=2;
    	String path = "/api/users/"+user_id;
    	HashMap<String, String> body = BaseClass.SetupResquestBody(arr, parameters);

    	Response response = LaunchApi.patchAPI(body,path);
    	
    	BaseClass.responseCodeVerify(response.getStatusCode(),200);
    	BaseClass.contentTypeVerify(response.contentType(),"application/json");
    	BaseClass.responseTimeVerify(response.getTime(),1500);
    	
    	Object value=response.jsonPath().get("name"); // Get Any value to verify 
    	BaseClass.responseValueVerify(value,parameters[0]);
    	
    }

}
