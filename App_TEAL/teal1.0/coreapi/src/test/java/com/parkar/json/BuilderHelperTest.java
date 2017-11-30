package com.parkar.json;


import static com.parkar.api.rest.json.JsonBuildHelper.build;
import static com.parkar.api.rest.json.JsonBuildHelper.list;
import static com.parkar.api.rest.json.JsonBuildHelper.map;
import static com.parkar.api.rest.json.JsonBuildHelper.mapInRange;
import static com.parkar.api.rest.json.JsonBuildHelper.objRef;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.api.rest.json.Request;
import com.parkar.dateutils.DateGenerator;

public class BuilderHelperTest {
	private static final String FORMAT = "yyyy-MM-dd";
	private ObjectMapper mapper;
	DateGenerator dg;
	

	@Before
	public void setUp() throws Exception {
		HashMap<String,String> options = new HashMap<String,String>();
		options.put("dateFormat", FORMAT);
		dg = new DateGenerator(options);
		mapper = new ObjectMapper();

	}
	
	@Test
	public void testBuilderHelperTest() throws IOException, ParkarCoreAPIException{
		String expectedPayLoad = "{\"startDateTime\" : \""+dg.getDateTime(2, "T22:30:19")+""+"\",\"commentNotes\" :"
				+ " [{\"comment\" : {\"name\" : \"some comments\",\"active\" : true,\"id\" : 20}},"
				+ "{\"comment\" : {\"name\" : \"another comments\",\"active\" : false,\"id\" : 21}}],"
				+ "\"employee\" : {\"id\" : 1,\"qualifier\" : \"382\"},\"endDateTime\" : \"2016-07-23T22:30:19\","
				+ "\"segments\" : [{\"workruleRef\" : {\"id\" : 2, \"qualifier\" : \"Full time\"},\"type\" : \"REGULAR_SEGMENT\",\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 21/Register\"}},"
				+ "{\"workruleRef\" : {\"qualifier\" : \"Part time\"},\"type\" : \"TRANSFER_SEGMENT\",\"skillCertProfileRefs\" : [{\"qualifier\" : \"CP001\"}],\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 22/RN\"}}]}";
		
		//String schemaLocation = new java.io.File(".").getCanonicalPath() + "\\resources\\unitTest\\schema\\shift.json";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("schema/shift.json").getFile());
		String schemaLocation = file.getAbsolutePath();
		Request req = build(schemaLocation,
				map(
						"startDateTime", dg.getDateTime(2, "T22:30:19"),
						"commentNotes", list( 
										map(
												"comment" , map("name","some comments", "active", true, "id", 20)
										),
										map(
												"comment" , map("name","another comments", "active", false, "id", 21)
										)
								),
						"employee", objRef("382",1),
						"endDateTime", "2016-07-23T22:30:19",
						"segments",
						list(
								map(
										"workruleRef", objRef("Full time",2),
										"type", "REGULAR_SEGMENT",
										"orgJobRef", objRef("Organization/Division 1/Facility 1/Store 2/Department 21/Register")
								), 
								map( 
										"workruleRef", objRef("Part time"),
										"type", "TRANSFER_SEGMENT",
										"skillCertProfileRefs", list(
																	objRef("CP001")
																),
										"orgJobRef",	objRef("Organization/Division 1/Facility 1/Store 2/Department 22/RN")
								)
						)
				)
		);
		String payload = req.getRequest().toString();
		String actualPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(payload));
		String expectedPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(expectedPayLoad));
