package com.parkar.helpers;

import io.appium.java_client.AppiumDriver;

import java.io.File;

import org.openqa.selenium.WebElement;
import org.testng.log4testng.Logger;

import com.parkar.exception.ParkarCoreMobileException;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.CoreReporter;
import com.parkar.report.ParkarReporter;
import com.parkar.report.StepStatus;
import com.parkar.testng.Configurator;
import com.parkar.utils.ParkarLocatorLoaderUtil;
import com.parkar.utils.ParkarSeleniumUtil;

public class BasicPageSyncHelper {
	private static CoreReporter reporter = ParkarReporter.getInstance();
	final static Logger logger = Logger.getLogger(BasicPageSyncHelper.class);
	final static int timeout = Integer.parseInt(Configurator.getInstance().getParameter("timeout"));
	/**
	 * Capture screenshot as a file.
	 * 
	 * @param driver
	 *            WebDriver
	 * @return File. screenshot file
	 */
	public static File saveAsScreenShot(AppiumDriver<?> driver) {
		ParkarLogger.traceEnter();
		File file = null;
		String infoMsg = "saveAsScreenShot: ";
		try {
			file = ParkarSeleniumUtil.takeScreenShot(driver);
			logger.info(infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.reportStep(StepStatus.FAIL, infoMsg, e);
		}
		ParkarLogger.traceLeave();
		return file;
	}

	/**
	 * Wait for frame until it is present in the DOM and switch to it
	 * 
	 * @param driver
	 *            WebDriver driver
	 * @param locator
	 *            locator defined in the properties file specific to the current
	 *            page for the the driver to find the element based on
	 * @throws ParkarCoreMobileException
	 *             throws a ParkarCoreUIException
	 */
	public static void waitForFrameToLoadAndSwitch(AppiumDriver<?> driver,String locator) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "waitForFrameToLoadAndSwitch: " + "[ " + locator
				+ " ]";
		try {
			logger.info(infoMsg);
			ParkarSeleniumUtil.frameToBeAvailableAndSwitchToIt(driver,
					ParkarLocatorLoaderUtil.determineByType(locator),
					timeout);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg,
					saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Wait for page to be loaded based on the HTML document.readyState
	 * attributes
	 * 
	 * @param driver
	 *            SeleniumWebDriver instance
	 * @throws ParkarCoreMobileException
	 *             throws a ParkarCoreUIException
	 */
	public static void waitForPageToLoad(AppiumDriver<?> driver)
			throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		ParkarSeleniumUtil.waitForDocumentStateToBeComplete(driver);
		ParkarLogger.traceLeave();
	}

	/**
	 * Wait until the visibility of given element locator is located on the
	 * page.
	 * 
	 * @param driver
	 *            SeleniumWebDriver instance
	 * @param locator
	 *            locator defined in the properties file specific to the current
	 *            page for the the driver to find the element based on
	 * @param timeout
	 *            wait in seconds
	 * @throws ParkarCoreMobileException
	 *             throws a ParkarCoreUIException
	 */
	public static void waitForApplicationToLoad(AppiumDriver<?> driver, String locator, int timeout)
			throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "waitForApplicationToLoad: " + "[ " + locator + " ]";
		try {
			logger.info(infoMsg);
			ParkarSeleniumUtil.visibilityOfElementLocated(driver,
					ParkarLocatorLoaderUtil.determineByType(locator),
					timeout);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg,
					saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Wait until the the given locator element goes invisible on the page.
	 * 
	 * @param driver
	 *            SeleniumWebDriver instance
	 * @param locator
	 *            locator defined in the properties file specific to the current
	 *            page for the the driver to find the element based on
	 * @param timeout
	 *            wait in seconds
	 * @throws ParkarCoreMobileException
	 *             throws a ParkarCoreUIException
	 * 
	 */
	public static void waitForContentToLoad(AppiumDriver<?> driver, String locator, int timeout)
			throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "waitForContentToLoad: " + "[ " + locator + " ]";
		try {
			logger.info(infoMsg);
			ParkarSeleniumUtil.waitUntilElementIsInvisible(driver,
					ParkarLocatorLoaderUtil.determineByType(locator),
					timeout);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg,
					saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Wait until the visibility of given pop-up locator is located.
	 * 
	 * @param driver
	 *            selenium webdriver
	 * @param locator
	 *            locator defined in the properties file specific to the current
	 *            page for the the driver to find the element based on
	 * @param timeout
	 *            wait in seconds
	 * @throws ParkarCoreMobileException
	 *             throws a ParkarCoreUIException
	 */
	public static void waitForPopupToLoad(AppiumDriver<?> driver, String locator,int timeout)
			throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "waitForPopupToLoad: " + "[ " + locator + " ]";
		try {
			logger.info(infoMsg);
			ParkarSeleniumUtil.visibilityOfElementLocated(driver,
					ParkarLocatorLoaderUtil.determineByType(locator),
					timeout);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg,
					saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}
	
	/**
	 * Wait until the enable of given element. 
	 * @param driver
	 *            selenium webdriver
	 * @param element
	 *            Predefined web element for the enable to check upon
	 * @param timeout
	 *            wait in seconds
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public static void waitForElementToEnable(AppiumDriver<?> driver, WebElement element, int timeout) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "waitForElementToEnable: [" + element.toString() + "]";
		try {
			logger.info(infoMsg);
			ParkarSeleniumUtil.waitForElementToBeEnabled(driver, element, timeout);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}
	
	
	/**
	 * Wait until the disable of given element. 
	 * @param driver
	 *            selenium webdriver
	 * @param element
	 *            Predefined web element for the enable to check upon
	 * @param timeout
	 *            wait in seconds
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public static void waitForElementToDisable(AppiumDriver<?> driver, WebElement element, int timeout) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "waitForElementToDisable: [" + element.toString() + "]";
		try {
			logger.info(infoMsg);
			ParkarSeleniumUtil.waitForElementToBeDisabled(driver, element, timeout);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}
	
	
	/**
	 * Wait until the display of given element. 
	 * @param driver
	 *            selenium webdriver
	 * @param element
	 *            Predefined web element for the enable to check upon
	 * @param timeout
	 *            wait in seconds
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public static void waitForElementToDisplay(AppiumDriver<?> driver, WebElement element, int timeout) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "waitForElementToDisplay: [" + element.toString() + "]";
		try {
			logger.info(infoMsg);
			ParkarSeleniumUtil.waitForElementToBeDisplayed(driver, element, timeout);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}
	
	
	/**
	 * Wait until the display of given element. 
	 * @param driver
	 *            selenium webdriver
	 * @param element
	 *            Predefined web element for the enable to check upon
	 * @param timeout
	 *            wait in seconds
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public static void waitForElementToDisappear(AppiumDriver<?> driver, WebElement element, int timeout) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "waitForElementToDisppear: [" + element.toString() + "]";
		try {
			logger.info(infoMsg);
			ParkarSeleniumUtil.waitForElementToBeDisappear(driver, element, timeout);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}
}