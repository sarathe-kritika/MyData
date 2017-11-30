package com.parkar.utils.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.parkar.dateutils.DateParser;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;

public class CSVReader {

	final static Logger logger = Logger.getLogger(CSVReader.class);

	private static Map<String, String[]> headerMap = new HashMap<>();

	private static final String DateTypeStartWith = "{{{";
	private static final String DateTypeEndWith = "}}}";

	private static final DateParser dateParser = new DateParser();

	private static final String isDateTypePattern = "(\\{{3})(.*)(\\}{3})";

	/**
	 * Read the whole content of given csv file
	 * 
	 * @param fileName:
	 *            String
	 * @return List: The whole content of given csv file
	 * @throws ParkarCoreCommonException
	 *             : customized Parkar core common exception
	 */
	public static List<Map<String, String>> readCSV(String fileName) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		String absoluteFilePath = FileUtils.getAbsolutePath(fileName);
		int csvRowCount = 0;
		try {
			// total row count without header
			csvRowCount = (int) Files.lines(Paths.get(absoluteFilePath)).count() - 1;
		} catch (Exception e) {
			String message = "Exception occured when read csv file: " + absoluteFilePath;
			logger.error(message, e);
			throw new ParkarCoreCommonException(message, e);
		}
		ParkarLogger.traceLeave();
		return readCSV(fileName, 1, csvRowCount);
	}

	/**
	 * Check if it is a date pattern
	 * 
	 * @param columnValue
	 *            : String
	 * @return boolean : true or false
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
	 * Replace two vertical line with comma.
	 * 
	 * @param value
	 *            String
	 * @return String
	 */
	private static String replaceTwoVerticalLineToComma(String value) {
		ParkarLogger.traceEnter();
		ParkarLogger.traceLeave();
		return value.replaceAll("\\|\\|", ",");
	}

	/**
	 * Read the line content from startRow to endRow
	 * 
	 * @param fileName:
	 *            String
	 * @param startRow:
	 *            int
	 * @param endRow:
	 *            int
	 * @return The line content from startRow to endRow
	 * @throws ParkarCoreCommonException
	 *             : customized Parkar core common exception
	 */
	public static List<Map<String, String>> readCSV(String fileName, int startRow, int endRow)
			throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		List<Map<String, String>> content = new ArrayList<>();

		if (startRow < 0 || endRow < 0 || (startRow > endRow)) {
			String message = "Parameter error, startRow is: " + startRow + ", endRow is:" + endRow;
			logger.error(message);
			throw new ParkarCoreCommonException(message);
		}

		for (int i = startRow; i <= endRow; i++) {
			content.add(readCSV(fileName, i));
		}
		ParkarLogger.traceLeave();
		return content;
	}

	/**
	 * Read the single line content of given csv file
	 * 
	 * @param fileName:
	 *            String
	 * @param row:
	 *            row number of csv file, start from 1
	 * @return Map which its keyset contains headers and valueset contains given
	 *         row's values.
	 * @throws ParkarCoreCommonException
	 *             : customized Parkar core common exception
	 */
	public static Map<String, String> readCSV(String fileName, int row) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		Map<String, String> lineMap = null;
		String line = null;

		if (row < 0) {
			String message = "Row can not be negative, row: " + row;
			logger.error(message);
			throw new ParkarCoreCommonException(message);
		}

		fileName = FileUtils.getAbsolutePath(fileName);

		String[] header = getHeader(fileName);
		try (Stream<String> lines = Files.lines(Paths.get(fileName))) {

			line = lines.skip(row).findFirst().get();
			if (line == null) {
				String message = "Row: " + row + " does not exist in csv file: " + fileName;
				logger.error(message);
				throw new ParkarCoreCommonException(message);
			}

			String[] columns = line.split(",", -1);
			if (header.length != columns.length) {
				String message = "CSV file: " + fileName + " is not valid, header column equals " + header.length
						+ ", row column equals " + columns.length + " on row: " + row;
				logger.error(message);
				throw new ParkarCoreCommonException(message);
			}

			lineMap = new LinkedHashMap<>();
			for (int i = 0; i < header.length; i++) {
				String value = replaceTwoVerticalLineToComma(columns[i]);
				if (isDatePattern(value)) {
					lineMap.put(header[i], dateParser.parseDatePattern(getInnnerDateExpresssion(value)));
				} else {
					lineMap.put(header[i], value);
				}
			}
		} catch (Exception e) {
			String message = "Exception occured when read csv file: " + fileName + ", row: " + row;
			logger.error(message, e);
			throw new ParkarCoreCommonException(message, e);
		}
		ParkarLogger.traceLeave();
		return lineMap;
	}

	/**
	 * Read single line map of csv file by given id value.
	 * 
	 * @param fileName
	 *            csv file name.
	 * @param idValue
	 *            value of first column.
	 * @return Map of the row content, <b>null</b> if not found.
	 * @throws ParkarCoreCommonException
	 *             : customized Parkar core common exception
	 */
	public static Map<String, String> readCSV(String fileName, String idValue) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		if (fileName == null || idValue == null) {
			logger.info("Parameter error, fileName is: " + fileName + ", idValue: " + idValue);
			throw new ParkarCoreCommonException("Parameter error, fileName is: " + fileName + ", idValue: " + idValue);
		}

		List<Map<String, String>> rows = readCSV(fileName);
		if (rows.size() == 0) {
			logger.info("CSV file's content is empty, fileName is: " + fileName);
			throw new ParkarCoreCommonException("CSV file's content is empty, fileName is: " + fileName);
		}

		String firstColumnName = rows.get(0).keySet().iterator().next();
		Map<String, String> rowFound = null;
		for (Map<String, String> row : rows) {
			if (idValue.equals(row.get(firstColumnName))) {
				rowFound = row;
				break;
			}
		}
		ParkarLogger.traceLeave();
		return rowFound;
	}

	/**
	 * Get the header (first line) of a csv file
	 * 
	 * @param fileName:
	 *            String
	 * @return String array split header by comma
	 * @throws ParkarCoreCommonException
	 *             : customized Parkar core common exception
	 */
	protected static String[] getHeader(String fileName) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		if (headerMap.containsKey(fileName)) {
			return headerMap.get(fileName);
		}

		try (BufferedReader csvReader = new BufferedReader(new FileReader(fileName))) {

			String line = csvReader.readLine();
			if (line == null) {
				String message = "The first line of given csv file: " + fileName + " is empty.";
				logger.error(message);
				throw new ParkarCoreCommonException(message);
			}
			headerMap.put(fileName, line.split(",", -1));
		} catch (FileNotFoundException e) {
			String message = "File not found for given path: " + fileName;
			logger.error(message, e);
			throw new ParkarCoreCommonException(message, e);
		} catch (IOException e) {
			String message = "IOException occured when reading the file: " + fileName;
			logger.error(message, e);
			throw new ParkarCoreCommonException(message, e);
		}
		ParkarLogger.traceLeave();
		return headerMap.get(fileName);
	}

}
