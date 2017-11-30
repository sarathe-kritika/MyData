package com.parkar.helpers;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.parkar.exception.ParkarCoreUIException;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.CoreReporter;
import com.parkar.report.ParkarReporter;
import com.parkar.report.StepStatus;
import com.parkar.utils.ParkarSeleniumUtil;

public class BasicPageElementHelper {

	private static CoreReporter reporter = ParkarReporter.getInstance();
	final static Logger logger = Logger.getLogger(BasicPageElementHelper.class);
	/**
	 * Scroll to the element. Brings element into view
	 *   
	 * @param driver WebDriver
	 * @param elt WebElement
	 */
	public static void scrollIntoView(WebDriver driver, WebElement elt) {
		ParkarLogger.traceEnter();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elt);
		ParkarLogger.traceLeave();
	}

	/**
	 * Find element based on if the element is clickable
	 * 
	 * @param driver
	 *            WebDriver instance
	 * @param locator
	 *            locator defined in the properties file specific to the current
	 *            page for the the driver to find the element based on
	 * @param timeout int
	 * @return WebElement : object of clickable element
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 */
	public static WebElement findElementByClickable(WebDriver driver, By locator, int timeout)
			throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		WebElement webElement = null;
		String infoMsg = "findElementByClickable: " + "[ " + locator + " ]" + " with timeout: " + timeout;
		try {
			webElement = ParkarSeleniumUtil.elementToBeClickable(driver, locator, timeout);
			logger.info(infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg , BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreUIException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
		return webElement;
	}

	/**
	 * Find element based on if the element is visible
	 * 
	 * @param driver
	 *            WebDriver instance
	 * @param locator
	 *            locator defined in the properties file specific to the current
	 *            page for the the driver to find the element based on
	 * @param timeout int
	 * @return WebElement : Object of visible element
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 */
	public static WebElement findElementByVisible(WebDriver driver, By locator, int timeout)
			throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		WebElement webElement = null;
		String infoMsg = "findElementByVisibile: " + "[ " + locator + " ]" + " with timeout: " + timeout;
		try {
			webElement = ParkarSeleniumUtil.visibilityOfElementLocated(driver, locator, timeout);
			logger.info(infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg , BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreUIException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
		return webElement;

	}

	/**
	 * Find element based on if the element is invisible
	 * 
	 * @param driver
	 *            WebDriver instance
	 * @param locator
	 *            locator defined in the properties file specific to the current
	 *            page for the the driver to find the element based on
	 * @param timeout int
	 * @return WebElement
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 */
	public static WebElement findElementByInvisible(WebDriver driver, By locator, int timeout)
			throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		WebElement webElement = null;
		String infoMsg = "findElementByInvisibile: " + "[ " + locator + " ]" + " with timeout: " + timeout;
		try {
			ParkarSeleniumUtil.waitUntilElementIsInvisible(driver, locator, timeout);
			logger.info(infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreUIException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
		return webElement;
	}

	/**
	 * Find element based on if the element is present
	 * 
	 * @param driver
	 *            WebDriver instance
	 * @param locator
	 *            locator defined in the properties file specific to the current
	 *            page for the the driver to find the element based on
	 * @param timeout int
	 * @return WebElement : object of present element
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 */
	public static WebElement findElementByPresence(WebDriver driver, By locator, int timeout)
			throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		WebElement webElement = null;
		String infoMsg = "findElementByPresence: " + "[ " + locator + " ]" + " with timeout: " + timeout;
		try {
			webElement = ParkarSeleniumUtil.presenceOfElementLocated(driver, locator, timeout);
			logger.info(infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreUIException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
		return webElement;
	}

	/**
	 * Find element based on if the element is NOT present
	 * 
	 * @param driver
	 *            WebDriver instance
	 * @param locator
	 *            locator defined in the properties file specific to the current
	 *            page for the the driver to find the element based on
	 * @param timeout int
	 * @return WebElement : object of element which are not present
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 */
	public static WebElement findElementByNotPresence(WebDriver driver, By locator, int timeout)
			throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		// We don't want the timeout to be too long for not presence
		timeout = 5;
		WebElement webElement = null;
		String infoMsg = "findElementByNotPresence: " + "[ " + locator + " ]" + " with timeout: " + timeout;
		try {
			webElement = ParkarSeleniumUtil.presenceOfElementLocated(driver, locator, timeout);
			logger.info(infoMsg);
			if (webElement != null) {
				reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver));
			}
		} catch (Exception e) {
			// Not action required element should not be there on page.
		}
		ParkarLogger.traceLeave();
		return webElement;
	}

	/*
	 * Multiple elements location
	 */
	/**
	 * Find all elements based on if the element is present
	 * 
	 * @param driver
	 *            WebDriver instance
	 * @param locator
	 *            locator defined in the properties file specific to the current
	 *            page for the the page.driver to find the element based on
	 * @param timeout int           
	 * @return List. list of WebElement by presence
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 */
	public static List<WebElement> findElementsByPresence(WebDriver driver, By locator, int timeout)
			throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		List<WebElement> webElements = null;
		String infoMsg = "findElementsByPresence: " + "[ " + locator + " ]" + " with timeout: " + timeout;
		try {
			webElements = ParkarSeleniumUtil.presenceOfAllElementsLocatedBy(driver, locator, timeout);
			logger.info(infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreUIException(infoMsg, e);
		}

		ParkarLogger.traceLeave();
		return webElements;
	}
	/*
	 * Actions utils functions
	 */

	/**
	 * 
	 * @param driver
	 *            WebDriver instance
	 * @param elt WebElement
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 */
	public static void clickInvisible(WebDriver driver, WebElement elt) throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		String infoMsg = "clickInvisible: " + "[ " + elt + " ]";
		try {
			ParkarSeleniumUtil.clickOnInvisible(driver, elt);
			logger.info(infoMsg);
			ParkarLogger.traceLeave();
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreUIException(infoMsg, e);
		}
	}

	/**
	 * Get selected web elements count
	 * 
	 * @param driver WebDriver
	 * @param xpath String
	 * @param timeout int
	 * @return int : object of selected web elements count
	 * @throws ParkarCoreUIException throws a  ParkarCoreUIException
	 */
	public static int getElementCountByXpath(WebDriver driver, String xpath, int timeout) throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		int count = 0;
		String infoMsg = "getElementCountByXpath: " + "[ " + xpath + " ]" + " with timeout: " + timeout;
		try {
			count = ParkarSeleniumUtil.presenceOfAllElementsLocatedBy(driver, By.xpath(xpath), timeout).size();
			logger.info(infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreUIException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
		return count;
	}
	
	/**
	 * Execute the javascript code using JavascriptExecutor with given parameters
	 * 
	 * @param driver WebDriver
	 * @param javascriptToExecute String
	 * @param parameters Object...
	 * @return Return value of executed javascript code with given parameters.
	 * @throws ParkarCoreUIException :ParkarCoreUIException
	 */
	public static Object executeScript(WebDriver driver, String javascriptToExecute, Object... parameters)
			throws ParkarCoreUIException {
		ParkarLogger.traceEnter();
		Object object = null;
		String infoMsg = "executeScript: " + javascriptToExecute + " with parameter: " + parameters;
		try {
			object = ParkarSeleniumUtil.executeScript(driver, javascriptToExecute, parameters);
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreUIException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
		return object;
	}

	/**
	 * Perform drag and drop from source element to target element
	 * @param driver : WebDriver
	 * @param source : WebElement
	 * @param target : WebElement
	 * @throws ParkarCoreUIException : throws customized Parkar exception
	 */
	public static void dragAndDrop(WebDriver driver, WebElement source, WebElement target) throws ParkarCoreUIException{
		ParkarLogger.traceEnter();
		String infoMsg = "Drag and Drop: element";
		try {
			Actions builder = new Actions(driver);
			builder.dragAndDrop(source, target).build().perform();
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreUIException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}
}
