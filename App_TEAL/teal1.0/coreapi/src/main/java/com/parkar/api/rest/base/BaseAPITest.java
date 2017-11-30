package com.parkar.api.rest.base;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.parkar.api.rest.assertions.APIAssertions;
import com.parkar.api.rest.driver.APIDriver;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.api.rest.json.JsonBuildHelper;
import com.parkar.api.rest.operations.APIOperations;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.IReporter;
import com.parkar.report.ParkarReporter;
import com.parkar.testng.BaseTest;

public class BaseAPITest extends BaseTest{
	protected final static Logger logger = Logger.getLogger(BaseAPITest.class);					
	public APIDriver apiDriver=new APIDriver();	
	public static String BASE_URI=null; 
	protected IReporter reporter = ParkarReporter.getInstance();
	
	//API Object Implementation
	protected APIAssertions assertion;	

	/**
	 * SetUp - Global Cookies are initialized at the global level, and the Base URL is constrcuted and set for all the calls.
	 * @param context: ITestContext
	 * @throws ParkarCoreAPIException
	 * 			: Parkar Core API exception
	 */
	@BeforeSuite(alwaysRun = true)
	public void setUp(ITestContext context) throws ParkarCoreAPIException{
		BASE_URI=configurator.getParameter("Backend_Server");		
		constructURL(BASE_URI);
		//set date parser for jsonBuilder
		JsonBuildHelper.setDateFormat(context.getCurrentXmlTest().getAllParameters());
	}	
	
	/**
	 * Initialize variables before method test
	 * 
	 * @param m method
	 * @param context ITestContext
	 * @param testResult Describes the result of a test
	 * @throws ParkarCoreCommonException throws a ParkarCoreCommonException
	 */
	@BeforeMethod(alwaysRun = true)
	public void beforeMethodAPI(Method m, ITestContext context, ITestResult testResult) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		try {
			assertion = new APIAssertions();
			//set the assertion that will be used by listener
			testResult.setAttribute("assertion", assertion);
		} catch (Exception e) {
			String erroMsg = "excute before test method fail";
			logger.error(erroMsg, e);
			throw new ParkarCoreAPIException(erroMsg, e);
		}
		ParkarLogger.traceLeave();
	}
	
	/**
	 * Construct baseURL
	 * @param baseURL: String
	 * @throws ParkarCoreAPIException
	 * 			: Parkar Core API exception
	 */
	public void constructURL(String baseURL) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		APIOperations apiDriver= new APIOperations();
		try{
		apiDriver.setBaseURL(baseURL);	
		} catch(Exception e){
			logger.error("Exception occured while setting the base URL . Exception due to : "+ e.getMessage());
		}
		ParkarLogger.traceLeave();
	}	
	
	/**
	 * Do initialization 
	 * @param BaseURL: String
	 * @throws ParkarCoreAPIException
	 * 			: Parkar Core APi exception
	 */
	public void initialize(String BaseURL) throws ParkarCoreAPIException{
		ParkarLogger.traceEnter();
		constructURL(BaseURL);
		ParkarLogger.traceLeave();
	}	
	
}
