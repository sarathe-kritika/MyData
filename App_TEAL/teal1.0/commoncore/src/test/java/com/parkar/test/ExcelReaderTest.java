package com.parkar.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.utils.common.ExcelReader;

public class ExcelReaderTest {

	private static File testExcel = null;

	private Map<String, String> rowsRead = null;
	private List<Map<String, String>> testData = null;
	private String testCaseId = null;
	
	@BeforeClass
	public static void setUp() throws Exception {

		testExcel = new File("resources/DemoTest.xlsx");

		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet(" TestCase Info ");
		XSSFRow row;
		Map<String, Object[]> empinfo = new TreeMap<String, Object[]>();
		empinfo.put("1", new Object[] { "TESTCASE_ID", "TESTCASE_NAME", "DEPENDENCY" });
		empinfo.put("2", new Object[] { "test_ALM82066_AddItemInCart", "AddItemInCart", "AddItemInCart1" });
		empinfo.put("3", new Object[] { "test_ALM82062_ComposeMail", "ComposeMail", "NO" });
		empinfo.put("4", new Object[] { "test_ALM82063_DeleteMail", "DeleteMail", "test_ALM82062_ComposeMail" });
		empinfo.put("5", new Object[] { "test_ALM82064_MoveToTrash", "MoveToTrash", "NO" });
		empinfo.put("6", new Object[] { "test_ALM82065_MoveToInbox", "MoveToInbox", "test_ALM82064_MoveToTrash" });

		Set<String> keyid = empinfo.keySet();
		int rowid = 0;
		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = empinfo.get(key);
			int cellid = 0;
			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}

		FileOutputStream out = new FileOutputStream(testExcel);
		workbook.write(out);
		out.close();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		testExcel.delete();
	}

	@Test
	public void testFileNotNull() throws ParkarCoreCommonException
	{
		Assert.assertNotEquals(ExcelReader.read(testExcel.getName()), testData);
	}
	@Test
	public void testListEquals() throws Exception {
		testData = ExcelReader.read(testExcel.getName(), 1, 1);
		Assert.assertEquals(ExcelReader.read(testExcel.getName(), 1, 1), testData);
	}

	@Test
	public void testListNotEquals() throws ParkarCoreCommonException, IOException {
		testData = ExcelReader.read(testExcel.getName(), 1, 1);
		Assert.assertNotEquals(ExcelReader.read(testExcel.getName(), 1,2), testData);
	}

	public void testMapEquals() throws ParkarCoreCommonException, IOException
	{
		rowsRead=ExcelReader.read(testExcel.getName(), 2);
		Assert.assertEquals(ExcelReader.read(testExcel.getName(), 2), rowsRead);
	}
	@Test
	public void testMapNotEquals() throws ParkarCoreCommonException, IOException
	{
		rowsRead=ExcelReader.read(testExcel.getName(), 2);
		Assert.assertNotEquals(ExcelReader.read(testExcel.getName(), 1), rowsRead);
	}
	
	@Test
	public void testRowNotNull() throws ParkarCoreCommonException, IOException {
		rowsRead = ExcelReader.read(testExcel.getName(), 1);
		Assert.assertTrue(rowsRead != null);
	}

	@Test
	public void testInvalidFilePath() {
		boolean exceptionThrowed = false;
		try {
			ExcelReader.read("path to not exist file");
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
	}

	@Test
	public void testInvalidRowNumber() throws IOException {
		boolean exceptionThrowed = false;
		try {
			ExcelReader.read(testExcel.getName(), 10000);
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
	}

	@Test
	public void testNegativeRowNumber() throws IOException {
		boolean exceptionThrowed = false;
		try {
			ExcelReader.read(testExcel.getName(), -1);
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
	}

	@Test
	public void testNegativeRange() throws IOException
	{
		boolean exceptionThrowed = false;
		try {
			ExcelReader.read(testExcel.getName(), -1,-3);
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
	}
	
	@Test
	public void testRowTestCaseId() throws ParkarCoreCommonException {
		testCaseId = "test_ALM82066_AddItemInCart";
		rowsRead = ExcelReader.read(testExcel.getName(), testCaseId);
		Assert.assertEquals(rowsRead.get("TESTCASE_ID"), testCaseId);
	}

	@Test
	public void testInvalidParam() throws IOException {
		boolean exceptionThrowed = false;
		try {
			ExcelReader.read(testExcel.getName(), 2, 1);
		} catch (ParkarCoreCommonException e) {
			exceptionThrowed = true;
		}
		Assert.assertTrue(exceptionThrowed);
	}
}
