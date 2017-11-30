package com.parkar.json;


import static com.parkar.api.rest.json.JsonReaderHelper.build;
import static com.parkar.api.rest.json.JsonReaderHelper.getValue;
import static com.parkar.api.rest.json.JsonReaderHelper.read;
import static com.parkar.api.rest.json.JsonReaderHelper.setValue;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkar.dateutils.DateParser;
import com.parkar.exception.ParkarCoreCommonException;

public class ReaderHelperTest {
	private static final String FORMAT = "yyyy-MM-dd";
	private ObjectMapper mapper;
	

	@Before
	public void setUp() throws Exception {
		HashMap<String,String> options = new HashMap<String,String>();
		options.put("dateFormat", FORMAT);
		mapper = new ObjectMapper();

	}
	
	@Test
	public void testDateHelperTest() throws IOException, ParkarCoreCommonException{
		//String location = new java.io.File(".").getCanonicalPath() + "\\resources\\unitTest\\payload\\shift.json";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("payload/shift.json").getFile());
		String location = file.getAbsolutePath();
		String expectedPayLoad = "{\"startDateTime\" : \"2016-06-16T22:30:19\",\"commentNotes\" :"
				+ " [{\"comment\" : {\"name\" : \"some comments\",\"active\" : true,\"id\" : 20}},"
				+ "{\"comment\" : {\"name\" : \"another comments\",\"active\" : false,\"id\" : 21}}],"
				+ "\"employee\" : {\"id\" : 1,\"qualifier\" : \"382\"},\"endDateTime\" : \"2016-07-23T22:30:19\","
				+ "\"segments\" : [{\"workruleRef\" : {\"id\" : 2, \"qualifier\" : \"Full time\"},\"type\" : \"REGULAR_SEGMENT\",\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 21/Register\"}},"
				+ "{\"workruleRef\" : {\"qualifier\" : \"Part time\"},\"type\" : \"TRANSFER_SEGMENT\",\"skillCertProfileRefs\" : [{\"test\" : [ {\"id\" : 1,\"active\" : false} ]}],\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 22/RN\"}}]}";
		
		String payload = build(read(location));
		
		
		String actualPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(payload));
		String expectedPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(expectedPayLoad));
//		System.out.println(actualPrettyPrint);
//		System.out.println(expectedPrettyPrint);
		assertEquals(expectedPrettyPrint.trim(),actualPrettyPrint.trim());
	}
	
	@Test
	public void testReaderHelperTestWithSymbolicDate() throws IOException, ParkarCoreCommonException{
		//String location = new java.io.File(".").getCanonicalPath() + "\\resources\\unitTest\\payload\\shiftWithSymbolicDate.json";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("payload/shiftWithSymbolicDate.json").getFile());
		String location = file.getAbsolutePath();
		DateParser dp = new DateParser();
		
		String expectedPayLoad = "{\"startDateTime\" : \""+dp.parseDatePattern("TODAYT22:30:19")+"\",\"commentNotes\" :"
				+ " [{\"comment\" : {\"name\" : \"some comments\",\"active\" : true,\"id\" : 20}},"
				+ "{\"comment\" : {\"name\" : \"another comments\",\"active\" : false,\"id\" : 21}}],"
				+ "\"employee\" : {\"id\" : 1,\"qualifier\" : \"382\"},\"endDateTime\" : \""+dp.parseDatePattern("-10MT22:30:19")+"\","
				+ "\"segments\" : [{\"startDateTime\" : \""+dp.parseDatePattern("+2SUNDAY2M-10d2yT11:24:20")+"\",\"workruleRef\" : {\"id\" : 2, \"qualifier\" : \"Full time\"},\"type\" : \"REGULAR_SEGMENT\",\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 21/Register\"}},"
				+ "{\"workruleRef\" : {\"qualifier\" : \"Part time\"},\"type\" : \"TRANSFER_SEGMENT\",\"skillCertProfileRefs\" : [{\"test\" : [ {\"id\" : 1,\"active\" : false} ]}],\"orgJobRef\" : {\"qualifier\" : \"Organization/Division 1/Facility 1/Store 2/Department 22/RN\"}}]}";
		
		String payload = build(read(location));
		
		String actualPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(payload));
		String expectedPrettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(expectedPayLoad));
//		System.out.println(actualPrettyPrint);
//		System.out.println(expectedPrettyPrint);
		assertEquals(expectedPrettyPrint.trim(),actualPrettyPrint.trim());
	}
	
	@Test
	public void testReaderHelperGetValueAtPath() throws IOException, ParkarCoreCommonException{
		//String location = new java.io.File(".").getCanonicalPath() + "\\resources\\unitTest\\payload\\shiftValueAtPath.json";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("payload/shiftValueAtPath.json").getFile());
		String location = file.getAbsolutePath();
		
		String startDateTime = getValue("startDateTime",read(location));
		assertEquals("TODAYT22:30:19",startDateTime);
		
		String employeeId = getValue("employee.id",read(location));
		assertEquals("1",employeeId);

		String commentName = getValue("commentNotes[0].comment.name",read(location));
		assertEquals("some comments",commentName);
		
		String noteText = getValue("commentNotes[0].notes[0].text",read(location));
		assertEquals("People can WFH",noteText);
		
		String noteTextNegative = getValue("commentNotes[0].notes[0].text1",read(location));
		assertEquals(null,noteTextNegative);
		
		String startDateTimeNegative = getValue("startDateTimes",read(location));
		assertEquals(null,startDateTimeNegative);
	}
	
	
	@Test
	public void testReaderHelperSetValueAtPath() throws IOException, ParkarCoreCommonException{
		//String location = new java.io.File(".").getCanonicalPath() + "\\resources\\unitTest\\payload\\shiftValueAtPath.json";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("payload/shiftValueAtPath.json").getFile());
		String location = file.getAbsolutePath();
		JsonNode node = read(location);
		
		setValue("TOMORROWT22:30:19", "startDateTime", node);
		String startDateTime = getValue("startDateTime",node);
		assertEquals("TOMORROWT22:30:19",startDateTime);
		
		setValue(22, "employee.id", node);
		String employeeId = getValue("employee.id",node);
		assertEquals("22",employeeId);

		setValue("some comments change", "commentNotes[0].comment.name", node);
		String commentName = getValue("commentNotes[0].comment.name",node);
		assertEquals("some comments change",commentName);
		
		setValue("People cannot WFH", "commentNotes[0].notes[0].text", node);
		String noteText = getValue("commentNotes[0].notes[0].text",node);
		assertEquals("People cannot WFH",noteText);
		
		setValue("something", "commentNotes[0].notes1[0].text", node);
		String notesNegative = getValue("commentNotes[0].notes1[0].text",node);
		assertEquals(null,notesNegative);
		
		setValue("something", "commentNotes[0].notes[0].text1", node);
		String noteTextNegative = getValue("commentNotes[0].notes[0].text1",node);
		assertEquals("something",noteTextNegative);
	}
}