package com.parkar.api.rest.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.jayway.restassured.response.Response;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.ParkarReporter;
import com.parkar.report.StepStatus;
import com.parkar.api.rest.IAPIOperations;
import com.parkar.api.rest.auth.AuthN;
import com.parkar.api.rest.base.BaseAPITest;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.api.rest.json.Request;
import com.parkar.api.rest.operations.APIOperations;
import com.parkar.api.rest.operations.APIResponse;

public class APIDriver  {
	protected final static Logger logger = Logger.getLogger(APIDriver.class);
	public String GLOBAL_COOKIE = null;

	protected ParkarReporter reporter = ParkarReporter.getInstance();
	public static final String BREAK_LINE = "</br>";

	/** 
	 * Restful request action types
	 */
	private enum Method {
		GET, POST, DELETE, PUT
	};

	/**
	 * Get the API driver
	 * 
	 * @return API driver
	 */
	public IAPIOperations getApiDriver() {
		return apiDriver;
	}

	public APIOperations apiDriver;


	/**
	 * Constructor of APIDriver
	 */
	public APIDriver() {
		this.apiDriver = new APIOperations();
	}

	/**
	 * This method is to login to app  and sets the global cookie internally
	 * 
	 * @param userName: String
	 * @param password: String
	 */
	public void doLogin(String userName, String password) {
		GLOBAL_COOKIE = AuthN.doLogin(apiDriver,userName, password, BaseAPITest.BASE_URI);
	}

