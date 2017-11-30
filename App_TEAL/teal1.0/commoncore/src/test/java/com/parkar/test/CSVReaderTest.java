package com.parkar.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.parkar.dateutils.DateGenerator;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.utils.common.CSVReader;

public class CSVReaderTest {
	private static File shift = null;
	private static File contactDetails = null;
	private static File dateEvaluation=null;
	private static File invalidDateExpression=null;
	private static File testData=null;
	
	private static List<Map<String, String>> shiftList = new ArrayList<>();
	private static List<Map<String, String>> contactDetailsList = new ArrayList<>();
	
	private int rowNum =0;
	private Map<String,String> rowsRead=null;
	private String filePath=null;
	private String testCaseId;

	@BeforeClass
	public static void setUp() throws Exception {
		shift = new File("resources/shift.csv");
		contactDetails = new File("resources/ContactDetails.csv");
		
		dateEvaluation = new File("resources/dateEvaluation.csv");
		invalidDateExpression = new File("resources/invalidDateExpression.csv");
		testData = new File("resources/testData.csv");
		
		
		FileUtils.writeStringToFile(dateEvaluation, "id,today,today-5d,today+5d,today+5w,today+5M,today+5y\n" + 
				"1,{{{today}}},{{{today-5d}}},{{{+5d}}},{{{+5w}}},{{{+5M}}},{{{+5y}}}");
		
		
		FileUtils.writeStringToFile(invalidDateExpression, "id,today,today-5d,today+5d,today+5w,today+5M,today+5y,invalidExpression\n" + 
				"1,{{{invalidExpression}}},{{{toda}}},{{{+5}}}},{{{+5w}}},{{{+5M}}},{{{+5y}}},{{{invalidExpression}}}\n");
		
		
		FileUtils.writeStringToFile(shift, "id,startDateTime.dayOfMonth,startDateTime.dayOfWeek,startDateTime.dayOfYear,endDateTime.dayOfMonth,endDateTime.dayOfWeek,endDateTime.dayOfYear,segments.id,segments.segmentTypeRef.id,segments.segmentTypeRef.qualifier\n" + 
				"1||2,2,3,4,5,6,7,8,9,10\n" +
				"1,2,3,4,5,6,7,11,12,13");
		
		FileUtils.writeStringToFile(testData, "TestCaseID,UserName,Password,To,Subject,Body\n" + 
				"1,parkartesting@gmail.com,parkarautomation,parkartesting@gmail.com,TestMail1,This is Test mail 1||This is Test mail 2.\n" +
				"50,parkartesting@gmail.com,parkarautomation,parkartesting@gmail.com,TestMail2,This is Test mail 50.");
		
		shiftList.add(new LinkedHashMap<String, String>(){{
			put("id", "1,2");
			put("startDateTime.dayOfMonth", "2");
			put("startDateTime.dayOfWeek", "3");
			put("startDateTime.dayOfYear", "4");
			put("endDateTime.dayOfMonth", "5");
			put("endDateTime.dayOfWeek", "6");
			put("endDateTime.dayOfYear", "7");
			put("segments.id", "8");
			put("segments.segmentTypeRef.id", "9");
			put("segments.segmentTypeRef.qualifier", "10");
		}});
		shiftList.add(new LinkedHashMap<String, String>(){{
			put("id", "1");
			put("startDateTime.dayOfMonth", "2");
			put("startDateTime.dayOfWeek", "3");
			put("startDateTime.dayOfYear", "4");
			put("endDateTime.dayOfMonth", "5");
			put("endDateTime.dayOfWeek", "6");
			put("endDateTime.dayOfYear", "7");
			put("segments.id", "11");
			put("segments.segmentTypeRef.id", "12");
			put("segments.segmentTypeRef.qualifier", "13");
		}});
		
		
		FileUtils.writeStringToFile(contactDetails, "name,id,addresses.address.addresstype,addresses.address.addresslines,addresses.address.city\n" + 
				"Amit||Gandhi,123,office,Okaya Center,noida\n" +
				"Amit,123,office,Sec 62,noida\n" +
				"Amit,123,home,Indirapuram,noida\n" +
				"Amit,123,home,ATS,noida");
		contactDetailsList.add(new LinkedHashMap<String, String>(){{
			put("name", "Amit,Gandhi");
			put("id", "123");
			put("addresses.address.addresstype", "office");
			put("addresses.address.addresslines", "Okaya Center");
			put("addresses.address.city", "noida");
		}});
		contactDetailsList.add(new LinkedHashMap<String, String>(){{
			put("name", "Amit");
			put("id", "123");
			put("addresses.address.addresstype", "office");
			put("addresses.address.addresslines", "Sec 62");
			put("addresses.address.city", "noida");
		}});
		contactDetailsList.add(new LinkedHashMap<String, String>(){{
			put("name", "Amit");
			put("id", "123");
			put("addresses.address.addresstype", "home");
			put("addresses.address.addresslines", "Indirapuram");
			put("addresses.address.city", "noida");
		}});
		contactDetailsList.add(new LinkedHashMap<String, String>(){{
			put("name", "Amit");
			put("id", "123");
			put("addresses.address.addresstype", "home");
			put("addresses.address.addresslines", "ATS");
			put("addresses.address.city", "noida");
		}});
	}
	
