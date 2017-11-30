package com.parkar.report;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.factory.ParkarReportFactory;
import com.parkar.logging.ParkarLogger;
import com.parkar.testng.Configurator;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ParkarReporter implements CoreReporter {

	private static ParkarReporter instance;
	private static final SimpleDateFormat SCREEN_SHOT_FORMATER = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS");
	private static boolean deepReporting;
	private Configurator configurator = Configurator.getInstance();
	private ExtentReports extentReport;
	// private CaseUsageTracker tracker;
	private final static Logger logger = Logger.getLogger(ParkarReporter.class);
	private static final String BREAK_LINE = "</br>";
	private static final int STACK_TRACE_LIMIT = 15;
	private Map<Integer, TestReportContext> testReportContextMap;
	final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";

	/**
	 * Creates an instance of ExtentReportConfigurator
	 * 
	 * @return ExtentReportConfigurator
	 * 
	 */
	public static ParkarReporter getInstance() {
		ParkarLogger.traceEnter();
		// Double checked locking principle is used
		if (instance == null) {
			synchronized (ParkarReporter.class) {
				if (instance == null) {
					instance = new ParkarReporter();
					logger.info("Reporter instance created.");
				}
			}
		}
		ParkarLogger.traceLeave();
		return instance;
	}

	/**
	 * Initializes extent report
	 * 
	 * @param params:HashMap
	 */
	@Override
	public void initializeReport(Map<String, String> params) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		try {
			testReportContextMap = new HashMap<Integer, TestReportContext>();
			/*
			 * Initialize extend report for local report generation
			 */
			extentReport = ParkarReportFactory.createExtentReport();
			/**
			 * Set deep reporting value
			 */
			if (!configurator.getParameter("deepReporting").equalsIgnoreCase("False"))
				deepReporting = true;
			/*
			 * Initialize heat tracker reporting
			 */
			/*
			 * tracker = null; if (params.containsKey("logLevel")) { if
			 * (params.get("logLevel").equalsIgnoreCase("suite") &&
			 * params.containsKey("dbName") && params.containsKey("dbPort")) {
			 * tracker = new ParkarTrackerFactory().createReportTracker(
			 * params.get("dbName"), params.get("dbPort")); } }
			 */
		} catch (Exception e) {
			String message = "initialize Reporting Failed: ";
			logger.error(message, e);
			throw new ParkarCoreCommonException(message, e);
		}
		logger.info("Initialize Reporting Success. ");
		ParkarLogger.traceLeave();
	}

	/**
	 * Add system information to report, need to pass key attribute and it's
	 * value
	 * 
	 * @param key:
	 *            String
	 * @param value:
	 *            String
	 */
	@Override
	public void addSystemInfo(String key, String value) {
		extentReport.addSystemInfo(key, value);
	}

	private synchronized TestReportContext getTestReporterContext() {
		return testReportContextMap.get((int) (long) (Thread.currentThread().getId()));
	}

	@Override
	public synchronized void endTest() {
		TestReportContext testReportContext = testReportContextMap.get((int) (long) (Thread.currentThread().getId()));
		extentReport.endTest(testReportContext.getExtentTest());
	}

	/**
	 * Creates a toggle for a test in extent report
	 * 
	 * @param testName:
	 *            String
	 * @param desc
	 *            : String
	 * 
	 */
	@Override
	public synchronized void startTest(final String testName, final String desc, final String... categories) {
		TestReportContext testReportContext = new TestReportContext();
		ExtentTest extetTest = extentReport.startTest(testName, desc);
		extetTest.assignCategory(categories);
		testReportContext.setExtentTest(extetTest);
		testReportContextMap.put((int) (long) (Thread.currentThread().getId()), testReportContext);
	}

	/**
	 * Closes extent report
	 */
	@Override
	public void endReport() {
		extentReport.close();
	}

	/**
	 * Writes the status of a step in extent report with LogStatus as INFO
	 * 
	 * @param stepName:String
	 */
	@Override
	public void reportStep(final String stepName) {
		getTestReporterContext().getExtentTest().log(LogStatus.INFO, stepName);
		Reporter.log(stepName + BREAK_LINE);
	}

	/**
	 * Writes the status of a step in extent report
	 * 
	 * @param status:StepStatus
	 * @param stepName:
	 *            String
	 */
	@Override
	public void reportStep(final StepStatus status, final String stepName) {
		getTestReporterContext().getExtentTest().log(getLogStatus(status), stepName);
		Reporter.log(stepName + BREAK_LINE);
	}

	/**
	 * Writes the status of a step in extent report
	 * 
	 * @param status:StepStatus
	 * @param stepName:
	 *            String
	 * @param t:
	 *            Throwable
	 */
	@Override
	public void reportStep(final StepStatus status, final String stepName, Throwable t) {
		getTestReporterContext().getExtentTest().log(getLogStatus(status), stepName + BREAK_LINE + getFormatedStack(t));
		Reporter.log(stepName + BREAK_LINE);
	}

	/**
	 * Writes the status of a step in extent report
	 * 
	 * @param status:StepStatus
	 * @param stepName:
	 *            String
	 * @param details:
	 *            String
	 */
	@Override
	public void reportStep(final StepStatus status, final String stepName, final String details) {
		getTestReporterContext().getExtentTest().log(getLogStatus(status), stepName + BREAK_LINE + details);
		Reporter.log(stepName + BREAK_LINE + details);
	}

	/**
	 * Writes the status of a step in extent report. Adds screenshot file to it.
	 * 
	 * @param status:StepStatus
	 * @param stepName:
	 *            String
	 * @param file:
	 *            screenshot file
	 */
	@Override
	public void reportStep(final StepStatus status, final String stepName, File file) {
		System.setProperty(ESCAPE_PROPERTY, "false");
		String imagePath = getScreenShotPath(file);
		String htmlTagPath = BREAK_LINE + getTestReporterContext().getExtentTest().addScreenCapture(imagePath);
		getTestReporterContext().getExtentTest().log(getLogStatus(status), stepName + htmlTagPath);
		String screen = "file://" + imagePath;
		Reporter.log(BREAK_LINE + "<a href= '" + screen + "' target='_blank' ><img src='" + screen
				+ "' height=\"42\" width=\"42\"border=\"5\">" + "</a>" + BREAK_LINE);
	}

	/**
	 * Writes the status of a step in extent report. Adds screenshot file to it
	 * with message of exception
	 * 
	 * @param status:StepStatus
	 * @param stepName:
	 *            String
	 * @param file:
	 *            screenshot file
	 * @param t:
	 *            Throwable
	 */
	@Override
	public void reportStep(final StepStatus status, final String stepName, final File file, final Throwable t) {
		System.setProperty(ESCAPE_PROPERTY, "false");
		String imagePath = getScreenShotPath(file);
		String screen = "file://" + imagePath;
		String stackTrace = getFormatedStack(t);
		String htmlTagPath = BREAK_LINE + getTestReporterContext().getExtentTest().addScreenCapture(imagePath);
		getTestReporterContext().getExtentTest().log(getLogStatus(status),
				stepName + htmlTagPath + BREAK_LINE + stackTrace);
		Reporter.log(stepName + "<a href= '" + screen + "' target='_blank' ><img src='" + screen
				+ "' height=\"42\" width=\"42\"border=\"5\">" + "</a>" + BREAK_LINE + stackTrace + BREAK_LINE);
	}

	/**
	 * Writes the status of a step and details in extent report. Adds screenshot
	 * file to it.
	 * 
	 * @param status:StepStatus
	 * @param stepName:
	 *            String
	 * @param details:
	 *            String
	 * @param file:
	 *            screenshot file
	 */
	@Override
	public void reportStep(final StepStatus status, final String stepName, final String details, final File file) {
		System.setProperty(ESCAPE_PROPERTY, "false");
		String imagePath = getScreenShotPath(file);
		String htmlTagPath = BREAK_LINE + getTestReporterContext().getExtentTest().addScreenCapture(imagePath);
		getTestReporterContext().getExtentTest().log(getLogStatus(status),
				stepName + BREAK_LINE + details + htmlTagPath);
		String screen = "file://" + imagePath;
		Reporter.log(stepName + BREAK_LINE + details + "<a href= '" + screen + "' target='_blank' ><img src='" + screen
				+ "' height=\"42\" width=\"42\"border=\"5\">" + "</a>" + BREAK_LINE);
	}

	/**
	 * Writes the status of a step in extent report only if deep reporting is on
	 * or on failure
	 * 
	 * @param status:StepStatus
	 * @param stepName:
	 *            String
	 */
	@Override
	public void deepReportStep(final StepStatus status, final String stepName) {
		if (status.equals(StepStatus.ERROR) || status.equals(StepStatus.FAIL))
			reportStep(status, stepName);
		else {
			if (deepReporting)
				reportStep(status, stepName);
		}
	}

	/**
	 * Writes the status of a step in extent report only if deep reporting is on
	 * or on failure
	 * 
	 * @param status:StepStatus
	 * @param stepName:
	 *            String
	 * @param t:
	 *            Throwable
	 */
	@Override
	public void deepReportStep(final StepStatus status, final String stepName, final Throwable t) {
		if (status.equals(StepStatus.ERROR) || status.equals(StepStatus.FAIL))
			reportStep(status, stepName, t);
		else if (deepReporting)
			reportStep(status, stepName, t);
	}

	/**
	 * Writes the status of a step in extent report only if deep reporting is on
	 * or on failure.
	 * 
	 * @param status:
	 *            StepStatus
	 * @param stepName:
	 *            String
	 * @param details:
	 *            String
	 */
	@Override
	public void deepReportStep(final StepStatus status, final String stepName, final String details) {
		if (status.equals(StepStatus.ERROR) || status.equals(StepStatus.FAIL))
			reportStep(status, stepName, details);
		else if (deepReporting)
			reportStep(status, stepName, details);
	}

	/**
	 * Writes the status of a step in extent report. Adds screenshot file to it.
	 * Only if deep reporting is on or on failure.
	 * 
	 * @param status:LogStatus
	 * @param stepName:
	 *            String
	 * @param file:
	 *            screenshot file
	 */
	@Override
	public void deepReportStep(final StepStatus status, final String stepName, final File file) {
		if (status.equals(StepStatus.ERROR) || status.equals(StepStatus.FAIL))
			reportStep(status, stepName, file);
		else {
			if (deepReporting)
				reportStep(status, stepName, file);
		}
	}

	/**
	 * Writes the status of a step in extent report. Adds screenshot file to it
	 * with message of exception. Only if deep reporting is on or on failure.
	 * 
	 * @param status:LogStatus
	 * @param stepName:
	 *            String
	 * @param file:
	 *            screenshot file
	 * @param t:
	 *            Throwable
	 */
	@Override
	public void deepReportStep(final StepStatus status, final String stepName, final File file, final Throwable t) {
		if (status.equals(StepStatus.ERROR) || status.equals(StepStatus.FAIL))
			reportStep(status, stepName, file, t);
		else if (deepReporting)
			reportStep(status, stepName, file, t);
	}

	/**
	 * Writes the status of a step and details in extent report. Adds screenshot
	 * file to it. Only if deep reporting is on or on failure.
	 * 
	 * @param status:LogStatus
	 * @param stepName:
	 *            String
	 * 
	 * @param details:
	 *            String
	 * @param file:
	 *            File
	 * 
	 */
	@Override
	public void deepReportStep(final StepStatus status, final String stepName, final String details, final File file) {
		if (status.equals(StepStatus.ERROR) || status.equals(StepStatus.FAIL))
			reportStep(status, stepName, details, file);
		else if (deepReporting)
			reportStep(status, stepName, details, file);
	}

	/**
	 * Adds a snapshot to the log event details
	 * 
	 * Note: this method does not create the screen-capture for the report, it
	 * only gets the path of the image.
	 * 
	 * @param file:
	 *            File
	 * @return String: image path
	 */
	private String getScreenShotPath(final File file) {
		String imagePath = "";
		if (file != null) {
			try {
				imagePath = configurator.getDirectoryParameter("current_sc_report") + "/_"
						+ SCREEN_SHOT_FORMATER.format(new Date()) + ".png";
				FileUtils.copyFile(file, new File(imagePath));
			} catch (IOException e) {
				String errMsg = " Fail to copy file";
				logger.error(errMsg, e);
			}
		} else {
			String errMsg = " Fail: Screenshot file is null, cannot add screenshot";
			logger.error(errMsg);
		}
		return imagePath;
	}

	/**
	 * Return corresponding ExtentReport LogStatus for incoming StepStatus
	 * 
	 * @param status:
	 *            StepStatus return LogStatus
	 */
	private LogStatus getLogStatus(final StepStatus status) {
		switch (status) {
		case PASS:
			return LogStatus.PASS;
		case FAIL:
			return LogStatus.FAIL;
		case SKIP:
			return LogStatus.SKIP;
		case ERROR:
			return LogStatus.ERROR;
		default:
			return LogStatus.INFO;
		}
	}

	/**
	 * Format the stack message and return first N lines as defined by
	 * STACK_TRACE_LIMIT variable
	 * 
	 * @param e:
	 *            Throwable object
	 */
	private String getFormatedStack(final Throwable e) {
		StringBuilder sb = new StringBuilder();
		int nline = 1;
		if (e != null)
			for (StackTraceElement element : e.getStackTrace()) {
				if (nline <= STACK_TRACE_LIMIT && element != null)
					sb.append(element.toString() + BREAK_LINE);
				else
					break;
				nline++;
			}
		return sb.toString();

	}

	/**
	 * Usage tracker functions
	 * 
	 * @param caseName
	 * @throws ParkarCoreCommonException
	 *             : customized Parkar core common exception
	 */
	/*
	 * public void resetTrackerCollection(String caseName) throws
	 * ParkarCoreCommonException{ if(tracker!=null)
	 * tracker.resetTrackerCollection(caseName); }
	 */

	/**
	 * Usage tracker functions
	 * 
	 * @throws ParkarCoreCommonException
	 *             : customized Parkar core common exception
	 */
	/*
	 * public void saveUsage() throws ParkarCoreCommonException{
	 * if(tracker!=null) tracker.saveUsage(); }
	 */
}
