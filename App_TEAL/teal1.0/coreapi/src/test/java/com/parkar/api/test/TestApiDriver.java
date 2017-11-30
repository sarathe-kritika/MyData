/*package com.parkar.api.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.parkar.api.rest.base.BaseAPITest;
import com.parkar.api.rest.driver.APIDriver;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.api.rest.json.Request;
import com.parkar.api.rest.operations.APIResponse;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.mock.services.MockService;
import com.parkar.report.Reporter;



public class TestApiDriver extends MockService{

	
	static APIDriver apiDriver;
	static Reporter reporter ;
	private static HashMap<String, String> parameterMap;

	@BeforeClass
	public static void setUp() throws ParkarCoreCommonException
	{
		apiDriver = new APIDriver();
		parameterMap = new HashMap<String,String>();
		parameterMap.put("deepReporting","True");
		reporter = Reporter.getInstance();
		reporter.initializeReport(parameterMap);
		reporter.startTest("loginTest", "loginTest");
		startMockedServices();
		BaseAPITest.LOGIN_ENDPOINT="http://localhost:9999/MockService/loginService";
	}

	@Test()
	public void testDoLogin()
	{
		
		apiDriver.doLogin("Tcmgr", "kronites");
		assertNotNull(apiDriver.GLOBAL_COOKIE);
		
	}
	
	 * Test Case for the overloaded execute method with one parameter of APIDriver.
	 * Test Method : APIDriver.execute(String URI)
	 
	@Test()
	public void testAPIExecute() throws ParkarCoreAPIException
	{
		
		apiDriver.GLOBAL_COOKIE="test";
		String URI ="http://localhost:9999/ShiftMockService/getShiftWithQueryParameter?tenantId=combined&id=3";
		APIResponse res=get(URI);
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.getNodeValue("id"),9514);
		assertEquals(res.getNodeValue("posted"),true);
		assertEquals(res.getNodeValue("commentNotes[0].notes[0].text"),"People can WFH");
		
	}
	
	
	 * Test Case for the overloaded execute method of APIDriver with request URI and the query map as parameters.
	 * Test Method : APIDriver.execute(String URI,HashMap<String, Object> map)
	
	@Test
	public void testAPIExecuteWithPathParameter() throws ParkarCoreAPIException
	{
		
		
		
		apiDriver.GLOBAL_COOKIE="test";
		String URI ="http://localhost:9999/ShiftMockService/getShiftWithQueryParameter";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tenantId", "combined");
		map.put("id", 9);
		get(URI,map);
		URI ="http://localhost:9999/ShiftMockService/createShiftWithPathParameter/{sid}";
		map = new HashMap<String, Object>();
		map.put("sid", "3");
		
		APIResponse res=post(URI,map);
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.getNodeValue("id"),9514);
		assertEquals(res.getNodeValue("posted"),true);
		assertEquals(res.getNodeValue("commentNotes[0].notes[0].text"),"People can WFH");
		
		URI ="http://localhost:9999/ShiftMockService/updateShiftWithPathParam/{updateShift}";
		map = new HashMap<String, Object>();
		map.put("updateShift", "9");
		res=put(URI,map);
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.getNodeValue("id"),9514);
		assertEquals(res.getNodeValue("posted"),true);
		assertEquals(res.getNodeValue("commentNotes[0].notes[0].text"),"People can WFH");
		URI ="http://localhost:9999/ShiftMockService/deleteShiftWithPathParam/{deleteShift}";
		map = new HashMap<String, Object>();
		map.put("deleteShift", "9");
		res =delete(URI,map);
		assertEquals(res.getStatusCode(), 204);
		
		
	}
	
	
	 * Test Case for the overloaded execute method of APIDriver with request URI and JSON string as parameters
	 * Test Method : APIDriver.execute(String URI,String jsonRequest)
	
	@Test
	public void testApiDriverExecuteWithJsonAsBody() throws ParkarCoreAPIException
	{
		
		
		apiDriver.GLOBAL_COOKIE="test";
	
		Request formParametersJson = new Request ("{\"employee\":{\"id\":178,\"qualifier\":\"396\"}}");
		
		String URI ="http://localhost:9999/ShiftMockService/createShiftWithQueryParam?tenantId=combined";
		
		APIResponse res=post(URI,formParametersJson);
		
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.getNodeValue("id"),9514);
		assertEquals(res.getNodeValue("posted"),true);
		assertEquals(res.getNodeValue("commentNotes[0].notes[0].text"),"People can WFH");
		
		URI ="http://localhost:9999/ShiftMockService/updateShiftWithQueryParam?tenantId=combined";
		 res=put(URI,formParametersJson);
		 
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.getNodeValue("id"),9514);
		assertEquals(res.getNodeValue("posted"),true);
		assertEquals(res.getNodeValue("commentNotes[0].notes[0].text"),"People can WFH");
		
		
		
	}
	
	 * Test Case for the overload method with three parameters of APIDriver.
	 * Test Method : APIDriver.execute(String URI,String jsonRequest,Map<String, Object> map)
	 
	@Test
	public void testApiDriverExecuteWithQueryParameterAndJsonBody() throws ParkarCoreAPIException 
	{
		
		apiDriver.GLOBAL_COOKIE="test";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tenantId", "combined");
		String formParametersJson ="{\"employee\":{\"id\":178,\"qualifier\":\"396\"}}";
		Request req = new Request (formParametersJson);
	
		
		
		String URI ="http://localhost:9999/ShiftMockService/createShiftWithBody";
		
		APIResponse res=post(URI,req,map);
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.getNodeValue("id"),9514);
		assertEquals(res.getNodeValue("posted"),true);
		assertEquals(res.getNodeValue("commentNotes[0].notes[0].text"),"People can WFH");
		
		
		URI ="http://localhost:9999/ShiftMockService/updateShiftWithQueryParam";
		res =put(URI,req,map);
		assertEquals(res.getStatusCode(), 200);
		assertEquals(res.getNodeValue("id"),9514);
		assertEquals(res.getNodeValue("posted"),true);
		assertEquals(res.getNodeValue("commentNotes[0].notes[0].text"),"People can WFH");
		
		
	}
	
	
	private APIResponse get(String URI) throws ParkarCoreAPIException
	{
		return apiDriver.execute(URI);
	}
	
	private APIResponse post(String URI,HashMap<String, Object> map) throws ParkarCoreAPIException
	{
		return apiDriver.execute(URI,map);
	}
	
	private APIResponse get(String URI,HashMap<String, Object> map) throws ParkarCoreAPIException
	{
		
		return apiDriver.execute(URI, map);
		
	}
	
	private APIResponse put(String URI,HashMap<String, Object> map) throws ParkarCoreAPIException
	{
		return apiDriver.execute(URI,map);
	}
	
	private APIResponse delete(String URI,HashMap<String, Object> map) throws ParkarCoreAPIException
	{
		
		return apiDriver.execute(URI, map);
		
	}
	
	private APIResponse post(String URI,Request jsonRequest) throws ParkarCoreAPIException
	{
		
		return apiDriver.execute(URI, jsonRequest);
		
		
	}
	

	private APIResponse put(String URI,Request jsonRequest) throws ParkarCoreAPIException
	{
		
		return apiDriver.execute(URI, jsonRequest);
		
		
	}
	
	private APIResponse post(String URI,Request jsonRequest,HashMap<String, Object> map) throws ParkarCoreAPIException
	{
		
		return apiDriver.execute(URI, jsonRequest, map);
		
	}
	
	private APIResponse put(String URI,Request jsonRequest,HashMap<String, Object> map) throws ParkarCoreAPIException
	{
		
		return apiDriver.execute(URI, jsonRequest, map);
		
	}

		
	
	
}

*/