 package com.parkar.api.rest.auth;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import com.parkar.api.rest.operations.APIOperations;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.ParkarReporter;
import com.parkar.report.StepStatus;

public class AuthN {
	protected final static Logger logger = Logger.getLogger(AuthN.class);
	protected static ParkarReporter reporter = ParkarReporter.getInstance();
	public static final String BREAK_LINE = "</br>";


	
	/**
	 * Login function
	 * 
	 * @param apiOps: APIOperation
	 * @param userName: String
	 * @param password: String
	 * @param loginEndpoint: String
	 * @return cookie: String
	 */
	public static String doLogin(APIOperations apiOps,String userName, String password, String loginEndpoint ){
		ParkarLogger.traceEnter();
		Response res=null;
		 String infoMessage="Logged in with username :"+ userName+BREAK_LINE+ " Password : "+password +BREAK_LINE+" Login endpoint as : "+ loginEndpoint ;
		try{
			String formParametersJson ="{username:"+userName+",password:"+password+"}";
			res=apiOps.postWithFormParameters("", formParametersJson, loginEndpoint);
		 logger.info(infoMessage);
		 reporter.reportStep(StepStatus.INFO, infoMessage);
		}catch(Exception e){		
			logger.error(infoMessage,e);
			reporter.reportStep(StepStatus.ERROR, infoMessage,e);
		}
		ParkarLogger.traceLeave();
		return generateCookie(res);
		
	}	
	
	/**
	 * Generate cookie via login response
	 * 
	 * @param loginResponse: Response
	 * @return cookie in String
	 */
	private static String generateCookie(Response loginResponse) {
		ParkarLogger.traceEnter();
		String cookie="";
		try{
		Iterator<Header> it= loginResponse.getHeaders().iterator();	   
			while (it.hasNext()) {
				Header h = (Header) it.next();
				if (h.getName().equalsIgnoreCase("Set-Cookie")) {
					cookie = cookie + h.getValue().split("[;]")[0];
					cookie =cookie.split("=")[1];
					break;
				}
			}
		logger.info("Generated cookie is : "+ cookie);
		}catch(Exception e){
			logger.error("Exception occured while generating cookie .  Exception occured is due to : "+e.getMessage());

		}
		   ParkarLogger.traceLeave();
		   return cookie;
	}
	
	
	
	
}