//		System.out.println(actualPrettyPrint);
//		System.out.println(expectedPrettyPrint);
		assertEquals(expectedPrettyPrint.trim(),actualPrettyPrint.trim());
	}
	
	
	
	@Test
	public void testBuilderHelperForMapTest() throws IOException, ParkarCoreAPIException{
		String expectedPayLoad = "{\"startDateTime\" : \""+dg.getDateTime(2, "T22:30:19")+""+"\",\"commentNotes\" :"
				+ " [{\"comment\" : {\"name\" : \"some comments\",\"active\" : true,\"id\" : 20}},"
				+ "{\"comment\" : {\"name\" : \"another comments\",\"active\" : false,\"id\" : 21}}],"
				+ "\"employee\" : {\"id\" : 1,\"qualifier\" : \"382\"},\"endDateTime\" : \"2016-07-23T22:30:19\","
				+ "\"segments\" : [{\"startDateTime\" : \""+dg.getDateTime(0, "T08:00:00")+"\",\"workruleRef\" : {\"qualifier\" : \"Full time\", \"id\" : 2},\"type\" : \"REGULAR_SEGMENT\",\"endDateTime\" : \""+dg.getDateTime(0, "T08:00:00")+"\",\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 21/Register0\"}},"
				+ "{\"startDateTime\" : \""+dg.getDateTime(1, "T08:00:00")+"\",\"workruleRef\" : {\"qualifier\" : \"Full time\", \"id\" : 2},\"type\" : \"REGULAR_SEGMENT\",\"endDateTime\" : \""+dg.getDateTime(1, "T08:00:00")+"\",\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 21/Register1\"}}]}";
		
		//String schemaLocation = new java.io.File(".").getCanonicalPath() + "\\resources\\unitTest\\schema\\shift.json";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("schema/shift.json").getFile());
		String schemaLocation = file.getAbsolutePath();
		Request req = build(schemaLocation,
				map(
						"startDateTime", dg.getDateTime(2, "T22:30:19"),
						"commentNotes", list( 
										map(
												"comment" , map("name","some comments", "active", true, "id", 20)
										),
										map(
												"comment" , map("name","another comments", "active", false, "id", 21)
										)
								),
						"employee", objRef("382",1),
						"endDateTime", "2016-07-23T22:30:19",
						"segments",
						mapInRange(0,1,
								map(
										"workruleRef", objRef("Full time",2),
										"type", "REGULAR_SEGMENT",
										"startDateTime", "{index}dT08:00:00",
							            "endDateTime", "{index}dT08:00:00",
										"orgJobRef", objRef("Organization/Division 1/Facility 1/Store 2/Department 21/Register{index}")
								)
						)
						
				)
		);
		String payload = req.getRequest().toString();
		String actualPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(payload));
		String expectedPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(expectedPayLoad));
//		System.out.println(actualPrettyPrint);
//		System.out.println(expectedPrettyPrint);
		assertEquals(expectedPrettyPrint.trim(),actualPrettyPrint.trim());
	}
	
	@Test
	public void testWithoutSchemaValidation() throws IOException, ParkarCoreAPIException{
		String expectedPayLoad = "{\"startDateTime\" : \""+dg.getDateTime(2, "T22:30:19")+"\",\"commentNotes\" :"
				+ " [{\"comment\" : {\"name\" : \"some comments\",\"active\" : true,\"id\" : 20}},"
				+ "{\"comment\" : {\"name\" : \"another comments\",\"active\" : false,\"id\" : 21}}],"
				+ "\"employee\" : {\"id\" : 1,\"qualifier\" : \"382\"},\"endDateTime\" : \"2016-07-23T22:30:19\","
				+ "\"segments\" : [{\"workruleRef\" : {\"id\" : 2, \"qualifier\" : \"Full time\"},\"type\" : \"REGULAR_SEGMENT\",\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 21/Register\"}},"
				+ "{\"workruleRef\" : {\"qualifier\" : \"Part time\"},\"type\" : \"TRANSFER_SEGMENT\",\"skillCertProfileRefs\" : [{\"test\" : [{\"id\":1,\"active\":false}]}],\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 22/RN\"}}]}";
		
		Request req = build(
				map(
						"startDateTime", dg.getDateTime(2, "T22:30:19"),
						"commentNotes", list( 
										map(
												"comment" , map(
																"name","some comments", 
																"active", true, 
																"id", 20
															)
										),
										map(
												"comment" , map(
																"name","another comments", 
																"active", false, 
																"id", 21
															)
										)
								),
						"employee", objRef("382",1),
						"endDateTime", "2016-07-23T22:30:19",
						"segments",
						list(
								map(
										"workruleRef", objRef("Full time",2),
										"type", "REGULAR_SEGMENT",
										"orgJobRef", objRef("Organization/Division 1/Facility 1/Store 2/Department 21/Register")
								), 
								map( 
										"workruleRef", objRef("Part time"),
										"type", "TRANSFER_SEGMENT",
										"skillCertProfileRefs", list(
																	map("test" , 
																			list(
																					map(
																						"id", 1,
																						"active", false
																					)
																				)
																	)
																),
										"orgJobRef",	objRef("Organization/Division 1/Facility 1/Store 2/Department 22/RN")
								)
						)
				)
		);
		String payloadWithoutValidation = req.getRequest().toString();
		String actualPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(payloadWithoutValidation));
		String expectedPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(expectedPayLoad));
