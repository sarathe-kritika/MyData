package com.parkar.utils.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.parkar.dateutils.DateParser;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;

public class ExcelReader {
	final static Logger logger = Logger.getLogger(ExcelReader.class);

	private static final String DateTypeStartWith = "{{{";
	private static final String DateTypeEndWith = "}}}";

	private static final DateParser dateParser = new DateParser();

	private static final String isDateTypePattern = "(\\{{3})(.*)(\\}{3})";

	private static List<String> getHeaders(XSSFSheet sheet) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		List<String> headersList = new LinkedList<String>();

		Row headerRow = sheet.getRow(0);
		for (int columnNo = 0; columnNo < headerRow.getLastCellNum(); columnNo++) {
			Cell cell = headerRow.getCell(columnNo);
			if (cell != null) {
				headersList.add(cell.toString());
			} else {
				String message = "Excel file is not valid, header cell cannot be blank.";
				logger.error(message);
				throw new ParkarCoreCommonException(message);
			}
		}
		ParkarLogger.traceLeave();
		return headersList;
	}

	/**
	 * Reads the whole content of given excel file
	 * 
	 * @param fileName
	 *            name of file
	 * @return The whole content of given excel file
	 * @throws ParkarCoreCommonException
	 *             ParkarCoreCommonException thrown
	 */
	public static List<Map<String, String>> read(String fileName) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		List<Map<String, String>> content = new ArrayList<>();
		String filePath = FileUtils.getAbsolutePath(fileName);

		try (FileInputStream fis = new FileInputStream(filePath)) {
			XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			List<String> headersList = getHeaders(mySheet);

			int rowNo = 1;
			for (Row row : mySheet) {
				if (rowNo != 1) {
					List<String> columnsData = new LinkedList<String>();
					Map<String, String> rowMap = new LinkedHashMap<String, String>();

					for (int columnNo = 0; columnNo < row.getLastCellNum(); columnNo++) {
						Cell cell = row.getCell(columnNo, Row.CREATE_NULL_AS_BLANK);
						columnsData.add(cell.toString());
					}
					for (int i = 0; i < headersList.size(); i++) {
						if (isDatePattern(columnsData.get(i))) {
							rowMap.put(headersList.get(i),
									dateParser.parseDatePattern(getInnnerDateExpresssion(columnsData.get(i))));
						} else {
							rowMap.put(headersList.get(i), columnsData.get(i));
						}
					}
					content.add(rowMap);
				}
				rowNo++;
			}
		} catch (IOException e) {
			String message = "Exception occured while reading excel file: " + fileName;
			logger.error(message, e);
			throw new ParkarCoreCommonException(message, e);
		}
		ParkarLogger.traceLeave();
		return content;
	}

	/**
	 * Reads the single line content of given excel file
	 * 
	 * @param fileName
	 *            name of file
	 * @param rowNo row number
	 * @return Map whose keySet contains headers and valueSet contains given
	 *         row's values.
	 * @throws ParkarCoreCommonException
	 *             ParkarCoreCommonException thrown
	 * @throws IOException IOException thrown
	 */
	public static Map<String, String> read(String fileName, int rowNo) throws ParkarCoreCommonException, IOException {
		ParkarLogger.traceEnter();
		Map<String, String> rowMap = new LinkedHashMap<String, String>();
		String filePath = FileUtils.getAbsolutePath(fileName);
		int rowCount = getRowCount(filePath);

		if (rowNo < 0 || rowNo > rowCount) {
			String message = "Row doesn't exist, row: " + rowNo;
			logger.error(message);
			throw new ParkarCoreCommonException(message);
		}

		try (FileInputStream fis = new FileInputStream(filePath)) {
			XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);

			List<String> headersList = getHeaders(mySheet);

			int k = 0;
			boolean found = false;
			for (Row row : mySheet) {
				if (rowNo == k) {
					found = true;
					List<String> columnsData = new LinkedList<String>();

					for (int columnNo = 0; columnNo < row.getLastCellNum(); columnNo++) {
						Cell cell = row.getCell(columnNo, Row.CREATE_NULL_AS_BLANK);
						columnsData.add(cell.toString());
					}
					for (int i = 0; i < headersList.size(); i++) {
						if (isDatePattern(columnsData.get(i))) {
							rowMap.put(headersList.get(i),
									dateParser.parseDatePattern(getInnnerDateExpresssion(columnsData.get(i))));
						} else {
							rowMap.put(headersList.get(i), columnsData.get(i));
						}
					}
					break;
				}
				k++;
			}
			if (!found) {
				String message = "Row " + rowNo + " doesn't exist in the excel file: " + fileName;
				logger.error(message);
				throw new ParkarCoreCommonException(message);
			}
		} catch (IOException e) {
			String message = "Exception occured while reading excel file: " + fileName;
			logger.error(message, e);
			throw new ParkarCoreCommonException(message, e);
		}
		ParkarLogger.traceLeave();
		return rowMap;
	}

	/**
	 * Reads the line content from startRow to endRow
	 * 
	 * @param fileName
	 *            name of file
	 * @param startRow
	 *            starting row no
	 * @param endRow
	 *            ending row no
	 * @return The line content from startRow to endRow
	 * @throws ParkarCoreCommonException
	 *             ParkarCoreCommonException thrown
	 * @throws IOException IOException thrown 
	 */
	public static List<Map<String, String>> read(String fileName, int startRow, int endRow)
			throws ParkarCoreCommonException, IOException {
		ParkarLogger.traceEnter();
		List<Map<String, String>> content = new ArrayList<>();
		String filePath = FileUtils.getAbsolutePath(fileName);
		int rowCount = getRowCount(filePath);
		if (startRow < 0 || endRow < 0 || (startRow > endRow) || (startRow > rowCount) || (endRow > rowCount)) {
			String message = "Invalid parameter, startRow is: " + startRow + ", endRow is:" + endRow;
			logger.error(message);
			throw new ParkarCoreCommonException(message);
		}

		for (int i = startRow; i <= endRow; i++) {
			content.add(read(fileName, i));
		}
		ParkarLogger.traceLeave();
		return content;
	}

	/**
	 * Reads the single line content of given excel file based on the
	 * testCaseId, first column will be taken as TestCaseID
	 * 
	 * @param fileName path of the file
	 * @param testCaseId Id of test case
	 * @return Map whose keySet contains headers and valueSet contains given
	 *         row's values.
	 * @throws ParkarCoreCommonException
	 *             ParkarCoreCommonException thrown
	 */
	public static Map<String, String> read(String fileName, String testCaseId) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		Map<String, String> rowMap = new LinkedHashMap<String, String>();

		String filePath = FileUtils.getAbsolutePath(fileName);

		try (FileInputStream fis = new FileInputStream(filePath)) {
			XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);

			List<String> headersList = getHeaders(mySheet);

			boolean found = false;

			for (Row row : mySheet) {
				String testCaseIDData = row.getCell(0).toString();
				if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					testCaseIDData = testCaseIDData.substring(0, testCaseIDData.length() - 2);
				}
				if (testCaseId.equals(testCaseIDData)) {
					found = true;
					List<String> columnsData = new LinkedList<String>();

					for (int columnNo = 0; columnNo < row.getLastCellNum(); columnNo++) {
						Cell cell = row.getCell(columnNo, Row.CREATE_NULL_AS_BLANK);
						columnsData.add(cell.toString());
					}
					for (int i = 0; i < headersList.size(); i++) {
						if (isDatePattern(columnsData.get(i))) {
							rowMap.put(headersList.get(i),
									dateParser.parseDatePattern(getInnnerDateExpresssion(columnsData.get(i))));
						} else {
							rowMap.put(headersList.get(i), columnsData.get(i));
						}
					}
					break;
				}
			}

			if (!found) {
				String message = "TestCaseId " + testCaseId + " doesn't exist in the excel file: " + fileName;
				logger.error(message);
				throw new ParkarCoreCommonException(message);
			}
		} catch (IOException e) {
			String message = "Exception occured while reading excel file: " + fileName;
			logger.error(message, e);
			throw new ParkarCoreCommonException(message, e);
		}
		ParkarLogger.traceLeave();
		return rowMap;
	}

	/**
	 * Check if it is a date pattern
	 * 
	 * @param columnValue:
	 *            String
	 * @return true or false
	 */
	private static boolean isDatePattern(String columnValue) {
		ParkarLogger.traceEnter();
		int from = columnValue.indexOf(DateTypeStartWith);
		int to = columnValue.indexOf(DateTypeEndWith);
		ParkarLogger.traceLeave();
		return (from >= 0) && (to > from);
	}

	/**
	 * Get the inner date expression
	 * 
	 * @param columnValue:
	 *            String
	 * @return the expression in String
	 */
	private static String getInnnerDateExpresssion(String columnValue) {
		ParkarLogger.traceEnter();
		Pattern datePattern = Pattern.compile(isDateTypePattern);
		Matcher matcher = datePattern.matcher(columnValue);
		matcher.find();
		ParkarLogger.traceLeave();
		return matcher.group(2).replaceAll(" ", "");
	}

	/**
	 * This method will give the count of row present in excel file.
	 * @param fileName path of the file
	 * @return rowcount int
	 * @throws ParkarCoreCommonException ParkarCoreCommonException thrown
	 */
	private static int getRowCount(String fileName) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		int count;
		try (FileInputStream fis = new FileInputStream(fileName)) {
			XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			count= (mySheet.getPhysicalNumberOfRows()) - 1;
		} catch (IOException e) {
			String message = "Exception occured while reading excel file: " + fileName;
			logger.error(message, e);
			throw new ParkarCoreCommonException(message, e);
		}
		ParkarLogger.traceLeave();
		return count;
	}
}