package com.parkar.api.rest.operations;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.parkar.logging.ParkarLogger;
import com.parkar.api.rest.exception.ParkarCoreAPIException;

public class APIResponse  {

	final static Logger logger = Logger.getLogger(APIResponse.class);
	public static final String BREAK_LINE = "</br>";
	
	protected final Response res;
	JsonUtils jsonUtils = new JsonUtils();
	
	public APIResponse(Response res1) {
		this.res = res1;
	}

	/**
	 * This method is for getting the HTTP status code of the response.
	 * 
	 * @return: actualStatusCode:int
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public int getStatusCode() throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		int actualStatusCode = 0;
		String infoMessage = "";
		try {
			actualStatusCode = res.getStatusCode();
			infoMessage = infoMessage + BREAK_LINE + " A: " + actualStatusCode;
			logger.info(infoMessage);
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceLeave();
		return actualStatusCode;
	}

	/**
	 * This method is for getting node(jsonPath) value in the request json.
	 * 
	 * @param jsonPath:String
	 * @return Object:nodeValue
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public Object getNodeValue(String jsonPath) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Object obj = null;
		String infoMessage = "getNodevalue " + BREAK_LINE + " JsonPath: " + jsonPath;
		try {
			String json = res.asString();
			obj = JsonPath.with(json).get(jsonPath);
			infoMessage = infoMessage + BREAK_LINE + " Received value: " + obj;
			logger.info(infoMessage);
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceLeave();
		return obj;
	}

	/**
	 * This method is for getting node values for the matching jsonPath.
	 * 
	 * @param jsonPath:String
	 * @return list:List
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public List<?> getNodeValues(String jsonPath) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		List<?> list = null;
		String infoMessage = "getNodevalues " + BREAK_LINE + " JsonPath: " + jsonPath;
		try {
			String json = res.asString();
			list = JsonPath.with(json).get(jsonPath);
			infoMessage = infoMessage + BREAK_LINE + "Received values are: " + list;
			logger.info(infoMessage);
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceLeave();
		return list;
	}

	/**
	 * This method verifies if presence of node(jsonPath) in the response json
	 *  
	 * @param jsonPath:String
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: throws core API exception
	 */
	public boolean isNodePresent(String jsonPath) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		JsonPath j = new JsonPath(res.asString());
		Boolean value = false;
		String infoMessage = "isNodePresent " + BREAK_LINE + "JsonPath:" + jsonPath;
		logger.info(infoMessage);
		try {
			if (j.get(jsonPath).getClass().equals(ArrayList.class)) {

				ArrayList<Object> jsonList = j.getJsonObject(jsonPath);
				for (Object obj : jsonList) {
					if (obj != null) {
						value = true;
						break;
					} else
						value = false;
				}
			} else {
				value = (j.get(jsonPath).toString() != null);
			}
		} catch (Exception e) {
			logger.error(infoMessage, e);
			value = false;
		}
		ParkarLogger.traceLeave();
		return (value);
	}

	/**
	 * This method verifies if the value at the jsonPath/node is as per the expected regexPattern.
	 * 
	 * @param jsonPath:String
	 * @param regex:String
	 * @return expectedRegexMatch:boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public boolean getJsonPathRegEx(String jsonPath, String regex) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		String value = null;
		boolean expectedRegexMatch = false;
		String infoMessage = "getJsonPathRegEx " + BREAK_LINE + " JsonPath: " + jsonPath + BREAK_LINE + " Regex: "
				+ regex;
		try {
			value = getNodeValue(jsonPath).toString();
			expectedRegexMatch = Pattern.compile(regex).matcher(value).find();
			if (expectedRegexMatch) {
				infoMessage = infoMessage + BREAK_LINE + " Value:" + value + "matched";
				logger.info(infoMessage);
			} else {
				infoMessage = infoMessage + BREAK_LINE + "Value:" + value + "did not matched";
				logger.info(infoMessage);
			}
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceLeave();
		return expectedRegexMatch;
	}

	/**
	 * This method is for getting the count of nodes/jsonPath in the response.
	 * 
	 * @param jsonPath:String
	 * @return count:int
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public int getJsonPathCount(String jsonPath) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		int count = 0;
		String infoMessage = "getJsonPathCount " + BREAK_LINE + " JsonPath: " + jsonPath;
		try {
			count = getNodeValues(jsonPath).size();
			infoMessage = infoMessage + BREAK_LINE + " Received count is " + count;
			logger.info(infoMessage);

		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceLeave();
		return count;
	}

	/**
	 * This method is for validating the response against the JsonSchema present in the classpath.
	 * 
	 * @param jsonSchema:String
	 * @return assertJsonSchema:boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public boolean jsonSchemaValidation(String jsonSchema) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		boolean assertJsonSchema = false;
		String infoMessage = "JsonSchemaValidation " + BREAK_LINE + " JsonSchema: " + jsonSchema;
		try {
			res.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchema));
			logger.info(infoMessage);
			assertJsonSchema = true;
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceLeave();
		return assertJsonSchema;
	}

	/**
	 * This method is for verifying if expected value exists in particular response for the jsonPath.
	 * 
	 * @param value:Object
	 * @param jsonPath:String
	 * @return contains:boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public boolean isInList(String jsonPath, Object value) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		List<?> li = getNodeValues(jsonPath);
		boolean contains = false;
		String infoMessage = "isInList" + BREAK_LINE + " Inside list values: " + li;
		try {
			if (li.contains(value)) {
				infoMessage = infoMessage + BREAK_LINE + "Expected value : " + value + " found ";
				logger.info(infoMessage);
				contains = true;
			} else {
				infoMessage = infoMessage + BREAK_LINE + "Expected value : " + value + "not found ";
				logger.info(infoMessage);
			}
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceLeave();
		return contains;
	}

	/**
	 * This method verifies if the actual status code is present in the given list
	 * of expected status codes.
	 * 
	 * @param expectedStatusCode:List
	 * @return contains :boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public boolean isInListOfHTTPCodes(List<?> expectedStatusCode) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		int actualStatusCode = 0;
		boolean contains = false;
		String infoMessage = "isInListOfHTTPCodes" + BREAK_LINE + "From expected list of status codes : "
				+ expectedStatusCode;
		try {
			actualStatusCode = res.getStatusCode();
			if (expectedStatusCode.contains(actualStatusCode)) {
				infoMessage = infoMessage + BREAK_LINE + "Actual status code: " + actualStatusCode + "found ";
				logger.info(infoMessage);
				contains = true;
			} else {
				infoMessage = infoMessage + BREAK_LINE + "Actual status code: " + actualStatusCode + "not found ";
				logger.info(infoMessage);
			}
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceLeave();
		return contains;
	}

	/**
	 * This method is for validating if response contains string .
	 * 
	 * @param valueToFind:String,that
	 *            need to be searched in the response body
	 * @return containsString:boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public boolean contains(String valueToFind) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		boolean containString = false;
		String infoMessage = "";
		try {
			String response = res.asString();
			if (StringUtils.contains(response, valueToFind) && StringUtils.containsIgnoreCase(response, valueToFind)) {
				infoMessage = "ContainsString " + BREAK_LINE + "Contains expected string in response : " + valueToFind;
				logger.info(infoMessage);
				containString = true;
			} else {
				infoMessage = "Containsstring " + BREAK_LINE + "Does not Contains string in response : " + valueToFind;
				logger.info(infoMessage);
			}
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceLeave();
		return containString;
	}

	/**
	 * This method is for verifying if JSON Array present at jsonPath in
	 * response is sorted according to a field as per sort order given by user.
	 * 
	 * @param jsonPath:String
	 * @param fieldName:String
	 * @param isAscOrder:boolean
	 * @return flag:boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public boolean isJsonSort(String jsonPath, String fieldName, boolean isAscOrder) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		boolean flag = false;
		String infoMessage = "sortJson " + BREAK_LINE + " JsonPath: " + jsonPath + BREAK_LINE + " FieldName: "
				+ fieldName + BREAK_LINE + "AscendingOrder: " + isAscOrder;
		try {
			List<?> valuesFromJsonPath = getNodeValues(jsonPath);
			JSONArray jsonArr = new JSONArray(valuesFromJsonPath);
			logger.info(infoMessage);
			if (jsonUtils.verifyJsonSort(jsonArr, fieldName, isAscOrder)) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceLeave();
		return flag;
	}

	/***
	 * This method is for verifying json compare as per expected json given by
	 * the user.
	 * 
	 * @param expectedJson:String
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public boolean isJSONCompare(String expectedJson) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		ValidationResults jsonCompareResult = new ValidationResults(false);
		String infoMessage = "isJSONCompare " + BREAK_LINE + "E: " + expectedJson;
		try {
			jsonCompareResult = jsonUtils.jsonCompare(res, expectedJson);
			if (jsonCompareResult.isPass()) {
				logger.info(infoMessage);
			} else {
				infoMessage = infoMessage + BREAK_LINE + "Failure Reason: " + jsonCompareResult.getFailedReason();
				logger.info(infoMessage);
			}
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceEnter();
		return jsonCompareResult.isPass();
	}

	/***
	 * This method is for verifying json compare as per expected json file given
	 * by the user.
	 * 
	 * @param expectedJsonFile:
	 *            File
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public boolean isJSONCompare(File expectedJsonFile) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		ValidationResults jsonCompareResult = new ValidationResults(false);
		String expectedJson = "";
		String infoMessage = "";
		try {
			expectedJson = FileUtils.readFileToString(expectedJsonFile, "UTF-8");
			jsonCompareResult = jsonUtils.jsonCompare(res, expectedJson);
			infoMessage = "isJSONCompare " + BREAK_LINE + " E: " + expectedJson;
			if (jsonCompareResult.isPass()) {
				logger.info(infoMessage);
			} else {
				infoMessage = infoMessage + BREAK_LINE + "Failure Reason: " + jsonCompareResult.getFailedReason();
				logger.info(infoMessage);
			}
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceEnter();
		return jsonCompareResult.isPass();
	}

	/***
	 * This method is for verifying json contains as per expected json given by
	 * the user.
	 * 
	 * @param expectedJson:String
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public boolean isJSONContains(String expectedJson) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		ValidationResults jsonContainResult = new ValidationResults(false);
		String infoMessage = "isJSONContains " + BREAK_LINE + " E: " + expectedJson;
		try {
			jsonContainResult = jsonUtils.jsonContains(res, expectedJson);
			if (jsonContainResult.isPass()) {
				logger.info(infoMessage);
			} else {
				infoMessage = infoMessage + BREAK_LINE + "Failure Reason: " + jsonContainResult.getFailedReason();
				logger.info(infoMessage);
			}
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceLeave();
		return jsonContainResult.isPass();
	}

	/***
	 * This method is for verifying json contains as per expected json File
	 * given by the user.
	 * 
	 * @param expectedJsonfile:
	 *            File
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: krnos core API exception
	 */
	public boolean isJSONContains(File expectedJsonfile) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		ValidationResults jsonContainResult = new ValidationResults(false);
		String expectedJson = "";
		String infoMessage = "";
		try {
			expectedJson = FileUtils.readFileToString(expectedJsonfile, "UTF-8");
			jsonContainResult = jsonUtils.jsonContains(res, expectedJson);
			infoMessage = "isJSONContains  " + BREAK_LINE + " E: " + expectedJson;
			if (jsonContainResult.isPass()) {
				logger.info(infoMessage);
			} else {
				infoMessage = infoMessage + BREAK_LINE + "Failure Reason: " + jsonContainResult.getFailedReason();
				logger.info(infoMessage);
			}
		} catch (Exception e) {
			logger.error(infoMessage, e);
			throw new ParkarCoreAPIException(infoMessage, e);
		}
		ParkarLogger.traceLeave();
		return jsonContainResult.isPass();
	}

}