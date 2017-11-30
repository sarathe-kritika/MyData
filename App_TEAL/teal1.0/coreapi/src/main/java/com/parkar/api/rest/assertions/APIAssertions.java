
package com.parkar.api.rest.assertions;


import org.apache.log4j.Logger;
import org.testng.Assert;

import com.parkar.assertion.ParkarSoftAssertion;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.IReporter;
import com.parkar.report.ParkarReporter;
import com.parkar.report.StepStatus;

public class APIAssertions  extends ParkarSoftAssertion {
	protected final static Logger logger = Logger.getLogger(APIAssertions.class);
	private IReporter reporter = ParkarReporter.getInstance();

	/**
	 * Verify if actual is true
	 * 
	 * @param actual: boolean
	 * @return true or false
	 */
	public boolean verifyTrue(final boolean actual) {
		return verifyTrue(actual, "");
	}

	
	/**
	 * Verify if actual is true, log message with step details
	 * Can be used when user needs to send custom message on to the reports
	 * @param actual: boolean
	 * @param stepDetail: String
	 * @return true or false
	 */
	public boolean verifyTrue(final boolean actual, final String stepDetail) {
		ParkarLogger.traceEnter();
		boolean result = false;
		String message = "verifyTrue: " + BREAK_LINE + " A: " + actual + BREAK_LINE + stepDetail;
		try {
			Assert.assertTrue(actual);
			logger.info(message);
			reporter.reportStep(StepStatus.PASS, message);
			result=true;
		} catch (AssertionError ae) {
			verificationFailures.add(ae);
			logger.error(message + " - "+ ae.getMessage());
			reporter.reportStep(StepStatus.FAIL, message);
		}
		ParkarLogger.traceLeave();
		return result;
	}

	
	/**
	 * Verify if actual is false
	 * 
	 * @param actual: boolean
	 * @return true or false
	 */
	public boolean verifyFalse(final boolean actual) {
		return verifyFalse(actual, "");
	}

	
	/**
	 * Verify if actual is false, log message with step details
	 * Can be used when user needs to send custom message on to the reports
	 * @param actual: boolean
	 * @param stepDetail: String
	 * @return true or false
	 */
	public boolean verifyFalse(final boolean actual, final String stepDetail) {
		ParkarLogger.traceEnter();
		boolean result = false;
		String message = "verifyFalse: " + BREAK_LINE + " A: " + actual + BREAK_LINE + stepDetail;
		try {
			Assert.assertFalse(actual);
			logger.info(message);
			reporter.reportStep(StepStatus.PASS, message);
			result=true;
		} catch (AssertionError ae) {
			verificationFailures.add(ae);
			logger.error(message+" - "+ ae.getMessage());
			reporter.reportStep(StepStatus.FAIL, message);
		}
		ParkarLogger.traceLeave();
		return result;
	}

	
	/**
	 * Verify if actual equals to expected
	 * 
	 * @param actual: Object
	 * @param expected: Object
	 * @return true or false
	 */
	public boolean verifyEquals(final Object actual, final Object expected) {
		return verifyEquals(actual, expected, "");
	}


	/**
	 * Verify if actual equals to expected, log message with step details
	 *  Can be used when user needs to send custom message on to the reports
	 * @param actual: Object
	 * @param expected: Object
	 * @param stepDetail: String
	 * @return true or false
	 */
	public boolean verifyEquals(final Object actual, final Object expected, final String stepDetail) {
		ParkarLogger.traceEnter();
		boolean result = false;
		validateExpectedActualValues(actual, expected);
		String message = "verifyEquals: " + BREAK_LINE + " A: " + actual.toString() + BREAK_LINE + " E: "
				+ expected.toString() + BREAK_LINE + stepDetail;
		try {
			Assert.assertEquals(actual, expected);
			logger.info(message);
			reporter.reportStep(StepStatus.PASS, message);
			result = true;
		} catch (AssertionError ae) {
			verificationFailures.add(ae);
			logger.error(message+" - "+ ae.getMessage());
			reporter.reportStep(StepStatus.FAIL, message);
		}
		ParkarLogger.traceLeave();
		return result;
	}

	
	/**
	 * Verify if actual doesn't equal to expected
	 * 
	 * @param actual: Object
	 * @param expected: Object
	 * @return true or false
	 */
	public boolean verifyNotEquals(final Object actual, final Object expected) {
		return verifyNotEquals(actual, expected, "");
	}

	
	/**
	 * Verify if actual doesn't equal to expected, log message with step details
	 * Can be used when user needs to send custom message on to the reports
	 * @param actual: Object
	 * @param expected: Object
	 * @param stepDetail: String
	 * @return true or false
	 */
	public boolean verifyNotEquals(final Object actual, final Object expected, final String stepDetail) {
		ParkarLogger.traceEnter();
		boolean result = false;
		validateExpectedActualValues(actual, expected);
		String message = "verifyNotEquals: " + BREAK_LINE + " A: " + actual.toString() + BREAK_LINE + " Not E: "
				+ expected.toString() + BREAK_LINE + stepDetail;
		try {
			Assert.assertNotEquals(actual, expected);
			logger.info(message);
			reporter.reportStep(StepStatus.PASS, message);
			result = true;
		} catch (AssertionError ae) {
			verificationFailures.add(ae);
			logger.error(message+ " - "+ ae.getMessage());
			reporter.reportStep(StepStatus.FAIL, message + " - "+ ae.getMessage());
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