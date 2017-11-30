package com.parkar.assertion;

import io.appium.java_client.AppiumDriver;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.parkar.helpers.BasicPageSyncHelper;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.ParkarReporter;
import com.parkar.report.StepStatus;

public class MobileAssertion extends ParkarSoftAssertion{

	final static Logger logger = Logger.getLogger(MobileAssertion.class);
	private ParkarReporter reporter = ParkarReporter.getInstance();
	private AppiumDriver<?> appiumDriver = null;
	
	/**
	 * Constructs a new SoftAssertion with the webDriver.
	 * 
	 * @param webDriver:WebDriver
	 */
	public MobileAssertion(AppiumDriver<?> appiumDriver){
		this.appiumDriver =appiumDriver;
	}
	/**
	 * Asserts that a condition if true. 
	 * 
	 * @param condition:boolean
	 * @return boolean
	 */
	public boolean verifyTrue(final boolean condition) {
		return verifyTrue(condition, "");
	}
	/**
	 * Asserts that a condition is true. 
	 * @param condition:boolean
	 * @param stepDetail:String
	 * @return boolean
	 */
	public boolean verifyTrue(final boolean condition, final String stepDetail) {
		ParkarLogger.traceEnter();
		boolean result = false;
		String message = "verifyTrue: " + stepDetail + BREAK_LINE + " A: " + condition;
		try {
			Assert.assertTrue(condition);
			logger.info(message);
			reporter.reportStep(StepStatus.PASS, message);
			result = true;
		} catch (AssertionError ae) {
			verificationFailures.add(ae);
			logger.error(message);
			reporter.reportStep(StepStatus.FAIL, message , BasicPageSyncHelper.saveAsScreenShot(appiumDriver));
		}
		ParkarLogger.traceLeave();
		return result;
	}
	/**
	 * Asserts that a condition is false. 
	 * 
	 * @param condition:boolean
	 * @return boolean
	 */
	public boolean verifyFalse(final boolean condition) {
		return verifyFalse(condition, "");
	}
	/**
	 * Asserts that a condition is false. 
	 * 
	 * @param condition:boolean
	 * @param stepDetail:String
	 * @return boolean
	 */
	public boolean verifyFalse(final boolean condition, final String stepDetail) {
		ParkarLogger.traceEnter();
		boolean result = false;
		String message = "verifyFalse: " + stepDetail + BREAK_LINE + " A: " + condition;
		try {
			Assert.assertFalse(condition);
			logger.info(message);
			reporter.reportStep(StepStatus.PASS, message);
			result =true;
		} catch (AssertionError ae) {
			verificationFailures.add(ae);
			logger.error(message);
			reporter.reportStep(StepStatus.FAIL, message , BasicPageSyncHelper.saveAsScreenShot(appiumDriver));
		}
		ParkarLogger.traceLeave();
		return result;
	}
	/**
	 * Asserts that two objects are equal. 
	 * 
	 * @param actual:Object
	 * @param expected:Object
	 * @return boolean
	 */
	public boolean verifyEquals(final Object actual, final Object expected) {
		return verifyEquals(actual, expected, "");
	}
	/**
	 * Asserts that two objects are equal. 
	 * 
	 * @param actual:Object
	 * @param expected:Object
	 * @param stepDetail:String
	 * @return boolean
	 */
	public boolean verifyEquals(final Object actual, final Object expected, final String stepDetail) {
		ParkarLogger.traceEnter();
		boolean result = false;
		validateExpectedActualValues(actual, expected);
		String message = "verifyEquals: " + stepDetail + BREAK_LINE + " A: " + actual.toString() + BREAK_LINE + " E: "
				+ expected.toString();
		try {
			Assert.assertEquals(actual, expected);
			logger.info(message);
			reporter.reportStep(StepStatus.PASS, message);
			result = true;
		} catch (AssertionError ae) {
			verificationFailures.add(ae);
			logger.error(message);
			reporter.reportStep(StepStatus.FAIL, message , BasicPageSyncHelper.saveAsScreenShot(appiumDriver));
		}
		ParkarLogger.traceLeave();
		return result;
	}
	/**
	 * Asserts that two objects are not equal. 
	 * 
	 * @param actual:Object
	 * @param expected:Object
	 * @return boolean
	 */
	public boolean verifyNotEquals(final Object actual, final Object expected) {
		return verifyNotEquals(actual, expected, "");

	}
	/**
	 * Asserts that two objects are not equal. 
	 * 
	 * @param actual:Object
	 * @param expected:Object
	 * @param stepDetail:String
	 * @return boolean
	 */
	public boolean verifyNotEquals(final Object actual, final Object expected, final String stepDetail) {
		ParkarLogger.traceEnter();
		boolean result = false;

		validateExpectedActualValues(actual, expected);
		String message = "verifyNotEquals: " + stepDetail + BREAK_LINE + " A: " + actual.toString() + BREAK_LINE
				+ " Not E: " + expected.toString();
		try {
			Assert.assertNotEquals(actual, expected);
			logger.info(message);
			reporter.reportStep(StepStatus.PASS, message);
			result = true;
		} catch (AssertionError ae) {
			verificationFailures.add(ae);
			logger.error(message);
			reporter.reportStep(StepStatus.FAIL, message , BasicPageSyncHelper.saveAsScreenShot(appiumDriver));
		}
		ParkarLogger.traceLeave();
		return result;

	}
		
	/**
	 * Validate that two objects are not null,and throws IllegalArgumentException
	 * @param expected:Object
	 * @param actual:Object
	 * @throws IllegalArgumentException throws a IllegalArgumentException
	 */
	private void validateExpectedActualValues(Object expected, Object actual) throws IllegalArgumentException {
		ParkarLogger.traceEnter();
		if (expected == null) {
			ParkarLogger.traceLeave();
			throw new IllegalArgumentException("expectedValue");
		}

		if (actual == null) {
			ParkarLogger.traceLeave();
			throw new IllegalArgumentException("actualValue");
		}
	}

}
