package com.parkar.api.rest.driver;

import static com.jayway.restassured.RestAssured.given;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.log4testng.Logger;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.ParkarReporter;
import com.parkar.report.StepStatus;
import com.parkar.testng.Configurator;
import com.parkar.api.rest.exception.ParkarCoreAPIException;

public class HTTPDriver {
	final static Logger logger = Logger.getLogger(HTTPDriver.class);
	protected ParkarReporter reporter = ParkarReporter.getInstance();
	protected Map <String,Object> genericParameters;

	/**
	 * Constructor of HTTPDriver
	 * 
	 * @param configurator: Configurator
	 */
	public HTTPDriver(Configurator configurator){
		genericParameters = new LinkedHashMap<String,Object>();
		genericParameters.put("tenantId", configurator.getParameter("Tenant"));
	}
	
	/**
	 * Get genericParameters
	 * 
	 * @return parameters in map
	 */
	public Map<String, Object> getGenericParameters() {
		return genericParameters;
	}

	/**
	 * Generate the message with payload and queryParameters
	 * 
	 * @param action: String
	 * @param URI: String
	 * @param payload: String
	 * @param queryParameters: Map
	 * @return message in String
	 */
	private String generateMessage(String action,String URI, String payload, Map<String, ?> queryParameters){
		return action + ": [" + URI + "]\n "
				+ "payload:\n " + payload != null ? payload : ""
				+ "parameters:\n" + queryParameters != null ?  queryParameters.toString() : "";
	}
	
	/**
	 * This method is to perform the Restful request action get to get the response with uri and authToken parameters
     * 
	 * @param uri: String
	 * @param authToken: String
	 * @return response of the get
	 * @throws ParkarCoreAPIException
	 * 					: parkar core API exception
	 */
	public Response get(String uri, String authToken) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		String msg = generateMessage("GET",uri,null,null);
		
		try {
			res = given().headers("Cookie", authToken)
					.when().get(uri);

			logger.info(msg);
			reporter.reportStep(StepStatus.INFO, msg);
		} catch (Exception e) {
			reporter.reportStep(StepStatus.FAIL, msg, e);
			throw new ParkarCoreAPIException(msg, e);
		} 
		ParkarLogger.traceLeave();
		return res;
	}
	
	/**
	 * This method is to perform the Restful request action get to get the response with uri and map
	 * @param uri: String
	 * @param authToken: String
	 * @param queryParameters: Map
	 * @return response of a get
	 * @throws ParkarCoreAPIException
	 * 						: throws core API exception
	 */
	public Response get(String uri, String authToken, Map<String, ?> queryParameters)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		String msg = generateMessage("GET",uri,null,queryParameters);
		try {
			res = given().headers("Cookie", authToken)
					.parameters(queryParameters)
					.when()
					.get(uri);
					
			logger.info(msg);
			reporter.reportStep(StepStatus.INFO, msg);
		} catch (Exception e) {
			logger.error(msg, e);
			reporter.reportStep(StepStatus.FAIL, msg, e);
			throw new ParkarCoreAPIException(msg, e);
		}
		ParkarLogger.traceLeave();
		return res;
	}
	
	/**
	 * This method is to perform the Restful request action post to get the response with with uri, authToken, payload, queryParameters
	 * @param uri: String
	 * @param authToken: String
	 * @param payload: String
	 * @param queryParameters: Map
	 * @return response of post
	 * @throws ParkarCoreAPIException
	 * 						: throws core API exception
	 */
	public Response post(String uri, String authToken, String payload, Map<String, ?> queryParameters) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		String msg = generateMessage("POST",uri,payload,queryParameters);
		
		try {
			res = given().headers("Cookie", authToken)
					.parameters(queryParameters)
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(payload).when().post(uri);

			logger.info(msg);
			reporter.reportStep(StepStatus.INFO, msg);
		} catch (Exception e) {
			reporter.reportStep(StepStatus.FAIL, msg, e);
			throw new ParkarCoreAPIException(msg, e);
		} 
		ParkarLogger.traceLeave();
		return res;
	}
	
	/**
	 *  * This method is to perform the Restful request action post to get the response with url, authToken and payload
	 * 
	 * @param uri: String
	 * @param authToken: String
	 * @param payload: String
	 * @return response
	 * @throws ParkarCoreAPIException
	 * 							: throws core API exception
	 */
	public Response post(String uri, String authToken, String payload) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		String msg = generateMessage("POST",uri,payload,null);
		
		try {
			res = given().headers("Cookie", authToken)
					.accept(ContentType.JSON)
					.contentType(ContentType.JSON)
					.body(payload).when().post(uri);

			logger.info(msg);
			reporter.reportStep(StepStatus.INFO, msg);
		} catch (Exception e) {
			reporter.reportStep(StepStatus.FAIL, msg, e);
			throw new ParkarCoreAPIException(msg, e);
		} 
		ParkarLogger.traceLeave();
		return res;
	}
	
}