//		System.out.println(actualPrettyPrint);
//		System.out.println(expectedPrettyPrint);
		assertEquals(expectedPrettyPrint.trim(),actualPrettyPrint.trim());
	}
	
	
	@Test
	public void testBuilderHelperListTest() throws IOException, ParkarCoreAPIException{
		String expectedPayLoad = "[{\"startDateTime\" : \""+dg.getDateTime(2, "T22:30:19")+""+"\",\"commentNotes\" :"
				+ " [{\"comment\" : {\"name\" : \"some comments\",\"active\" : true,\"id\" : 20}},"
				+ "{\"comment\" : {\"name\" : \"another comments\",\"active\" : false,\"id\" : 21}}],"
				+ "\"employee\" : {\"id\" : 1,\"qualifier\" : \"382\"},\"endDateTime\" : \"2016-07-23T22:30:19\","
				+ "\"segments\" : [{\"startDateTime\" : \""+dg.getDateTime(0, "T08:00:00")+"\",\"workruleRef\" : {\"qualifier\" : \"Full time\", \"id\" : 2},\"type\" : \"REGULAR_SEGMENT\",\"endDateTime\" : \""+dg.getDateTime(0, "T08:00:00")+"\",\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 21/Register0\"}},"
				+ "{\"startDateTime\" : \""+dg.getDateTime(1, "T08:00:00")+"\",\"workruleRef\" : {\"qualifier\" : \"Full time\", \"id\" : 2},\"type\" : \"REGULAR_SEGMENT\",\"endDateTime\" : \""+dg.getDateTime(1, "T08:00:00")+"\",\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 21/Register1\"}}]}"
				+ ",{\"startDateTime\" : \""+dg.getDateTime(2, "T22:30:19")+""+"\",\"commentNotes\" :"
				+ " [{\"comment\" : {\"name\" : \"some comments\",\"active\" : true,\"id\" : 20}},"
				+ "{\"comment\" : {\"name\" : \"another comments\",\"active\" : false,\"id\" : 21}}],"
				+ "\"employee\" : {\"id\" : 1,\"qualifier\" : \"382\"},\"endDateTime\" : \"2016-07-23T22:30:19\","
				+ "\"segments\" : [{\"startDateTime\" : \""+dg.getDateTime(0, "T08:00:00")+"\",\"workruleRef\" : {\"qualifier\" : \"Full time\", \"id\" : 2},\"type\" : \"REGULAR_SEGMENT\",\"endDateTime\" : \""+dg.getDateTime(0, "T08:00:00")+"\",\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 21/Register0\"}},"
				+ "{\"startDateTime\" : \""+dg.getDateTime(1, "T08:00:00")+"\",\"workruleRef\" : {\"qualifier\" : \"Full time\", \"id\" : 2},\"type\" : \"REGULAR_SEGMENT\",\"endDateTime\" : \""+dg.getDateTime(1, "T08:00:00")+"\",\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 21/Register1\"}}]}]";
		//String schemaLocation = new java.io.File(".").getCanonicalPath() + "\\resources\\unitTest\\schema\\shift.json";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("schema/shift.json").getFile());
		String schemaLocation = file.getAbsolutePath();
		Request payload = build(
				list(
						map(
							"startDateTime", dg.getDateTime(2, "T22:30:19"),
							"commentNotes", list( 
											map(
													"comment" , map("name","some comments", "active", true, "id", 20)
											),
											map(
													"comment" , map("name","another comments", "active", false, "id", 21)
											)
									),
							"employee", objRef("382",1),
							"endDateTime", "2016-07-23T22:30:19",
							"segments",
							mapInRange(0,1,
									map(
											"workruleRef", objRef("Full time",2),
											"type", "REGULAR_SEGMENT",
											"startDateTime", "{index}dT08:00:00",
								            "endDateTime", "{index}dT08:00:00",
											"orgJobRef", objRef("Organization/Division 1/Facility 1/Store 2/Department 21/Register{index}")
									)
							)
						),
						map(
								"startDateTime", dg.getDateTime(2, "T22:30:19"),
								"commentNotes", list( 
												map(
														"comment" , map("name","some comments", "active", true, "id", 20)
												),
												map(
														"comment" , map("name","another comments", "active", false, "id", 21)
												)
										),
								"employee", objRef("382",1),
								"endDateTime", "2016-07-23T22:30:19",
								"segments",
								mapInRange(0,1,
										map(
												"workruleRef", objRef("Full time",2),
												"type", "REGULAR_SEGMENT",
												"startDateTime", "{index}dT08:00:00",
									            "endDateTime", "{index}dT08:00:00",
												"orgJobRef", objRef("Organization/Division 1/Facility 1/Store 2/Department 21/Register{index}")
										)
								)
							)
					)
		);
		
		String actualPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(payload.getRequest().toString()));
		String expectedPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(expectedPayLoad));
		assertEquals(expectedPrettyPrint.trim(),actualPrettyPrint.trim());
	}
	
	@Test
	 public void testVariousInputForEmptyList() throws ParkarCoreAPIException, JsonProcessingException, IOException{
	  Request payload = build(list());
	    
	  String actualPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(payload.getRequest().toString()));
	  String expectedPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree("[]"));
	  assertEquals(expectedPrettyPrint.trim(),actualPrettyPrint.trim());
	 
	  
	  payload = build(map(
	       "forEmployees", list(
	           map(
	               "employeeRef", map(
	                   "qualifier", "382"
	               ),
	               "shifts", map(
	                   "create", list(),
	                   "update", list(
	                       map(
	                           "id", 2031,
	                           "employee", map(
	                               "qualifier", "618"
	                           ),
	                           "segments", list(
	                               map(
	                                   "startDateTime", "2016-05-28T08:00:00",
	                                   "endDateTime", "2016-05-28T16:00:00",
	                                   "type", "TRANSFER_SEGMENT"
	                               )
	                           )
	                       )
	                   )
	               )
	           )
	       )
	   ));
	  
	  
	  actualPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(payload.getRequest().toString()));
	  expectedPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree("{\"forEmployees\": [{\"employeeRef\": {\"qualifier\": \"382\"},\"shifts\": { \"create\": [],\"update\": [{\"id\": 2031,\"employee\": {\"qualifier\": \"618\"},\"segments\": [{\"startDateTime\": \"2016-05-28T08:00:00\",\"endDateTime\": \"2016-05-28T16:00:00\", \"type\": \"TRANSFER_SEGMENT\"}]}]}}]}"));
	  assertEquals(expectedPrettyPrint.trim(),actualPrettyPrint.trim());
	 }
	
	@Test
	public void testVariousInputForList() throws ParkarCoreAPIException, JsonProcessingException, IOException{
		Request payload = build(map("test",list(
				"test1",
				"test2",
				2
			)));
				
		String actualPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(payload.getRequest().toString()));
		String expectedPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree("{\"test\":[\"test1\",\"test2\",2]}"));
		assertEquals(expectedPrettyPrint.trim(),actualPrettyPrint.trim());
		
		
		payload = build(list(
				map("testkey","value"),
				"test1",
				"test2",
				2
			));
		
		actualPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(payload.getRequest().toString()));
		expectedPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree("[{\"testkey\" : \"value\"},\"test1\",\"test2\",2]"));
		assertEquals(expectedPrettyPrint.trim(),actualPrettyPrint.trim());
	}
	
	@Test
	public void testNegativeInputForMap(){
		Map<String,Object> map = null;
		try {
			map = map("testKey");
		} catch (ParkarCoreAPIException e) {
			assertEquals("You cannot give odd number of element", e.getMessage());
		}
		assertEquals(map, null);

		try {
			map = map("testKey",1,2);
		} catch (ParkarCoreAPIException e) {
			assertEquals("You cannot give odd number of element", e.getMessage());
		}
		assertEquals(map, null);
		
		try {
			map = map();
		} catch (ParkarCoreAPIException e) {
			assertEquals("You need to prepare at least two argument", e.getMessage());
		}
		assertEquals(map, null);
		
		try {
			map = map(2,"testKey");
		} catch (ParkarCoreAPIException e) {
			assertEquals("Key 2 must be string", e.getMessage());
		}
		assertEquals(map, null);
		
		
		try {
			map = objRef();
		} catch (ParkarCoreAPIException e) {
			assertEquals("You need to prepare at least two argument", e.getMessage());
		}
		assertEquals(map, null);
		
		//if more than two parameters are given to objRef, a map of {"id": null, "qualifier" : null} will be returned
		try {
			map = objRef(2, "test" ,43);
		} catch (ParkarCoreAPIException e) {
			assertEquals("You need to prepare at least two argument", e.getMessage());
		}
		assertEquals(map.get("id"), null);
		assertEquals(map.get("qualifier"), null);
	}
}