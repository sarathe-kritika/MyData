
package com.parkar.testng;

import java.lang.reflect.Method;

import java.util.Map;
import org.uncommons.reportng.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.parkar.dateutils.DateGenerator;
import com.parkar.dateutils.DateParser;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.CoreReporter;
import com.parkar.report.ParkarReporter;
import com.parkar.utils.database.DBUtils;

@Listeners({ TestResultListener.class, HTMLReporter.class, JUnitXMLReporter.class})
public abstract class BaseTest {

	private CoreReporter reporter=ParkarReporter.getInstance();
	protected Configurator configurator = Configurator.getInstance();

	final  static Logger logger =Logger.getLogger(BaseTest.class);
	private static final String BREAK_LINE = "</br>";
	protected DateParser dateParser = new DateParser();
	protected DateGenerator dateGenerator = new DateGenerator();

	/**
	 * Before suite, initialization
	 * 
	 * @param context: ITestContext
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 */
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext context) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		try {
			Map<String,String> params = context.getCurrentXmlTest().getAllParameters();
			configurator.initializeParameters(params);
			reporter.initializeReport(params);
		} catch (Exception e) {
			String errorMsg = "excution before suite fail!";
			logger.error(errorMsg, e);
			throw new ParkarCoreCommonException(errorMsg,e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * After suite, end the report
	 * 
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 */
	@AfterSuite(alwaysRun=true)
	public void afterSuite() throws ParkarCoreCommonException {
		reporter.endReport();
	}

	/**
	 * Before method, initialization.
	 * 
	 * @param m: Method
	 * @param context: ITestContext
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 */

	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(Method method, ITestContext context, Object[] testData) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		try {
			String testCaseName = "";
			String testCaseDescription = "";
			String testCaseData ="";
			TestParameters testParams = null;
			if (testData != null && testData.length > 0) {
				// Check if test method has actually received required
				// parameters
				for (Object testParameter : testData) {
					if (testParameter instanceof TestParameters) {
						testParams = (TestParameters) testParameter;
					}else if (testParameter != null && testParameter!="") {
						testCaseData = testCaseData + testParameter.toString()+ " | " ;
					}
				}
			}
			testCaseName = testParams != null ? String.format("%s(%s)", method.getName(), testParams.getTestName()) : method.getName();
			testCaseDescription = testParams != null ? testParams.getTestDescription() : method.getAnnotation(Test.class).description();
			testCaseDescription = testCaseData != "" ? testCaseDescription + BREAK_LINE + testCaseData : testCaseDescription;
			logger.info("Start test case [" + testCaseName + "]");
			String browser= context.getCurrentXmlTest().getParameter("browser");
			if (StringUtils.isNotBlank(browser))
				testCaseName = testCaseName + "_" + browser;
			reporter.startTest(testCaseName, testCaseDescription, method.getAnnotation(Test.class).groups()); 
		} catch (Exception e) {
			String erroMsg ="excute before test method fail";
			logger.error(erroMsg, e);
			throw new ParkarCoreCommonException(erroMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Closes the database connections after method test.
	 *
	 * @throws ParkarCoreCommonException
	 */
	@AfterTest(alwaysRun = true)
	public void afterTest() throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		try {
			if(DBUtils.mySQLConnection != null) {
				DBUtils.mySQLConnection.close();
			}
			if(DBUtils.oracleConnection != null) {
				DBUtils.oracleConnection.close();
			}
			if(DBUtils.postgreSQLConnection != null) {
				DBUtils.postgreSQLConnection.close();
			}
		} catch (Exception e) {
			String erroMsg = "Failed to close database connection.";
			logger.error(erroMsg, e);
			throw new ParkarCoreCommonException(erroMsg, e);
		}
		ParkarLogger.traceLeave();
	}

}