	/**
	 * This method is for fetching required path parameters from a map and replacing them in the URI
	 * Result is generated a URI with path parameters
	 * 
	 * @param map: HashMap
	 * @param endpoint: String
	 * @return endpoint
	 */
	private static String getPathParameters(HashMap<String, Object> map, String endpoint) {
		ParkarLogger.traceEnter();
		try {
			Set<String> keys = map.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				if (endpoint.contains("{" + key + "}")) {
					endpoint = endpoint.replace("{" + key + "}", (CharSequence) map.get(key));
				}
				logger.info("Generated URI with path parameters is : " + endpoint);
			}
		} catch (Exception e) {
			logger.error("Generating the URI with path parameters encountered an exception . " + e.getMessage());
		}
		ParkarLogger.traceLeave();
		return endpoint;
	}

	/**
	 * This method gets the calling method name, return the name as a String.
	 * 
	 * @return methodName
	 */
	private static String getCallerMethodName() {
		ParkarLogger.traceEnter();
		String methodName = "";
		try {
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement e = stacktrace[3];
			methodName = e.getMethodName();
		} catch (Exception e) {
			logger.error("Error while getting the caller method name. Method name recieved is :  " + methodName
					+ "  Exception occured is due to : " + e.getMessage());
		}
		ParkarLogger.traceLeave();
		return methodName;
	}

	/**
	 * This method is to perform the Restful request action(get/post/put/delete) with parameters (query and/or path)
     * using the specified URI.Input HashMap may have both path and query parameters. The parameters with the {} in URI will be replaced 
     * by the value which get from the HashMap key.  Ex :{name=tom, id=123, age=32}
     * /hello/world/{id}/{name} generated URI - /hello/world/123/tom?age=32
	 * 
	 * @param URI: String
	 * @param map: HashMap, this map saves request parameters
	 * 
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 * @return res: response
	 */

	public APIResponse execute(String URI, Map<String, Object> map) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		String methodName = getCallerMethodName();
		Response response = null;
		HashMap<String, Object> temp = new HashMap<String, Object>();
		Method method = Method.valueOf(methodName.toUpperCase());
		temp = getMapWithParameters(map);
		
		try {
			switch (method) {
			case GET: {
				String endpoint = generateURIWithParameters(URI, temp);
				response = apiDriver.getWithNoParameters(GLOBAL_COOKIE, endpoint);
				String infoMessage = "Method: GET " + BREAK_LINE + "URI : " + endpoint + BREAK_LINE + " Response :  "
						+ response.prettyPrint();
				logger.info(infoMessage);
				reporter.reportStep(StepStatus.INFO, infoMessage);

				break;
			}
			case POST: {
				String endpoint = generateURIWithParameters(URI, temp);
				response = apiDriver.postWithPathParameters(GLOBAL_COOKIE, endpoint);
				String infoMessage = "Method: POST " + BREAK_LINE + " URI : " + endpoint + BREAK_LINE + " Response :  "
						+ response.prettyPrint();
				logger.info(infoMessage);
				reporter.reportStep(StepStatus.INFO, infoMessage);

				break;
			}
			case PUT: {
				String endpoint = generateURIWithParameters(URI, temp);
				response = apiDriver.putWithPathParameters(GLOBAL_COOKIE, endpoint);
				String infoMessage = "Method: PUT " + BREAK_LINE + " URI : " + endpoint + BREAK_LINE + " Response :  "
						+ response.prettyPrint();
				logger.info(infoMessage);
				reporter.reportStep(StepStatus.INFO, infoMessage);

				break;
			}
			case DELETE: {
				String endpoint = generateURIWithParameters(URI, temp);
				response = apiDriver.deleteWithPathParameters(GLOBAL_COOKIE, endpoint);
				String infoMessage = "Method: DELETE " + BREAK_LINE + " URI : " + endpoint;
				logger.info(infoMessage);
				reporter.reportStep(StepStatus.INFO, infoMessage);

				break;
			}

			default:
				logger.info("No matching case found.");

			}
		} catch (Exception e) {
			String errorMessage = "Exception occured while firing an API call with URI : " + URI + BREAK_LINE
					+ " and parameters as  :  " + map;
			logger.error(errorMessage, e);
			reporter.reportStep(StepStatus.ERROR, errorMessage, e);

		}
		ParkarLogger.traceLeave();
		APIResponse apiResponse= new APIResponse(response);
		return apiResponse;
	}

	/**
	 * This method is to perform the Restful request action(post/put) with a json request body with the specified URI.
     * Input JsonRequest will be treated as request body
     *  
	 * @param URI: String
	 * @param payload: Request
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 * @return res: response
	 */

	public APIResponse execute(String URI, Request payload) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		String jsonRequest = payload.getRequest().toString();
		Response response = null;
		String methodName = getCallerMethodName();
		Method method = Method.valueOf(methodName.toUpperCase());
		try {
			switch (method) {
			case POST: {
				response = apiDriver.postWithFormParameters(GLOBAL_COOKIE, jsonRequest, URI);
				String infoMessage = "Method: POST " + BREAK_LINE + " URI : " + URI + BREAK_LINE + " Request :  "
						+ jsonRequest + BREAK_LINE + " Response :  " + response.prettyPrint();
				logger.info(infoMessage);
				reporter.reportStep(StepStatus.INFO, infoMessage);

				break;
			}
			case PUT: {
				response = apiDriver.putWithFormParameters(GLOBAL_COOKIE, jsonRequest, URI);
				String infoMessage = "Method: PUT " + BREAK_LINE + " URI : " + URI + BREAK_LINE + " Request :   "
						+ jsonRequest + BREAK_LINE + "  Response :  " + response.prettyPrint();
				logger.info(infoMessage);
				reporter.reportStep(StepStatus.INFO, infoMessage);

				break;
			}
			default:
				logger.info("No matching case found.");
			}
		} catch (Exception e) {
			String errorMessage = "Exception occured while firing an API call with URI : " + URI + BREAK_LINE
					+ " and request json body as  :  " + jsonRequest;
			logger.error(errorMessage, e);
			reporter.reportStep(StepStatus.ERROR, errorMessage, e);
		}
		ParkarLogger.traceLeave();
		APIResponse apiResponse= new APIResponse(response);
		return apiResponse;
	}

	/**
	 * This method is to perform the Restful request action(post/put) with json request body and parameters (query and/or path) 
	 * with the specified URI.
     * Input JsonRequest will be treated as request body. 
	 * 
	 * @param URI: String, the URL
	 * @param payload: Request
	 * @param map: HashMap, used to generate the URI, HashMap with have both path and query parameter.
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 * @return res: response
	 */

	public APIResponse execute(String URI, Request payload, Map<String, Object> map) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		String methodName = getCallerMethodName();
		String jsonRequest = payload.getRequest().toString();
		Response response = null;
		HashMap<String, Object> temp = new HashMap<String, Object>();
		Method method = Method.valueOf(methodName.toUpperCase());
		temp = getMapWithParameters(map);
		try {
			switch (method) {
			case POST: {

				String endpoint = generateURIWithParameters(URI, temp);
				response = apiDriver.postWithFormParameters(GLOBAL_COOKIE, jsonRequest, endpoint);
				String infoMessage = "Method: POST " + BREAK_LINE + " URI : " + endpoint + BREAK_LINE + " Request :   "
						+ jsonRequest + BREAK_LINE + " Response :  " + response.prettyPrint();
				logger.info(infoMessage);
				reporter.reportStep(StepStatus.INFO, infoMessage);

				break;
			}
			case PUT: {
				String endpoint = generateURIWithParameters(URI, temp);
				response = apiDriver.putWithFormParameters(GLOBAL_COOKIE, jsonRequest, endpoint);
				String infoMessage = "Method: PUT " + BREAK_LINE + " URI : " + endpoint + BREAK_LINE + " Request :   "
						+ jsonRequest + BREAK_LINE + " Response :  " + response.prettyPrint();
				logger.info(infoMessage);
				reporter.reportStep(StepStatus.INFO, infoMessage);

				break;
			}
			default:
				logger.info("No matching case found.");
			}
		} catch (Exception e) {
			String errorMessage = "Exception occured while firing an API call with URI : " + URI + BREAK_LINE
					+ "  parameters as : " + map + BREAK_LINE + " and request json body as  :  " + jsonRequest;
			reporter.reportStep(StepStatus.ERROR, errorMessage, e);

		}
		ParkarLogger.traceLeave();
		APIResponse apiResponse= new APIResponse(response);
		return apiResponse;
	}

	/**
	 * This method is to perform the Restful request action(get) with no parameters to a URI
	 * @param URI: String, the URL
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 * @return res:response
	 */

	public APIResponse execute(String URI) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		String methodName = getCallerMethodName();
		Response response = null;
		Method method = Method.valueOf(methodName.toUpperCase());
		try {
			switch (method) {

			case GET: {
				response = apiDriver.getWithNoParameters(GLOBAL_COOKIE, URI);
				String infoMessage = "Method: GET " + BREAK_LINE + " URI : " + URI + BREAK_LINE + " Response :  "
						+ response.prettyPrint();
				logger.info(infoMessage);
				reporter.reportStep(StepStatus.INFO, infoMessage);

				break;
			}
			default:
				logger.info("No matching case found.");
			}
		} catch (Exception e) {
			String errorMessage = "Exception occured while firing an API call with URI : " + URI;
			logger.error(errorMessage, e);
			reporter.reportStep(StepStatus.ERROR, errorMessage, e);
		}
		ParkarLogger.traceLeave();
		APIResponse apiResponse= new APIResponse(response);
		return apiResponse;
	}

	/**
	 * This method generates URI with path and/or query parameters. Input
	 * HashMap with have both path and query parameters. Replaces the parameters
	 * with the {} in URI. map - {name=tom, id=123, age=32} Ex :
	 * /hello/world/{id}/{name} generated URI - /hello/world/123/tom?age=32
	 * 
	 * @param URI: String, the URL
	 * @param map: HashMap
	 * @return res:URI
	 */

	private String generateURIWithParameters(String URI, Map<String, Object> map) {
		ParkarLogger.traceEnter();
		String generatedURI = null;
		try {
			Set<String> keys = map.keySet();
			Iterator<String> it = keys.iterator();
			HashMap<String, Object> pathParams = new HashMap<String, Object>();
			HashMap<String, Object> queryParams = new HashMap<String, Object>();

			while (it.hasNext()) {
				String mapKeys = it.next().toString();
				if (URI.contains(mapKeys) && URI.contains("{" + mapKeys + "}")) {
					pathParams.put(mapKeys, map.get(mapKeys));
				} else {
					queryParams.put(mapKeys, map.get(mapKeys));
				}
			}
			generatedURI = getPathParameters(pathParams, URI);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

			Set<String> pathParamsKeys = queryParams.keySet();
			it = pathParamsKeys.iterator();
			while (it.hasNext()) {
				String pathParamsKey = it.next().toString();
				nameValuePairs.add(new BasicNameValuePair(pathParamsKey, queryParams.get(pathParamsKey).toString()));
			}

			URIBuilder builder = new URIBuilder().setPath(generatedURI).setParameters(nameValuePairs);

			generatedURI = getPathParameters(pathParams, builder.build().toString());
			int size=generatedURI.length();
			if(generatedURI.charAt(size-1)=='?'){
				generatedURI=(String) generatedURI.subSequence(0, size-1);
			}
			logger.info("Generated URI is : " + generatedURI);
		} catch (Exception e) {
			logger.equals("Exception occured while generating URI with path paramters . Generated URI is : "
					+ generatedURI + "  Exception occured is due to : " + e.getMessage());
		}
		ParkarLogger.traceLeave();
		return generatedURI;

	}

	/**
	 * This method removes the map entries if the values are blank and returns
	 * only if the keys have values
	 * 
	 * @param map: HashMap
	 * @return map
	 */
	private HashMap<String, Object> getMapWithParameters(Map<String, Object> map) {
		ParkarLogger.traceEnter();
		HashMap<String, Object> temp = new HashMap<String, Object>();
		try {
			Set<String> keys = map.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String k = it.next();
				if (!map.get(k).equals("")) {
					temp.put(k, map.get(k));
				}
			}
		} catch (Exception e) {
			logger.error("Exception occured while removing the empty values from the map. " + e.getMessage());
		}
		ParkarLogger.traceLeave();
		return temp;

	}
}