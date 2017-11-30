package com.parkar.helpers;

import io.appium.java_client.AppiumDriver;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;

import com.parkar.exception.ParkarCoreMobileException;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.CoreReporter;
import com.parkar.report.ParkarReporter;
import com.parkar.report.StepStatus;

public class BasicBrowserHelper {

	final static Logger logger = Logger.getLogger(BasicPageSyncHelper.class);
	private static CoreReporter reporter = ParkarReporter.getInstance();

	/**
	 * Navigate to target URL
	 * 
	 * @param Url String
	 * @param driver WebDriver
	 */
	public static void navigate(String Url, AppiumDriver<?> driver) {
		ParkarLogger.traceEnter();
		driver.navigate().to(Url);
		String infoMsg = "navigate: " + Url;
		logger.info(infoMsg);
		reporter.deepReportStep(StepStatus.INFO, infoMsg);
		ParkarLogger.traceLeave();
	}

	/**
	 * Navigate back from current URL
	 * 
	 * @param driver WebDriver
	 */
	public static void navigateBack(AppiumDriver<?> driver) {
		ParkarLogger.traceEnter();
		driver.navigate().back();
		String infoMsg = "navigateBack: " + driver.getCurrentUrl();
		logger.info(infoMsg);
		reporter.deepReportStep(StepStatus.INFO, infoMsg);
		ParkarLogger.traceLeave();
	}

	/**
	 * Navigate forward from current URL
	 * 
	 * @param driver WebDriver
	 */
	public static void navigateForward(AppiumDriver<?> driver) {
		ParkarLogger.traceEnter();
		driver.navigate().forward();
		String infoMsg = "navigateForward: " + driver.getCurrentUrl();
		logger.info(infoMsg);
		reporter.deepReportStep(StepStatus.INFO, infoMsg);
		ParkarLogger.traceLeave();
	}
	/**
	 * Refresh current URL
	 * 
	 * @param driver WebDriver
	 */
	public static void refreshPage(AppiumDriver<?> driver) {
		ParkarLogger.traceEnter();
		driver.navigate().refresh();
		String infoMsg = "refreshPage: " + driver.getCurrentUrl();
		logger.info(infoMsg);
		reporter.deepReportStep(StepStatus.INFO, infoMsg);
		ParkarLogger.traceLeave();
	}
	/**
	 * Find cookie's value corresponding to given cookie name
	 * 
	 * @param driver WebDriver
	 * @param cookieName String
	 * @return Cookie's value corresponding to given cookie name, <br>
	 *         null<br>
	 *         if cookie with given cookie name is not present
	 */
	public static String getCookieValueByName(AppiumDriver<?> driver, String cookieName) {
		ParkarLogger.traceEnter();
		String cookieValue = null;
		cookieValue = driver.manage().getCookieNamed(cookieName).getValue();
		String infoMsg = "getCookieValueByName: " + cookieName;
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return cookieValue;
	}

	/**
	 * Store current window handle
	 * 
	 * @param driver WebDriver
	 * @return String
	 */
	public static String getCurrentWindowHandle(AppiumDriver<?> driver) {
		ParkarLogger.traceEnter();
		String winHandle = driver.getWindowHandle();
		String infoMsg = "getCurrentWindowHandle: " + winHandle;
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return winHandle;
	}

	/**
	 * Switch to new opened window
	 * 
	 * @param driver WebDriver
	 * @param parentWindowHandle String
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	public static void switchingToNewWindowOpened(AppiumDriver<?> driver, String parentWindowHandle)
			throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		Set<String> winHandles = driver.getWindowHandles();
		String infoMsg = "switchingToNewWindowOpened: ";
		if (StringUtils.isBlank(parentWindowHandle) || winHandles.size() < 2) {
			infoMsg = infoMsg + "Parent window handle is null or there is only one window.";
			logger.error(infoMsg);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg);
			throw new ParkarCoreMobileException(infoMsg);
		}
		for (String handle : winHandles) {
			if (!StringUtils.equals(parentWindowHandle, handle)) {
				switchToWindow(driver, handle);
				logger.info(infoMsg + handle);
				break;
			}
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Closing new opened window and switch back to original window
	 * 
	 * @param driver WebDriver
	 * @param closingWindowHandle String
	 * @param switchToWindowHandle String
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	public static void closingNewWindowAndSwitchBack(AppiumDriver<?> driver, String closingWindowHandle,
			String switchToWindowHandle) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "closingNewWindowAndSwitchBack: ";

		if (StringUtils.isBlank(switchToWindowHandle) || StringUtils.isBlank(closingWindowHandle)) {
			infoMsg = infoMsg
					+ " closingWindowHandle or switchToWindowHandle can't be null or blank. closingWindowHandle: "
					+ closingWindowHandle + ", switchToWindowHandle: " + switchToWindowHandle;
			logger.error(infoMsg);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg);
			throw new ParkarCoreMobileException(infoMsg);
		}
		switchToWindow(driver, closingWindowHandle);
		driver.close();
		switchToWindow(driver, switchToWindowHandle);
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
	}

	/**
	 * Switch to window with given window handle
	 * 
	 * @param driver WebDriver
	 * @param windowHanle String
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	public static void switchToWindow(AppiumDriver<?> driver, String windowHanle) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "switchToWindow: " + windowHanle;
		try {
			driver.switchTo().window(windowHanle);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
	}

	/**
	 * Get alert object
	 * 
	 * @param driver WebDriver
	 * @return Alert
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	public static Alert getAlert(AppiumDriver<?> driver) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "getAlert: ";
		try {
			Alert alert = driver.switchTo().alert();
			logger.info(infoMsg);
			ParkarLogger.traceLeave();
			return alert;
		} catch (Exception e) {
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			logger.error(infoMsg, e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}

	}


	/**
	 * Accept alert handle
	 * 
	 * @param driver WebDriver
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	public static void acceptAlert(AppiumDriver<?> driver) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "getAlert: ";
		try {
			Alert alert = getAlert(driver);
			alert.accept();
			logger.info(infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Dismiss alert handle
	 * 
	 * @param driver WebDriver
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	public static void dismissAlert(AppiumDriver<?> driver) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "dismissAlert:";
		try {
			Alert alert = getAlert(driver);
			alert.dismiss();
			logger.info(infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}
	/**
	 * Get alert text
	 * 
	 * @param driver WebDriver
	 * @return String
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	public static String getAlertText(AppiumDriver<?> driver) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String alertText = null;
		String infoMsg = "getAlertText: %s";
		try {
			Alert alert = getAlert(driver);
			alertText = alert.getText();
			infoMsg = String.format(infoMsg, alertText);
			logger.info(infoMsg);
		} catch (Exception e) {
			infoMsg = String.format(infoMsg, alertText);
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
		return alertText;
	}

	/**
	 * Get Page title
	 * 
	 * @param driver WebDriver
	 * @return String
	 */
	public static String getTitle(AppiumDriver<?> driver) {
		String title = null;
		ParkarLogger.traceEnter();
		title = driver.getTitle();
		String infoMsg = "getTitle: " + title;
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return title;

	}

	/**
	 * Get Page url
	 * 
	 * @param driver WebDriver
	 * @return String
	 */
	public static String getPageUrl(AppiumDriver<?> driver) {
		String title = null;
		ParkarLogger.traceEnter();
		title = driver.getCurrentUrl();
		String infoMsg = "getPageUrl: " + title;
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return title;

	}

}
