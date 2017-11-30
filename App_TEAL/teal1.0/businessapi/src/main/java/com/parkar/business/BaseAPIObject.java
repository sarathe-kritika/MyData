package com.parkar.business;

import static com.parkar.api.rest.json.JsonBuildHelper.build;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.log4testng.Logger;

import com.parkar.api.rest.auth.AuthN;
import com.parkar.api.rest.base.BaseAPITest;
import com.parkar.api.rest.driver.APIDriver;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.api.rest.json.Request;
import com.parkar.api.rest.json.RequestBuilder;
import com.parkar.api.rest.operations.APIOperations;
import com.parkar.api.rest.operations.APIResponse;
import com.parkar.logging.ParkarLogger;
import com.parkar.testng.Configurator;

public abstract class BaseAPIObject {
	public static final List<String> GENERIC_PARAMETRS = new ArrayList<String>(Arrays.asList("tenantId"));
	final static Logger logger = Logger.getLogger(BaseAPIObject.class);

	private static final Configurator configurator = Configurator.getInstance();
	private APIDriver apiDriver;
	private APIOperations apiOps;
	protected Map<String, Object> parameters = new HashMap<String, Object>();

	/**
	 * Constructor to initialize apidriver object
	 * 
	 * @param apiDriver:APIDriver
	 * 
	 */
	public BaseAPIObject(APIDriver apiDriver) {
		this.apiDriver = apiDriver;
	}

	/**
	 * This method to login to app, it sets the globalcookie internally
	 * 
	 * @param userName:String
	 * 
	 * @param password:String
	 * 
	 */
	public void login(String userName, String password) {
		apiOps = new APIOperations();
		apiDriver.GLOBAL_COOKIE = AuthN.doLogin(apiOps, userName, password, BaseAPITest.BASE_URI+"/login");
	}

