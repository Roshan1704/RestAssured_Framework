package com.restAssuredFramework.CommonClass;

import static io.restassured.RestAssured.baseURI;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;

public class BaseClass 
{
	protected static String csv_path = "C:\\Users\\awiro\\Downloads\\abc.xlsx";
	protected static HashMap<String, Object> requestBody;
	protected static HashMap<String, Object> subrequestBody;

    @BeforeTest
    public void before() 
    {
//        baseURI = "BASE_URI";
    	 baseURI = "https://reqres.in";
    	 
    	 requestBody= new HashMap<String, Object>();
    	 subrequestBody= new HashMap<String, Object>();
    }
    
    
    public static HashMap<String, String> SetupResquestBody(ArrayList<String> header, String... parameters) 
    {
        // Assuming header contains parameter names in order
        if (header.size() != parameters.length) {
            throw new IllegalArgumentException("Number of parameters does not match the number of headers");
        }
        //Create Map to setup key-Value pair
        HashMap<String, String> requestBody = new HashMap<>();

        // Populate the map with key-value pairs
        for (int i = 0; i < header.size(); i++) 
        {
            requestBody.put(header.get(i), parameters[i]);
        }
        
        return requestBody;
        
//        System.out.println(requestBody);
    }
    
//  
//	Assert.assertTrue(response.contentType().toLowerCase().matches("application/json".toLowerCase() + ".*"),
//          "Content Type mismatch");
    
    
    public static void responseCodeVerify(int actualResponseCode, int expectedResponseCode) 
    {
    	Assert.assertEquals(actualResponseCode, expectedResponseCode, "Response code verification failed");
    }
    
    public static void responseCodeVerifyFail(int actualResponseCode, int expectedResponseCode) 
    {
    	Assert.assertEquals(actualResponseCode, expectedResponseCode, "Response code verification failed");
    }
    
    public static void contentTypeVerify(String ActualContentType,String ExpectedContentType)
    {
    	Assert.assertTrue(ActualContentType.toLowerCase().matches(ExpectedContentType.toLowerCase() + ".*"),
		          "Content Type mismatch");
    }
    
    public static void responseValueVerify(Object actualValue,Object expectedValue)
    {
    	Assert.assertEquals(actualValue, expectedValue, "Value not matched");
    }
    public static void responseTimeVerify(long ActualresponseTime,long ExpectedresponseTime)
    {
    	Assert.assertTrue(ActualresponseTime< ExpectedresponseTime, "Response time is not less than "+ExpectedresponseTime+" milisec");
    }
    
	public static Object setupApiRequestBody(String key,Object value)
	{
		return requestBody.put(key,value);	
		
	}
	
	public static Object setupApiSubRequestBody(String key,Object value)
	{
		return subrequestBody.put(key, value);	
		
	}
    


}
