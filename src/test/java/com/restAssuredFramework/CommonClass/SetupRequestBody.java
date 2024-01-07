package com.restAssuredFramework.CommonClass;

import java.util.HashMap;

public class SetupRequestBody 
{
	static HashMap<String, Object> requestBody= new HashMap<String, Object>();
	static HashMap<String, Object> subrequestBody= new HashMap<String, Object>();
	public static Object setupApiRequestBody(String key,Object value)
	{
		return requestBody.put(key,value);	
		
	}
	
	public static Object setupApiSubRequestBody(String key,Object value)
	{
		return subrequestBody.put(key, value);	
		
	}
	
	public static void getupApiRequestBody()
	{
		
		Object first = setupApiRequestBody("Name","Roshan");
		Object second = setupApiRequestBody("roll","1");
		Object third = setupApiSubRequestBody("subject1","abc");
		Object f = setupApiSubRequestBody("subject2","eng");
		Object fv = setupApiSubRequestBody("subject3","xyz");
		Object sub = setupApiRequestBody("subject",subrequestBody);
		
		System.out.println(requestBody);
		
	}
	public static void main(String[] args) 
	{
		getupApiRequestBody();
		
	}

}