	@AfterClass
	public static void done() throws Exception {
		shift.delete();
		contactDetails.delete();
		dateEvaluation.delete();
		invalidDateExpression.delete();
		testData.delete();
	}

	@Test
	public void testEquals() throws Exception {
		Assert.assertEquals(CSVReader.readCSV(shift.getName()), shiftList);
		Assert.assertEquals(CSVReader.readCSV(shift.getName(), 1), shiftList.get(0));
		Assert.assertEquals(new ArrayList(){{ add(CSVReader.readCSV(shift.getName(), 1));}}, CSVReader.readCSV(shift.getName(), 1, 1));
		Assert.assertEquals(CSVReader.readCSV(shift.getName(), 2), shiftList.get(1));
		Assert.assertEquals(CSVReader.readCSV(shift.getName(), 1, 2), shiftList);
		
		Assert.assertEquals(CSVReader.readCSV(contactDetails.getName()), contactDetailsList);
		Assert.assertEquals(CSVReader.readCSV(contactDetails.getName(), 1), contactDetailsList.get(0));
		Assert.assertEquals(CSVReader.readCSV(contactDetails.getName(), 3), contactDetailsList.get(2));
		Assert.assertEquals(CSVReader.readCSV(contactDetails.getName(), 1, 4), contactDetailsList);;
	}
	