	/**
	 * This method gets all the generic parameters in the map as keys and empty
	 * values with tenant id fetched set from the testng.xml file
	 * 
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 */
	public void setGenericParameter() throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		parameters.clear();
		try {
			Iterator<String> gen = GENERIC_PARAMETRS.iterator();
			while (gen.hasNext()) {
				String key = gen.next().toString();
				parameters.put(key, "");
			}
		} catch (Exception e) {
			logger.error(
					"Exception occured while generating a map with keys for generic parameters .  " + e.getMessage());
			throw new ParkarCoreAPIException(e.getMessage(), e);
		}
		ParkarLogger.traceLeave();
		parameters.put("tenantId", configurator.getParameter("Tenant"));
	}

	/**
	 * This method is to perform POST with a json request body with the
	 * specified URI. Input JsonRequest will be treated as request body
	 * 
	 * @param requestJson:
	 *            Request
	 * @param URI:
	 *            String
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * @return apiResponse: APIResponse
	 */
	public APIResponse post(String URI, Request requestJson) throws ParkarCoreAPIException {
		APIResponse apiResponse = apiDriver.execute(URI, requestJson);
		return apiResponse;
	}

	/**
	 * This method is to perform POST with query and/or path parameters and the
	 * specified URI.
	 * 
	 * @param URI:
	 *            String
	 * 
	 * @param parameters:
	 *            Map
	 * 
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * 
	 * @return apiResponse: APIResponse
	 */
	public APIResponse post(String URI, Map<String, Object> parameters) throws ParkarCoreAPIException {
		APIResponse apiResponse = apiDriver.execute(URI, parameters);
		return apiResponse;
	}

	/**
	 * This method is to perform POST with a json request body , query and/or
	 * path parameters and the specified URI. Input JsonRequest will be treated
	 * as request body
	 * 
	 * @param URI:
	 *            String
	 * 
	 * @param requestJson:
	 *            Request
	 * 
	 * @param parameters:
	 *            Map
	 * 
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * 
	 * @return apiResponse: APIResponse
	 */
	public APIResponse post(String URI, Request requestJson, Map<String, Object> parameters)
			throws ParkarCoreAPIException {
		APIResponse apiResponse = apiDriver.execute(URI, requestJson, parameters);
		return apiResponse;
	}

	/**
	 * This method is to perform GET with a specified URI.
	 *
	 * @param URI:
	 *            String
	 * 
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * 
	 * @return apiResponse: APIResponse
	 */
	public APIResponse get(String URI) throws ParkarCoreAPIException {
		APIResponse apiResponse = apiDriver.execute(URI);
		return apiResponse;
	}

	/**
	 * This method is to perform GET with query and/or path parameters and a
	 * specified URI.
	 * 
	 * @param URI:
	 *            String
	 * 
	 * @param parameters:Map
	 * 
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * 
	 * @return apiResponse: APIResponse
	 */
	public APIResponse get(String URI, Map<String, Object> parameters) throws ParkarCoreAPIException {
		APIResponse apiResponse = apiDriver.execute(URI, parameters);
		return apiResponse;
	}

	/**
	 * This method is to perform PUT with query and/or path parameters and the
	 * specified URI.
	 * 
	 * @param URI:
	 *            String
	 * 
	 * @param parameters:
	 *            Map
	 * 
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * 
	 * @return apiResponse: APIResponse
	 */
	public APIResponse put(String URI, Map<String, Object> parameters) throws ParkarCoreAPIException {
		APIResponse apiResponse = apiDriver.execute(URI, parameters);
		return apiResponse;
	}

	/**
	 * This method is to perform PUT with a json request body with the specified
	 * URI. Input JsonRequest will be treated as request body
	 * 
	 * @param requestJson:
	 *            Request
	 * @param URI:
	 *            String
	 * 
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * 
	 * @return apiResponse: APIResponse
	 */
	public APIResponse put(String URI,Request requestJson) throws ParkarCoreAPIException {
		APIResponse apiResponse = apiDriver.execute(URI, requestJson);
		return apiResponse;
	}

	/**
	 * This method is to perform PUT with a json request body , query and/or
	 * path parameters and the specified URI. Input JsonRequest will be treated
	 * as request body
	 * 
	 * @param URI:
	 *            String
	 * 
	 * @param requestJson:
	 *            Request
	 * 
	 * @param parameters:
	 *            Map
	 * 
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * 
	 * @return apiResponse: APIResponse
	 */
	public APIResponse put(String URI, Request requestJson, Map<String, Object> parameters)
			throws ParkarCoreAPIException {
		APIResponse apiResponse = apiDriver.execute(URI, requestJson, parameters);
		return apiResponse;
	}

	/**
	 * This method is to perform DELETE with query and/or path parameters and
	 * the specified URI.
	 * 
	 * @param URI:
	 *            String
	 * 
	 * @param parameters:
	 *            Map
	 * 
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * 
	 * @return apiResponse: APIResponse
	 */
	public APIResponse delete(String URI, Map<String, Object> parameters) throws ParkarCoreAPIException {
		APIResponse apiResponse = apiDriver.execute(URI, parameters);
		return apiResponse;
	}

	/**
	 * This method is to remove all keys which have empty values.
	 * 
	 * @param map:
	 *            Map
	 * 
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * 
	 * @return newMap: Map
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> removeUnusedKey(Map<String, Object> map) throws ParkarCoreAPIException {
		Map<String, Object> newMap = new LinkedHashMap<String, Object>();
		for (String key : map.keySet()) {
			if (map.get(key) instanceof Map) {
				Map<String, Object> temp = removeUnusedKey((Map<String, Object>) map.get(key));
				if (temp != null) {
					newMap.put(key, temp);
				}
			} else if (map.get(key) instanceof List) {
				if (((List<?>) map.get(key)).get(0) instanceof Map) {
					List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
					for (Object o : (List<?>) map.get(key)) {
						Map<String, Object> temp = removeUnusedKey((Map<String, Object>) o);
						if (temp != null) {
							tempList.add(temp);
						}
					}
					if (tempList.size() != 0) {
						newMap.put(key, tempList);
					}
				} else {
					newMap.put(key, map.get(key));
				}
			} else {
				if (map.get(key) != null && !map.get(key).toString().isEmpty()) {
					newMap.put(key, map.get(key));
				}
			}
		}
		return newMap.size() == 0 ? null : newMap;
	}

	/**
	 * This method loads the schema file from the projects resource directory
	 * present at the root level and returns the file path.
	 * 
	 * @param schemaLocation:
	 *            String
	 * 
	 * @return schema:String
	 */
	protected String loadSchema(String schemaLocation) {
		String schema = configurator.getDirectoryParameter("root") + File.separator + "resources" + File.separator + schemaLocation;
		return schema;
	}

	/**
	 * This method loads the schema file from the projects resource directory
	 * present at the root level and generates the json with the data from the
	 * map.
	 * 
	 * @param schema:
	 *            String
	 * 
	 * @param map:
	 *            Map
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * 
	 * @return Request
	 */
	public Request buildPayLoad(String schema, Map<String, Object> map) throws ParkarCoreAPIException {
		return build(loadSchema(schema), map);
	}
	
	/**
	 * This method loads the schema file from the projects resource directory present at the root level and 
		generates the json with the data from the Hierarchical CSV files.
     *   
	 * @param schema: Schema file
	 * 
	 * @param csvFile : Parent CSV file
	 * @param rowToRead: csv row
	 * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * @return Request
	 */
	public Request buildPayLoad(String schema, String csvFile, String rowToRead) throws ParkarCoreAPIException{
		RequestBuilder reqGen = new RequestBuilder();
		return (reqGen.build(loadSchema(schema), csvFile, rowToRead));
	}
	
	/**
	 * This method generates the json with the data from the map.
	 * 
	 * @param map:
	 *            Map
     * @throws ParkarCoreAPIException
	 *             : parkar core API exception
	 * 
	 * @return Request
	 */
	public Request buildPayLoad(Map<String, Object> map) throws ParkarCoreAPIException {
		return build(map);
	}
}
