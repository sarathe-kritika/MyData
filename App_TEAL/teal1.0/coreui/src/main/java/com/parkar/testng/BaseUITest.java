package com.parkar.testng;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.parkar.assertion.UIAssertion;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.factory.ParkarUtilsFactory;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.CoreReporter;
import com.parkar.report.IReporter;
import com.parkar.report.ParkarReporter;
import com.parkar.report.StepStatus;

public abstract class BaseUITest extends BaseTest{
	protected WebDriver driver;
	
	protected UIAssertion assertion;

	protected IReporter reporter = ParkarReporter.getInstance();
	
	private CoreReporter coreReporter = ParkarReporter.getInstance();
	
	private static boolean isBrowserInfoSet = false;
		
	final static Logger logger = Logger.getLogger(BaseUITest.class);
	/**
	 * Initialize variables before method test
	 * 
	 * @param m method
	 * @param context ITestContext
	 * @param testResult ITestResult
	 * @throws Exception : ParkarCoreCommonException or ParkarCoreUIException
	 */
    @BeforeMethod(alwaysRun = true)
	public void beforeMethodUI(Method m, ITestContext context, ITestResult testResult) throws Exception {
		ParkarLogger.traceEnter();
		try {
			driver = ParkarUtilsFactory.initWebDriver(context);
			driver.manage().window().maximize();
			String frontEndServer = configurator.getParameter("Frontend_Server");
			driver.get(frontEndServer);
			assertion = new UIAssertion(driver);
			//set the assertion that will be used by listener
			testResult.setAttribute("assertion", assertion);
			reporter.reportStep(StepStatus.INFO, "Navigate to Login Page ");
			if (!isBrowserInfoSet) {
				Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
				coreReporter.addSystemInfo("browser", cap.getBrowserName().toLowerCase());
				coreReporter.addSystemInfo("browser version", cap.getVersion().toString());
				isBrowserInfoSet = true;
			}
			// Initialize trackers object
			// ExtentReportConfigurator.getInstance().resetTrackerCollection(m.getName());
		} catch (Exception e) {
			if (e instanceof ParkarCoreCommonException)
				throw e;
			String erroMsg = "excute before test method fail";
			logger.error(erroMsg, e);
			throw new ParkarCoreUIException(erroMsg, e);
		}
		ParkarLogger.traceLeave();
	}
    /**
	 * Close driver and quit driver after method test.
	 * 
	 * @param m Method
	 * @param result ITestResult
	 * @throws ParkarCoreCommonException throws a ParkarCoreCommonException
	 */
	@AfterMethod(alwaysRun = true)
	public void afterMethodUI(Method m, ITestResult result) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		try {
			driver.close();
			driver.quit();
		} catch (Exception e) {
			if (e instanceof ParkarCoreCommonException)
				throw e;
			String erroMsg = "excute after test method fail";
			logger.error(erroMsg, e);
			throw new ParkarCoreUIException(erroMsg, e);
		}
		ParkarLogger.traceLeave();

	}

}
