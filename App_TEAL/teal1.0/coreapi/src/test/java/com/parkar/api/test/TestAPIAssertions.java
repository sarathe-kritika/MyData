/*package com.parkar.api.test;

import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.restassured.response.Response;
import com.parkar.api.rest.assertions.APIAssertions;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.api.rest.operations.APIResponse;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.mock.services.MockService;
import com.parkar.report.Reporter;

public class TestAPIAssertions extends MockService{
	
	static Reporter reporter ;
	static APIAssertions apiAssertions;
	private static HashMap<String, String> parameterMap;
	private static APIResponse apiResponse;
	static String url="" ;
	static Response res = null;
	SoftAssert softassert= null;
	
	@BeforeClass
	public  static void executeBeforeClass() throws ParkarCoreCommonException {
		
		apiAssertions = new APIAssertions();
		parameterMap = new HashMap<String,String>();
		parameterMap.put("deepReporting","True");
		reporter = Reporter.getInstance();
		reporter.initializeReport(parameterMap);
		reporter.startTest("GenericJsonAssertions", "GenericJsonAssertions");
		
		url = "http://localhost:9999/ShiftMockService/getShiftWithQueryParameter?id=1&tenantId=combined";
		res = when().get(url).then().extract().response();
		apiResponse = new APIResponse(res);
		
		
	}
		
	@Test
	public void test1_VerifyTrue() throws ParkarCoreAPIException
	{
		boolean actual ;
		actual = apiResponse.isNodePresent("generated");
		assertEquals(true,apiAssertions.verifyTrue(actual));
		
		actual = apiResponse.isNodePresent("employee.id");
		assertEquals(true,apiAssertions.verifyTrue(actual));
		
		actual = apiResponse.isNodePresent("commentNotes[0].notes[0].text");
		assertEquals(true,apiAssertions.verifyTrue(actual));

		actual = apiResponse.isNodePresent("commentNotes[0].notes[1].text");
		assertEquals(false,apiAssertions.verifyTrue(actual));
	}
	
	@Test
	public void test2_VerifyFalse() throws ParkarCoreAPIException{
		boolean actual ;
		actual = apiResponse.isNodePresent("commentNotes[0].notes[1].text");
		assertEquals(true, apiAssertions.verifyFalse(actual));
		
		actual = apiResponse.isNodePresent("employeeId");
		assertEquals(true, apiAssertions.verifyFalse(actual));
		
		actual = apiResponse.isNodePresent("commentNotes[0].notes[0].text");
		assertEquals(false,apiAssertions.verifyFalse(actual));
		
		
	}
	
	@Test
	public void test3_VerifyEquals() throws ParkarCoreAPIException, IOException
	{
		
		assertEquals(true, apiAssertions.verifyEquals(apiResponse.getNodeValue("employee.id"),178));		
		assertEquals(false, apiAssertions.verifyEquals(apiResponse.getNodeValue("generated"),true));		
		assertEquals(true, apiAssertions.verifyEquals(apiResponse.getNodeValue("segments[0].workruleRef.qualifier"),"support"));	
		
		Map<String,Object> expected = new HashMap();
		expected.put("id", 51);
		expected.put("qualifier", "Organization/Division 1/Facility 1/Store 2/Department 21/RN");
		
		assertEquals(true, apiAssertions.verifyEquals(apiResponse.getNodeValue("segments[0].primaryOrgJobRef"), expected));
	}
	
	@Test
	public void test4_VerifyNotEquals() throws ParkarCoreAPIException
	{
		assertEquals(true, apiAssertions.verifyNotEquals(apiResponse.getNodeValue("employee.id"),177));		
		assertEquals(true, apiAssertions.verifyNotEquals(apiResponse.getNodeValue("generated"),true));		
		assertEquals(false, apiAssertions.verifyNotEquals(apiResponse.getNodeValue("segments[0].workruleRef.qualifier"),"Support"));	
		
		Map<String,Object> expected = new HashMap();
		expected.put("id", 52);
		expected.put("qualifier", "Organization/Division 1/Facility 1/Store 2/Department 21/RN");
		
		assertEquals(true, apiAssertions.verifyNotEquals(apiResponse.getNodeValue("segments[0].primaryOrgJobRef"), expected));
	}
	
	@Test
	public void test5_VerifyStatusCode() throws ParkarCoreAPIException
	{
		assertEquals(true,apiAssertions.verifyStatusCode(apiResponse.getStatusCode(), 200));
		assertEquals(false,apiAssertions.verifyStatusCode(apiResponse.getStatusCode(), 204));
	}

	
}*/