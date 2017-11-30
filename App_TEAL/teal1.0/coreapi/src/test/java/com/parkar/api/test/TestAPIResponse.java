/*package com.parkar.api.test;

import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
//import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.response.Response;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.api.rest.operations.APIResponse;
import com.parkar.mock.services.MockService;

public class TestAPIResponse extends MockService{

	static String url;
	static Response res;
	static APIResponse apiResponse;
	
	@BeforeClass
	public  static void executeBeforeClass() {
		url = "http://localhost:9999/ShiftMockService/getShiftWithQueryParameter?id=1&tenantId=combined";
		res = when().get(url).then().extract().response();
		apiResponse = new APIResponse(res);
	}
 
	
	@Test
	public void test1_StatusCode() throws ParkarCoreAPIException
	{
		assertEquals(200, apiResponse.getStatusCode());
	}
	
	@Test
	public void test2_getNodeValue() throws ParkarCoreAPIException, IOException {
		assertEquals(5, apiResponse.getNodeValue("commentNotes[0].comment.id"));
		Map<String, Object> commentmap = new HashMap();
		commentmap.put("id", 5);
		commentmap.put("name", "Union business");
		commentmap.put("active", true);
		assertEquals((Object)commentmap, apiResponse.getNodeValue("commentNotes[0].comment"));
	}
	
	@Test
	public void test3_getNodeValues() throws ParkarCoreAPIException
	{
		HashMap<String,Object> obj = new HashMap();
		obj.put("text", "People can WFH");
		obj.put("timestamp", "2016-07-01T09:04:59");
		obj.put("dataSourceDisplayName", "TKCSOWNER");
		obj.put("dataSourceId", 1);
		obj.put("commentNoteId", 715);
		
		List<Object> actual = new ArrayList();
		actual.add(obj);
		assertEquals(actual,apiResponse.getNodeValues("commentNotes[0].notes"));
	}
	
	@Test
	public void test4_isNodePresent() throws ParkarCoreAPIException{
		assertEquals(true, apiResponse.isNodePresent("generated"));
		assertEquals(true, apiResponse.isNodePresent("employee.id"));
		assertEquals(false, apiResponse.isNodePresent("employeeId"));
		assertEquals(true, apiResponse.isNodePresent("commentNotes[0].notes[0].text"));
		assertEquals(true, apiResponse.isNodePresent("commentNotes.notes.text"));
		assertEquals(false, apiResponse.isNodePresent("commentNotes[0].notes[1].text"));
	}
	
	@Test
	public void test5_getJsonPathRegEx() throws ParkarCoreAPIException{
		assertEquals(true, apiResponse.getJsonPathRegEx("commentNotes.notes", "[a-z]"));
		assertEquals(true, apiResponse.getJsonPathRegEx("commentNotes.notes", "[a-z]"));
		assertEquals(true, apiResponse.getJsonPathRegEx("segments[0].id", "[0-9]"));
		assertEquals(false, apiResponse.getJsonPathRegEx("segments[0].id", "[a-z]"));
	}
	
	@Test
	public void test6_getJsonPathCount() throws ParkarCoreAPIException{
		assertEquals(1, apiResponse.getJsonPathCount("commentNotes"));
		assertEquals(1, apiResponse.getJsonPathCount("segments"));
		assertEquals(1, apiResponse.getJsonPathCount("commentNotes[0].notes"));
	}
	
	@Test
	public void test7_isInListOfHTTPCodes() throws ParkarCoreAPIException
	{
		ArrayList<Integer> arrCodes= new ArrayList<>();
		arrCodes.add(201);
		arrCodes.add(200);
		assertEquals(true, apiResponse.isInListOfHTTPCodes(arrCodes));
		arrCodes.clear();
		arrCodes.add(400);
		assertEquals(false, apiResponse.isInListOfHTTPCodes(arrCodes));	
	}
	
	
	@Test
	public void test8_contains() throws ParkarCoreAPIException
	{
		assertEquals(true,apiResponse.contains("People can WFH"));
		assertEquals(true,apiResponse.contains("715"));
		assertEquals(false, apiResponse.contains("REGULAR_SEGMENT_changed"));
	}
	

	
	@Test
	public void test9_assertJsonSort() throws ParkarCoreAPIException
	{
		assertEquals(true,apiResponse.isJsonSort("commentNotes.comment", "id", true));
		assertEquals(true,apiResponse.isJsonSort("commentNotes.comment", "id",false));
		assertEquals(true,apiResponse.isJsonSort("segments", "id",true));
	}
	
	@Test
	public void test10_isJSONCompare() throws IOException, ParkarCoreAPIException {
		
		String expectedJson = "";
		File file;
		
		//Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		file = new File(classLoader.getResource("testCompare1.json").getFile());
		
		expectedJson = FileUtils.readFileToString(file);
		assertEquals(true, apiResponse.isJSONCompare(expectedJson));

		file = new File(classLoader.getResource("testCompare2.json").getFile());
		assertEquals(true, apiResponse.isJSONCompare(file));
		
		file = new File(classLoader.getResource("testCompare3.json").getFile());
		assertEquals(true, apiResponse.isJSONCompare(file));
		
		file = new File(classLoader.getResource("testCompare4.json").getFile());
		assertEquals(false, apiResponse.isJSONCompare(file));
	}
	
	@Test
	public void test11_isJSONContains() throws IOException, ParkarCoreAPIException {
		
		String expectedStr;
		File file;
		
		ClassLoader classLoader = getClass().getClassLoader();
		file = new File(classLoader.getResource("testContain1.json").getFile());
		
		
		expectedStr = FileUtils.readFileToString(file,"UTF-8");
		assertEquals(true, apiResponse.isJSONContains(expectedStr));

		// calling overloaded assertion with file
		file = new File(classLoader.getResource("testContain2.json").getFile());
		assertEquals(true, apiResponse.isJSONContains(file));
		
		file = new File(classLoader.getResource("testContain3.json").getFile());
		assertEquals(true, apiResponse.isJSONContains(file));
		
		file = new File(classLoader.getResource("testContain4.json").getFile());
		assertEquals(false, apiResponse.isJSONContains(file));
		

	}
	
}
*/