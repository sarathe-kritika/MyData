/*package com.parkar.element;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.parkar.element.interfaces.IBaseElement;
import com.parkar.element.interfaces.complex.IBaseGrid;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.logging.ParkarLogger;

public class BaseJQGrid extends BaseElement implements IBaseGrid {
	Logger logger = Logger.getLogger(BaseJQGrid.class);
	
	private static final String rowSelector = "//div[@role=\"row\"]";
	private static final String headerSelector = "//div[@role=\"columnheader\"]";
	private static final String columnSelector = "//div[@role=\"gridcell\"]";

	private List<String> headerColumns;

	public BaseJQGrid(WebDriver driver, WebElement element, String locatorKey, String locator, String navigation) {
		super(driver, element, locatorKey, locator, navigation);
	}

	*//**
	 * Get cell by given row index and column index.
	 * 
	 * @param row int
	 * @param column int
	 * @return IBaseElement
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 *//*
	@Override
	public IBaseElement getCell(int row, int column) throws ParkarCoreUIException {
		String xpath = rowSelector + "[" + row + "]" + columnSelector + "[" + (column + 1) + "]";
		return new BaseElement(driver, this.findElement(By.xpath("." + xpath)), "getCell", locator + xpath,
				navigation);
	}

	*//**
	 * Get cell by given row index and column name.
	 * 
	 * @param row int
	 * @param columnName String
	 * @return IBaseElement
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 *//*
	@Override
	public IBaseElement getCell(int row, String columnName) throws ParkarCoreUIException {
		List<String> header = getHeader();
		if (header.contains(columnName)) {
			return getCell(row, getColumnNumber(columnName));
		} else {
			return null;
		}
	}

	*//**
	 * Get row by given row index.
	 * 
	 * @param row int
	 * @return IBaseElement
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 *//*
	@Override
	public IBaseElement getRow(int row) throws ParkarCoreUIException {
		return new BaseElement(driver, this.findElement(By.xpath("." + rowSelector + "[" + row + "]")), "getRow",
				locator + rowSelector + "[" + row + "]", navigation);
	}

	*//**
	 * Get the first row's number which is in given columnNumber and it's value matches given valueToFind
	 * 
	 * @param valueToFind String
	 * @param columnNumber int
	 * @return The first row's number which matches the condition, -1 if not found.
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 *//*
	@Override
	public int getRowNumber(String valueToFind, int columnNumber) throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		
		if (!isColumnExist(columnNumber) || valueToFind == null) {
			logger.error("Parameter error, column number: " + columnNumber + ", valueToFind: " + valueToFind);
			throw new ParkarCoreUIException("Parameter error, column number: " + columnNumber + ", valueToFind: " + valueToFind);
		}
		
		IBaseElement baseElement = null;
		for (int i = 1; i <= getRowCount(); i++) {
			try {
				baseElement = getCell(i, columnNumber);
				if (valueToFind.equals(baseElement.getText())) {
					logger.info("Find " + valueToFind + " in column: " + columnNumber + "on row: " + i);
					return i;
				}
			} catch (Exception e) {
				logger.error("Exception occured when get cell, row: " + i + ", column name: " + columnNumber + " " + e.getMessage());
				throw new ParkarCoreUIException("Exception occured when get cell, row: " + i + ", column name: " + columnNumber, e);
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
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 *//*
	@Override
	public int getRowNumber(String valueToFind, String columnName) throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		
		if (!isColumnExist(columnName) || valueToFind == null) {
			logger.error("Parameter error, column name: " + columnName + ", valueToFind: " + valueToFind);
			throw new ParkarCoreUIException("Parameter error, column name: " + columnName + ", valueToFind: " + valueToFind);
		}
		
		IBaseElement baseElement = null;
		for (int i = 1; i <= getRowCount(); i++) {
			try {
				baseElement = getCell(i, columnName);
				if (valueToFind.equals(baseElement.getText())) {
					logger.info("Find " + valueToFind + " in column: " + columnName + "on row: " + i);
					return i;
				}
			} catch (Exception e) {
				logger.error("Exception occured when get cell, row: " + i + ", column name: " + columnName + " " + e.getMessage());
				throw new ParkarCoreUIException("Exception occured when get cell, row: " + i + ", column name: " + columnName, e);
			}
		}
		
		logger.info("Can't find " + valueToFind + " in column: " + columnName);
		ParkarLogger.traceLeave();
		return -1;
	}

	*//**
	 * Get first row of grid.
	 * 
	 * @return IBaseElement of first row.
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 *//*
	@Override
	public IBaseElement getFirstRow() throws ParkarCoreUIException {
		return new BaseElement(driver, this.findElement(By.xpath("." + rowSelector + "[1]")), "getRowAt",
				locator + rowSelector + "[1]", navigation);
	}

	*//**
	 * Get column index by given column name.
	 * 
	 * @param columnName String
	 * @return Column index by given column name, -1 if not found.
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
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
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 *//*
	@Override
	public String getColumnName(int column) throws ParkarCoreUIException {
		List<String> columnNames = getHeader();
		if(column < 0 || column >= columnNames.size()) {
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
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
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
	 * @throws ParkarCoreUIException throw a ParkarCoreUIException
	 *//*
	@Override
	public boolean isColumnExist(int column) throws ParkarCoreUIException {
		return (column >= 0) && (column < getHeader().size());
	}

	*//**
	 * Get the column names of current grid table
	 * 
	 * @return The column names list of grid
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 *//*
	private List<String> getHeader() throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		
		if (headerColumns == null) {
			headerColumns = new ArrayList<>();
			List<IBaseElement> headersWebElement = this.findElements(By.xpath("." + headerSelector));

			for (IBaseElement element : headersWebElement) {
				try {
					String headerName = element.getText().trim();
					headerColumns.add(headerName);
				} catch (Exception e) {
					logger.error("Get column names of the grid table fail " + e);
					throw new ParkarCoreUIException("Get column names of the grid table fail " + e.getMessage());
				}
			}
		}

		logger.info("Get Column names: " + headerColumns);
		ParkarLogger.traceLeave();
		return headerColumns;
	}
	
	*//**
	 * Get row count of all visible rows.
	 * @return int row count
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 *//*
	private int getRowCount() throws ParkarCoreUIException {
		return this.findElements(By.xpath("." + rowSelector)).size();
	}


}
*/