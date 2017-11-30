/*package com.parkar.element;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.parkar.element.interfaces.IBaseElement;
import com.parkar.element.interfaces.complex.IBaseGrid;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.logging.ParkarLogger;
import com.parkar.utils.ParkarLocatorLoaderUtil;
import com.parkar.utils.ParkarSeleniumUtil;


public class BaseUIGrid extends BaseElement implements IBaseGrid {
	
	Logger logger = Logger.getLogger(BaseUIGrid.class);
	
	private static final String rowSelector = "//div[@role=\"row\"]";
	private static final String columnSelector = "//div[@role=\"gridcell\"]";

	private List<String> headerColumns;	
	private WebElement jsElement;

	public BaseUIGrid(WebDriver driver, WebElement element, String locatorKey, String locator, String navigation) {
		super(driver, element, locatorKey, locator, navigation);
		jsElement = element.findElement(By.className("ui-grid-contents-wrapper"));
		loadLocalJS("gridScript.js");
	}
	
	*//**
	 * Get cell by given row index and column index.
	 * 
	 * @param row int
	 * @param column int
	 * @return IBaseElement of cell.
	 * @throws ParkarCoreUIException throw ParkarCoreUIException
	 *//*
	@Override
	public IBaseElement getCell(int row, int column) throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		
		getRowCount();
		String xpath = rowSelector + "[" + row + "]" + columnSelector + "[" + (column + 1) + "]";
		
		ParkarLogger.traceLeave();
		return new BaseElement(driver, this.findElement(By.xpath("." + xpath)), "getCell", locator + xpath,
				navigation);
	}

	*//**
	 * Get cell by given row index and column name.
	 * 
	 * @param row int
	 * @param columnName String
	 * @return IBaseElement of cell.
	 * @throws ParkarCoreUIException throw ParkarCoreUIException
	 *//*
	@Override
	public IBaseElement getCell(int row, String columnName) throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		
		IBaseElement baseElement = null;
		if (isColumnExist(columnName)) {
			baseElement = getCell(row, getColumnNumber(columnName));
		}
		
		ParkarLogger.traceLeave();
		return baseElement;
	}

	*//**
	 * Get row by given row index.
	 * 
	 * @param row int
	 * @return IBaseElement of row.
	 * @throws ParkarCoreUIException throw ParkarCoreUIException
	 *//*
	@Override
	public IBaseElement getRow(int row) throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		
		getRowCount();
		
		ParkarLogger.traceLeave();
		return new BaseElement(driver, this.findElement(By.xpath("." + rowSelector + "[" + row + "]")), "getRow",
				locator + rowSelector + "[" + row + "]", navigation);
	}

	*//**
	 * Get the first row's number which is in given columnNumber and it's value matches given valueToFind
	 * 
	 * @param valueToFind String
	 * @param columnNumber int
	 * @return The first row's number which matches the condition, -1 if not found.
	 * @throws ParkarCoreUIException throw ParkarCoreUIException
	 *//*
	@Override
	public int getRowNumber(String valueToFind, int columnNumber) throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		
		if (!isColumnExist(columnNumber) || valueToFind == null) {
			logger.error("Parameter error, column number: " + columnNumber + ", valueToFind: " + valueToFind);
			throw new ParkarCoreUIException("Parameter error, column number: " + columnNumber + ", valueToFind: " + valueToFind);
		}
		
		int rowCount = getRowCount();
		String cellValue = null;
		for (int i = 1; i <= rowCount; i++) {
			try {
				cellValue = getCellValue(i, columnNumber);
				if (valueToFind.equals(cellValue)) {
					logger.info("Find " + valueToFind + " in column: " + columnNumber + "on row: " + i);
					ParkarLogger.traceLeave();
					return i;
				}
			} catch (Exception e) {
				logger.error("Failed to get cell , row: " + i + ", column name: " + columnNumber, e);
				throw new ParkarCoreUIException("Failed to get cell , row: " + i + ", column name: " + columnNumber, e);
			}
		}
		
		logger.info("Can't find " + valueToFind + " in column: " + columnNumber);
		ParkarLogger.traceLeave();
		return -1;
	}

	*//**
	 * Get the first row's number which is in given columnName and it's value matches given valueToFind
	 * 
	 * @param valueToFind String
	 * @param columnName String
	 * @return The first row's number which matches the condition, -1 if not found.
	 * @throws ParkarCoreUIException throw ParkarCoreUIException
	 *//*
	@Override
	public int getRowNumber(String valueToFind, String columnName) throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		
		if (!isColumnExist(columnName) || valueToFind == null) {
			logger.error("Parameter error, column name: " + columnName + ", valueToFind: " + valueToFind);
			throw new ParkarCoreUIException("Parameter error, column name: " + columnName + ", valueToFind: " + valueToFind);
		}
		int columnNumber = getColumnNumber(columnName);
		
		ParkarLogger.traceLeave();
		return getRowNumber(valueToFind, columnNumber);
	}

	*//**
	 * Get first row of grid.
	 * 
	 * @return IBaseElement of first row.
	 * @throws ParkarCoreUIException throw ParkarCoreUIException
	 *//*
	@Deprecated
	@Override
	public IBaseElement getFirstRow() throws ParkarCoreUIException {
		return getRow(1);
	}

	*//**
	 * Get column index by given column name.
	 * 
	 * @param columnName String
	 * @return Column index by given column name, -1 if not found.
	 * @throws ParkarCoreUIException throw ParkarCoreUIException
	 *//*
	@Override
	public int getColumnNumber(String columnName) throws ParkarCoreUIException {
		List<String> header = getHeader();
		for(int i = 0; i < header.size(); i++) {
			if(header.get(i).equals(columnName)) {
				return i;
			}
		}
		
		return -1;
	}

	*//**
	 * Get column name by given column index.
	 * 
	 * @param column int
	 * @return Column name by given column index.
	 * @throws ParkarCoreUIException throw ParkarCoreUIException
	 *//*
	@Override
	public String getColumnName(int column) throws ParkarCoreUIException {
		List<String> columnNames = getHeader();
		if(!isColumnExist(column)) {
			logger.info("Parameter error, header size is: " + columnNames.size() + ", parameter is: " + column);
			throw new ParkarCoreUIException("Parameter error, header size is: " + columnNames.size() + ", parameter is: " + column);
		}
		
		return columnNames.get(column);
	}

	*//**
	 * Check whether the column is exist.
	 * 
	 * @param columnName String
	 * @return <b>true</b> if column index exist, <b>false</b> if column index not exist.
	 * @throws ParkarCoreUIException throw ParkarCoreUIException
	 *//*
	@Override
	public boolean isColumnExist(String columnName) throws ParkarCoreUIException {
		return getHeader().contains(columnName);
	}

	*//**
	 * Check whether the column is exist.
	 * 
	 * @param column int
	 * @return <b>true</b> if column index exist, <b>false</b> if column index not exist.
	 * @throws ParkarCoreUIException throw ParkarCoreUIException
	 *//*
	@Override
	public boolean isColumnExist(int column) throws ParkarCoreUIException {
		return (column >= 0) && (column < getHeader().size());
	}
	
	
	 * Get the column names of current grid table
	 
	private List<String> getHeader() throws ParkarCoreUIException {
		ParkarLogger.traceEnter();

		if (headerColumns == null) {
			headerColumns = new ArrayList<>();

			String script = "return GetUIGridColumnNames(arguments[0]);";
			Object obj = ParkarSeleniumUtil.executeScript(driver, script, jsElement);
			if (obj != null) {
				String[] columnNameStrs = obj.toString().split("\\|");
				headerColumns.addAll(Arrays.asList(columnNameStrs));
			}
		}

		logger.info("Get Column names: " + headerColumns);
		ParkarLogger.traceLeave();
		return headerColumns;
	}
	
	
	 * Get the cell's string value
	 
	private String getCellValue(int row, int column) throws ParkarCoreUIException {
		String script = "return GetUIGridCellValue(arguments[0], arguments[1], arguments[2]);";
		Object obj = ParkarSeleniumUtil.executeScript(driver, script, jsElement, row, column);
		if (obj == null) {
			return null;
		}
		return String.valueOf(obj.toString());
	}
	
	
	 * Scroll down to last row and get the total row count of grid.
	 
	private int getRowCount() throws ParkarCoreUIException {
		String getVisibleRowCount = "var scope = angular.element(arguments[0]).scope();"
				+ "return scope.uiGrid.data.length;";
		
		String scrollTo = "var scope = angular.element(arguments[0]).scope();"
				+ "scope.grid.scrollTo(scope.uiGrid.data[arguments[1]],scope.uiGrid.columnDefs[arguments[2]]);";
		
		int visibleRowCount = ((Long)ParkarSeleniumUtil.executeScript(driver, getVisibleRowCount, jsElement)).intValue();
		int oldVisibleRowCount = visibleRowCount;
		
		while (true) {
			ParkarSeleniumUtil.executeScript(driver, scrollTo, jsElement, visibleRowCount - 1, 1);
			
			// workround to wait 2 seconds.
			try {
				ParkarSeleniumUtil.visibilityOfElementLocated(driver, ParkarLocatorLoaderUtil.determineByType("//div[@role=\"__element_not_exist__\"]"), 2);
			} catch (Exception e) {
				// nothing to do
			}

			visibleRowCount = ((Long)ParkarSeleniumUtil.executeScript(driver, getVisibleRowCount, jsElement)).intValue();
			
			if(visibleRowCount > oldVisibleRowCount) {
				oldVisibleRowCount = visibleRowCount;
			} else {
				return visibleRowCount;
			}
		}
	}
	
	private void loadLocalJS(String fileName) {
		if (null == fileName || !fileName.endsWith(".js")) {
			return;
		}
		fileName = BaseUIGrid.class.getResource("").getPath() + fileName;
		
		List<String> lines = new ArrayList<String>();
		List<String> funcs = new ArrayList<String>();
		try {
			lines = Files.readAllLines(Paths.get(new File(fileName).getCanonicalPath()), Charset.defaultCharset());
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				if (null != line && !line.contains("//") && line.contains("function")) {
					if (sb.length() > 8) {
						funcs.add(sb.toString());
					}
					sb = new StringBuilder();
					sb.append(line).append("\r\n");
				} else if (null != line && !line.contains("//")) {
					sb.append(line).append("\r\n");
				}
			}
			if (sb.length() > 10) {
				funcs.add(sb.toString());
			}
			for (String function : funcs) {
				String script = "var scriptElt = window.document.createElement('script');"
						+ "scriptElt.type = 'text/javascript';" + "var innerText = " + function + "; "
						+ "scriptElt.innerHTML = innerText;" + "window.document.body.appendChild(scriptElt);";
				ParkarSeleniumUtil.executeScript(driver, script);
			}
		} catch (Exception e) {
			logger.error("loadLocalJS error, javascript file name is: " + fileName, e);
		}
	}
	
}
*/