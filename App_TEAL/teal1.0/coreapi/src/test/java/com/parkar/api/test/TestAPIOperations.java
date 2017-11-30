/*package com.parkar.api.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.api.rest.operations.APIOperations;
import com.parkar.mock.services.MockService;


public class TestAPIOperations extends MockService {
	
	static APIOperations apiOperation;
	@BeforeClass
	public static  void setUp()
	{
		//startMockedServices();
		apiOperation = new APIOperations();
	}
	
	@Test
	public void testSetBaseURL()
	{
		apiOperation.setBaseURL("http://localhost:9999", "ShiftMockService");
		assertEquals(RestAssured.baseURI,"http://localhost:9999");
		assertEquals(RestAssured.basePath,"ShiftMockService");
	}
	
	@Test 
	public void testCreateAuthToken()
	{
		Response res =apiOperation.setAuthToken("test").when().log().all().post("http://localhost:9999/ShiftMockService/createAuthToken");
		assertEquals("test",res.getHeader("Cookie"));
	}
	
	
	@Test 
	public void setFormParameters() throws ParkarCoreAPIException
	{
		String formParameters="{\"employee\":{\"id\":178,\"qualifier\":\"396\"}}";
		Response res =RestAssured.given().spec(apiOperation.setFormParameters(formParameters)).when().post("http://localhost:9999/ShiftMockService/createShiftWithBody");
		assertEquals(res.statusCode(),200);
	}
	

	@Test
	public void testGetWithQueryParameters() throws ParkarCoreAPIException
	{
		HashMap<String,String> queryParameters =new HashMap<String, String>();
		queryParameters.put("id", "3");
		queryParameters.put("tenantId", "combined");
		Response res =apiOperation.getWithQueryparameters("test", queryParameters, "http://localhost:9999/ShiftMockService/getShiftWithQueryParameter");
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.jsonPath().getInt("id"),9514);
		assertEquals(res.jsonPath().getBoolean("posted"),true);
		assertEquals(res.jsonPath().getInt("segments[0].id"),26565);
	}
	
	@Test
	public void testGetWithPathParameters() throws ParkarCoreAPIException
	{
		Response res =apiOperation.getWithPathParameters("test","http://localhost:9999/ShiftMockService/getShiftWithPathParameter/9");
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.jsonPath().getInt("id"),9514);
		assertEquals(res.jsonPath().getBoolean("posted"),true);
		assertEquals(res.jsonPath().getString("commentNotes[0].notes[0].text"),"People can WFH");
		
		//res =apiOperation.getWithPathParameters("test","http://localhost:9999/ShiftMockService/getShiftWithPathParameter/11");
		//assertEquals(res.getStatusCode(), 404);
	}
	

	@Test
	public void testGetWithNoParameters() throws ParkarCoreAPIException
	{  
		Response res =apiOperation.getWithNoParameters("test","http://localhost:9999/ShiftMockService/getShiftWithNoQueryParam");
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.jsonPath().getInt("id"),9514);
		assertEquals(res.jsonPath().getBoolean("posted"),true);
		assertEquals(res.jsonPath().getString("commentNotes[0].notes[0].text"),"People can WFH");
	}
	

	// test is failing with 404, commenting for now
	@Test
	public void testPostWithQueryParameters() throws ParkarCoreAPIException
	{
		HashMap<String,String> queryParameters =new HashMap<String, String>();
		queryParameters.put("tenantId", "combined");
		Response res =apiOperation.postWithQueryParameters("test", queryParameters, "http://localhost:9999/ShiftMockService/createShiftWithQueryParam");
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.jsonPath().getInt("id"),9514);
		assertEquals(res.jsonPath().getBoolean("posted"),true);
		assertEquals(res.jsonPath().getString("commentNotes[0].notes[0].text"),"People can WFH");
	}
	
	
	@Test
	public void testSetPostWithHeaderParameters() throws ParkarCoreAPIException
	{
		
		HashMap<String,String> headerParameters =new HashMap<String, String>();
		headerParameters.put("content-type", "application/json");
		Response res =apiOperation.postWithHeaderParameters(headerParameters, "http://localhost:9999/ShiftMockService/createShiftWithHeaderParam");
		
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.jsonPath().getInt("id"),9514);
		assertEquals(res.jsonPath().getBoolean("posted"),true);
		assertEquals(res.jsonPath().getString("commentNotes[0].notes[0].text"),"People can WFH");
		
	}
	
		
	
	
	@Test
	public void testPostWithFormParameters() throws ParkarCoreAPIException
	{
		String formParametersJson ="{\"employee\":{\"id\":178,\"qualifier\":\"396\"}}";
		Response res =apiOperation.postWithFormParameters("test", formParametersJson,"http://localhost:9999/ShiftMockService/createShiftWithBody");
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.jsonPath().getInt("id"),9514);
		assertEquals(res.jsonPath().getBoolean("posted"),true);
		assertEquals(res.jsonPath().getString("commentNotes[0].notes[0].text"),"People can WFH");
	}
	
	@Test
	public void testPostWithFormParametersWithFourParameters() throws ParkarCoreAPIException
	{
		String formParametersJson ="{\"employee\":{\"id\":178,\"qualifier\":\"396\"}}";
		String contentType="application/json";
		Response res =apiOperation.postWithFormParameters("test", formParametersJson,"http://localhost:9999/ShiftMockService/createShiftWithBody",contentType);
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.jsonPath().getInt("id"),9514);
		assertEquals(res.jsonPath().getBoolean("posted"),true);
		assertEquals(res.jsonPath().getInt("segments[0].id"),26565);
	}
	
	
	@Test
	public void testpostWithPathParameters() throws ParkarCoreAPIException
	{
		//Response res =apiOperation.postWithPathParameters("test","http://localhost:9999/ShiftMockService/createShiftWithPathParameter/A1");
		//assertEquals(res.getStatusCode(), 404);
		
	    Response res =apiOperation.postWithPathParameters("test","http://localhost:9999/ShiftMockService/createShiftWithPathParameter/9");
		assertEquals(res.getStatusCode(), 200);
		
		assertEquals(res.jsonPath().getInt("id"),9514);
		assertEquals(res.jsonPath().getBoolean("posted"),true);
		assertEquals(res.jsonPath().getInt("segments[0].id"),26565);
	}
	

	
	@Test
	public void testPutWithFormParameters() throws ParkarCoreAPIException
	{
		String formParametersJson ="{\"employee\":{\"id\":178,\"qualifier\":\"396\"}}";
		Response res =apiOperation.putWithFormParameters("test",formParametersJson,"http://localhost:9999/ShiftMockService/updateShiftWithBody");
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.jsonPath().getInt("id"),9514);
		assertEquals(res.jsonPath().getBoolean("posted"),true);
		assertEquals(res.jsonPath().getString("commentNotes[0].notes[0].text"),"People can WFH");
	}
	
	
	@Test
	public void testPutWithPathParameters() throws ParkarCoreAPIException
	{
		//Response res =apiOperation.putWithPathParameters("test","http://localhost:9999/ShiftMockService/updateShiftWithPathParam/A1");
		//assertEquals(res.getStatusCode(), 404);
		Response res =apiOperation.putWithPathParameters("test","http://localhost:9999/ShiftMockService/updateShiftWithPathParam/9");
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.jsonPath().getInt("id"),9514);
		assertEquals(res.jsonPath().getBoolean("posted"),true);
		assertEquals(res.jsonPath().getString("commentNotes[0].notes[0].text"),"People can WFH");
	}
	
	
	@Test
	public void tesPutWithQueryparameters() throws ParkarCoreAPIException
	{
		Map<String, String> queryParameters =new HashMap<String,String>();
		queryParameters.put("tenantId", "combined");
		Response res =apiOperation.putWithQueryparameters("test",queryParameters,"http://localhost:9999/ShiftMockService/updateShiftWithQueryParam");
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.jsonPath().getInt("id"),9514);
		assertEquals(res.jsonPath().getBoolean("posted"),true);
		assertEquals(res.jsonPath().getString("commentNotes[0].notes[0].text"),"People can WFH");
	}
	
	
	@Test
	public void testDeleteWithPathParameters() throws ParkarCoreAPIException
	{
		Response res =apiOperation.deleteWithPathParameters("test","http://localhost:9999/ShiftMockService/deleteShiftWithPathParam/7");
		assertEquals(res.getStatusCode(), 204);
	}
	
	@Test
	public void testDeleteWithQueryParameters() throws ParkarCoreAPIException
	{
		Map<String, String> queryParameters = new HashMap<String, String>();
		queryParameters.put("tenantId", "combined");
		queryParameters.put("id", "7");
		Response res =apiOperation.deleteWithQueryParameters("test",queryParameters,"http://localhost:9999/ShiftMockService/deleteShiftWithQueryParam");
		assertEquals(res.getStatusCode(), 204);
	}
	
}*/