	@Test
	public void testException() throws Exception {
		boolean exceptionThrowed = false;
		try {
			CSVReader.readCSV("path to not exist file");
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
		
		exceptionThrowed = false;
		try {
			CSVReader.readCSV("path to not exist file", 1);
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
		
		exceptionThrowed = false;
		try {
			CSVReader.readCSV("path to not exist file", 1, 2);
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
		
		exceptionThrowed = false;
		try {
			CSVReader.readCSV(shift.getName(), 10000);
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
		
		exceptionThrowed = false;
		try {
			CSVReader.readCSV(shift.getName(), -1);
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
		
		exceptionThrowed = false;
		try {
			CSVReader.readCSV(shift.getName(), 1, 10000);
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
		
		exceptionThrowed = false;
		try {
			CSVReader.readCSV(shift.getName(), 2, 1);
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
		
		exceptionThrowed = false;
		try {
			CSVReader.readCSV(shift.getName(), Integer.MIN_VALUE, Integer.MAX_VALUE);
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
		
	}
	
	@Test
	public void testNotEquals() throws Exception {
		Assert.assertNotEquals(CSVReader.readCSV(shift.getName()), contactDetailsList);
		Assert.assertNotEquals(CSVReader.readCSV(shift.getName(), 1), new ArrayList(){{
			add(contactDetailsList.get(0));
		}});
		Assert.assertNotEquals(CSVReader.readCSV(shift.getName(), 2), new ArrayList(){{
			add(contactDetailsList.get(1));
		}});
		Assert.assertNotEquals(CSVReader.readCSV(shift.getName(), 1, 2), contactDetailsList);
		
		Assert.assertNotEquals(CSVReader.readCSV(contactDetails.getName()), shiftList);
		Assert.assertNotEquals(CSVReader.readCSV(contactDetails.getName(), 1), new ArrayList(){{
			add(shiftList.get(0));
		}});
		Assert.assertNotEquals(CSVReader.readCSV(contactDetails.getName(), 3), new ArrayList(){{
			add(shiftList.get(1));
		}});
		Assert.assertNotEquals(CSVReader.readCSV(contactDetails.getName(), 1, 4), shiftList);;
	}
	
	@Test
	public void testReadShift() throws Exception {

		rowNum = 1;
		rowsRead = CSVReader.readCSV(shift.getName(), rowNum);
		Assert.assertTrue(rowsRead != null);

		Assert.assertTrue(rowsRead != null);
		Assert.assertEquals(rowsRead.get("id"), "1,2");
		Assert.assertEquals(rowsRead.get("startDateTime.dayOfMonth"), "2");
		Assert.assertEquals(rowsRead.get("startDateTime.dayOfWeek"), "3");
		Assert.assertEquals(rowsRead.get("startDateTime.dayOfYear"), "4");
		Assert.assertEquals(rowsRead.get("endDateTime.dayOfMonth"), "5");
		Assert.assertEquals(rowsRead.get("endDateTime.dayOfWeek"), "6");
		Assert.assertEquals(rowsRead.get("endDateTime.dayOfYear"), "7");
		Assert.assertEquals(rowsRead.get("segments.id"), "8");
		Assert.assertEquals(rowsRead.get("segments.segmentTypeRef.id"), "9");
		Assert.assertEquals(rowsRead.get("segments.segmentTypeRef.qualifier"), "10");

		rowNum = 2;
		rowsRead = CSVReader.readCSV(shift.getName(), rowNum);
		Assert.assertTrue(rowsRead != null);

		Assert.assertTrue(rowsRead != null);
		Assert.assertEquals(rowsRead.get("id"), "1");
		Assert.assertEquals(rowsRead.get("startDateTime.dayOfMonth"), "2");
		Assert.assertEquals(rowsRead.get("startDateTime.dayOfWeek"), "3");
		Assert.assertEquals(rowsRead.get("startDateTime.dayOfYear"), "4");
		Assert.assertEquals(rowsRead.get("endDateTime.dayOfMonth"), "5");
		Assert.assertEquals(rowsRead.get("endDateTime.dayOfWeek"), "6");
		Assert.assertEquals(rowsRead.get("endDateTime.dayOfYear"), "7");
		Assert.assertEquals(rowsRead.get("segments.id"), "11");
		Assert.assertEquals(rowsRead.get("segments.segmentTypeRef.id"), "12");
		Assert.assertEquals(rowsRead.get("segments.segmentTypeRef.qualifier"), "13");

	}

	@Test
	public void testReaddContactDetails() throws Exception {

		rowNum = 1;
		rowsRead = CSVReader.readCSV(contactDetails.getName(), rowNum);
		Assert.assertTrue(rowsRead != null);
		Assert.assertTrue(rowsRead != null);
		Assert.assertEquals(rowsRead.get("id"), "123");
		Assert.assertEquals(rowsRead.get("name"), "Amit,Gandhi");
		Assert.assertEquals(rowsRead.get("addresses.address.addresstype"), "office");
		Assert.assertEquals(rowsRead.get("addresses.address.addresslines"), "Okaya Center");
		Assert.assertEquals(rowsRead.get("addresses.address.city"), "noida");
		
		// read the line number of 3
		rowNum = 3;
		rowsRead = CSVReader.readCSV(contactDetails.getName(), rowNum);
		Assert.assertTrue(rowsRead != null);
		Assert.assertTrue(rowsRead != null);
		Assert.assertEquals(rowsRead.get("id"), "123");
		Assert.assertEquals(rowsRead.get("name"), "Amit");
		Assert.assertEquals(rowsRead.get("addresses.address.addresstype"), "home");
		Assert.assertEquals(rowsRead.get("addresses.address.addresslines"), "Indirapuram");
		Assert.assertEquals(rowsRead.get("addresses.address.city"), "noida");

	}

	@Test
	public void testReadCSV1() throws Exception {

		testCaseId = "1";
		rowsRead = CSVReader.readCSV(testData.getName(), testCaseId);
		Assert.assertTrue(rowsRead != null);
		Assert.assertEquals(rowsRead.get("UserName"), "parkartesting@gmail.com");
		Assert.assertEquals(rowsRead.get("Password"), "parkarautomation");
		Assert.assertEquals(rowsRead.get("To"), "parkartesting@gmail.com");
		Assert.assertEquals(rowsRead.get("Subject"), "TestMail1");
		Assert.assertEquals(rowsRead.get("Body"), "This is Test mail 1,This is Test mail 2.");
		
	}

	@Test
	public void testReadCSV2() throws Exception {
		testCaseId = "2";
		try {
			rowsRead = CSVReader.readCSV(contactDetails.getName(), testCaseId);
		}catch(Exception e){
			Assert.assertTrue(e instanceof ParkarCoreCommonException);
			Assert.assertTrue(rowsRead == null);
		}
	}

	@Test
	public void testRowNumberInvalidException() {
		rowNum = -1;
		boolean expectedThrowsException = false;
		
		try {
			CSVReader.readCSV(contactDetails.getName(), rowNum);
		} catch (Exception e) {
			expectedThrowsException = true;
		}
		Assert.assertTrue(expectedThrowsException);
	}

	@Test
	public void testFilePathException() {
		filePath = "";
		rowNum = 1;
		boolean expectedThrowsException = false;
		
		try {
			CSVReader.readCSV(filePath, rowNum);
		} catch (Exception e) {
			expectedThrowsException = true;
		}
		Assert.assertTrue(expectedThrowsException);
	}

	@Test
	public void testFailureRead() throws Exception {
		rowNum = 100;
		boolean expectedThrowsException = false;

		try {
			rowsRead = CSVReader.readCSV(contactDetails.getName(), rowNum);
		} catch (Exception e) {
			expectedThrowsException = true;
		}
		Assert.assertTrue(expectedThrowsException);
	}
	
	@Test
	public void testDateEvaluation() throws Exception {

		rowNum = 1;
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("dateFormat", "yyyy-MM-dd");
		DateGenerator dg = new DateGenerator(options);
		rowsRead = CSVReader.readCSV(dateEvaluation.getName(), rowNum);

		Assert.assertTrue(rowsRead != null);

		Assert.assertEquals(rowsRead.get("today"), dg.getToday());
		Assert.assertEquals(rowsRead.get("today+5d"), dg.getDate(5));
		Assert.assertEquals(rowsRead.get("today-5d"), dg.getDate(-5));
		Assert.assertEquals(rowsRead.get("today+5w"), dg.getWeekAs(5));
		Assert.assertEquals(rowsRead.get("today+5M"), dg.getMonthAs(5));
		Assert.assertEquals(rowsRead.get("today+5y"), dg.getYearAs(5));
	}
	
	@Test
	public void testDateEvaluationException() {
		rowNum = 1;
		boolean invalidDateException = false;
		try {
			rowsRead = CSVReader.readCSV(invalidDateExpression.getName(), rowNum);
		} catch (Exception e) {
			invalidDateException = true;
		}

		Assert.assertTrue(invalidDateException);
	}
	
	
}
