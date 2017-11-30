package com.parkar.api.rest.operations;

import static com.jayway.restassured.RestAssured.given;

import java.util.Map;

import org.testng.log4testng.Logger;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.parkar.logging.ParkarLogger;
import com.parkar.api.rest.IAPIOperations;
import com.parkar.api.rest.exception.ParkarCoreAPIException;

public class APIOperations implements IAPIOperations {

	public static Response Login_respone = null;
	public static String contentType = "application/json";
	final static Logger logger = Logger.getLogger(APIOperations.class);

	/**
	 * This method uses query parameters with method type as post to perform a POST request to URI. 
	 * 
	 * @param authToken
	 * 			: String
	 * @param queryParameters
	 *            :Map
	 * 
	 * @param URI
	 *            :endpoint
	 * @return response
	 */
	@Override
	public Response postWithQueryParameters(String authToken, Map<String, ?> queryParameters, String URI)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = setAuthToken(authToken).parameters(queryParameters).when().post(URI);
			String infoMessage = "postWithQueryParameters: \"  URI is " + URI + " with Query parameters "
					+ queryParameters + " and authToken " + authToken + " with response received as " + res.asString();
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "postWithQueryParameters: \"  URI is " + URI + " with Query parameters " + queryParameters
					+ " and authToken " + authToken + " failed with response received as " + res.asString();
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;
	}
	
	
	/**
	 * This method takes Headers parameters with method type post.
	 * 
	 * @param headerParameters
	 *            :Map
	 * 
	 * @param URI
	 *            :endpoint
	 * @return response
	 */
	@Override
	public Response postWithHeaderParameters(Map<String, ?> headerParameters, String URI)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = given()
				    .headers(headerParameters)					 
				    .accept(ContentType.JSON)
				    .contentType(ContentType.JSON)
				    .post(URI)
				    ;
			String infoMessage = "postWithHeaderParameters: \"  URI is " + URI + " with Header parameters "
					+ headerParameters +  "  response received as " + res.asString();
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "postWithHeaderParameters: \"  URI is " + URI + " with Header parameters " + headerParameters
					+ " failed and response received as " + res.asString();
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;
	}

	/**
	 * This method sets base URL.
	 * @param baseUrl: String
	 */
	@Override
	public void setBaseURL(String baseUrl) {
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = baseUrl;
		String infoMessage = "setBaseURL: \"  base URI  " + RestAssured.baseURI;
		logger.info(infoMessage);

	}

	/**
	 * This method is for setting cookie in the headers
	 * 
	 * @param cookie: String
	 * @return cookie
	 */
	@Override
	public RequestSpecification setAuthToken(String cookie) {
		return given().headers("token_key", cookie);
	}

	/**
	 * This method is for setting form/request body parameters
	 * 
	 * @param formParametersJson: String
	 * @return requestSpec:
	 */
	@Override
	public RequestSpecification setFormParameters(String formParametersJson) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		RequestSpecification requestSpec = null;
		try {
			String APIBody = formParametersJson;
			RequestSpecBuilder builder = new RequestSpecBuilder();
			builder.setBody(APIBody);
			builder.setContentType(ContentType.JSON);
			requestSpec = builder.build();
			requestSpec.log();

			String infoMessage = "setFormParameters: \" set form parameters using json " + formParametersJson
					+ " request specification is " + requestSpec;
			logger.info(infoMessage);
       } catch (Exception e) {
			String errMsg = "setFormParameters: \"  set form parameters using json " + formParametersJson
					+ " failed with request specification " + requestSpec;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		;
		return requestSpec;

	}

	/**
	 * This method is used to perform POST operation with json request body and with the cookies set in the headers
	 * perform a POST request to URI. 
	 * 
	 * @param authToken: String
	 * @param formParametersJson: String
	 * @param URI
	 *            : String
	 * @param contentType: String
	 * @return res:response
	 */
	@Override
	public Response postWithFormParameters(String authToken, String formParametersJson, String URI, String contentType)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = setAuthToken(authToken).contentType(contentType).accept(ContentType.JSON)
					.spec(setFormParameters(formParametersJson)).when().post(URI);

			String infoMessage = "postWithFormParameters: \" post from parameters using json " + formParametersJson
					+ " using authToken  " + authToken + " using URI " + URI + " with contentType " + contentType
					+ " and received response is " + res;
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "postWithFormParameters: \" post from parameters using json " + formParametersJson
					+ " using authToken " + authToken + " using URI " + URI + " with contentType " + contentType
					+ " failed and received response is " + res;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;

	}

	/**
	 * This method is used to perform POST operation with json request body, content-type as JSON and with the cookies set in the headers
	 * perform a POST request to URI. 
	 * 
	 * @param authToken: String
	 * @param formParametersJson: String
	 * @param URI
	 *            : String
	 * @return res:response
	 */
	@Override
	public Response postWithFormParameters(String authToken, String formParametersJson, String URI)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = setAuthToken(authToken).accept(ContentType.JSON).spec(setFormParameters(formParametersJson)).when()
					.post(URI);

			String infoMessage = "postWithFormParameters: \" post from parameters using json " + formParametersJson
					+ " using authToken " + authToken + " and URI " + URI + " and received response is " + res;
			logger.info(infoMessage);
		} catch (Exception e) {
            String errMsg = "postWithFormParameters: \" post from parameters using json " + formParametersJson
					+ " using authToken " + authToken + " and URI " + URI + " failed and received response is " + res;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;
	}

	/**
	 * This method is used to perform GET operation with query parameters and with the cookies set in the headers
	 * perform a GET request to URI.
	 * 
	 * @param authToken: String
	 * @param queryParameters
	 *            :HashMap
	 * @param URI
	 *            : String
	 * @return response
	 */
	@Override
	public Response getWithQueryparameters(String authToken, Map<String, ?> queryParameters, String URI)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = setAuthToken(authToken).parameters(queryParameters).when().get(URI);
			String infoMessage = "getWithQueryparameters: \" queryParameters as " + queryParameters
					+ " using authToken " + authToken + " and URI " + URI + " and received response is " + res;
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "getWithQueryparameters: \" queryParameters as " + queryParameters + " using authToken "
					+ authToken + " and URI " + URI + " failed and received response is " + res;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;

	}

	/**
	 * This method is used to perform PUT operation with query parameters and with the cookies set in the headers
	 *  
	 * @param authToken: String
	 * @param queryParameters
	 *            :Map
	 * @param URI
	 *            :endpoint
	 * @return response
	 */
	@Override
	public Response putWithQueryparameters(String authToken, Map<String, ?> queryParameters, String URI)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = setAuthToken(authToken).parameters(queryParameters).when().put(URI);
			String infoMessage = "putWithQueryparameters: \" queryParameters as " + queryParameters
					+ " using authToken " + authToken + " and URI " + URI + " and received response as " + res;
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "putWithQueryparameters: \" queryParameters as " + queryParameters + " using authToken "
					+ authToken + " and URI " + URI + " failed with received response as " + res;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;
	}

	/**
	 * This method is used to perform GET operation with path parameters and with the cookies set in the headers
	 * 
	 * @param authToken: String
	 * @param URI
	 *            :endpoint
	 * @return response
	 */
	@Override
	public Response getWithPathParameters(String authToken, String URI) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = setAuthToken(authToken).when().log().all().get(URI);
			String infoMessage = "getWithPathParameters: \" with authToken as " + authToken + " and URI " + URI
					+ " and received response as " + res;
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "getWithPathParameters: \" with authToken as " + authToken + " and URI " + URI
					+ " failed with received response as " + res;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;
	}

	/**
	 * This method is used to perform GET operation with no parameters and with the cookies set in the headers
	 * 
	 * @param authToken: String
	 * @param URI
	 *            : String
	 * @return response
	 */
	@Override
	public Response getWithNoParameters(String authToken, String URI) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = setAuthToken(authToken).get(URI);
			String infoMessage = "getWithNoParameters: \" with authToken as " + authToken + " and URI " + URI
					+ " and received response as " + res;
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "getWithNoParameters: \" with no parameters " + " using authToken " + authToken
					+ " and URI " + URI + " failed with received response as " + res;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;
	}

	/**
	 * This method is used to perform PUT operation with json request body , content-type as JSON and with the cookies set in the headers
	 *  
	 * @param authToken: String
	 * @param formParametersJson: String
	 * @param URI
	 *            : String
	 * @return res:response
	 */
	@Override
	public Response putWithFormParameters(String authToken, String formParametersJson, String URI)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = setAuthToken(authToken).accept(ContentType.JSON).spec(setFormParameters(formParametersJson)).when()
					.put(URI);
			String infoMessage = "putWithFormParameters: \" with form parameters json as " + formParametersJson
					+ " using authToken " + authToken + " and URI " + URI + " and received response as " + res;
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "putWithFormParameters: \" with form parameters json as " + formParametersJson
					+ " using authToken " + authToken + " and URI " + URI + " failed with received response as " + res;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;
	}

	/**
	 * This method is used to perform PUT operation with path parameters and with the cookies set in the headers
	 * perform a PUT request to URI.
	 * 
	 * @param authToken: String
	 * @param URI
	 *            : String
	 * @return res:response
	 */
	@Override
	public Response putWithPathParameters(String authToken, String URI) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = setAuthToken(authToken).when().put(URI);
			String infoMessage = "putWithFormParameters: \" using authToken " + authToken + " and URI " + URI
					+ " and received response as " + res;
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "putWithFormParameters: \" using authToken " + authToken + " and URI " + URI
					+ " failed with received response as " + res;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;
	}

	/**
	 * This method is used to perform DELETE operation with path parameters and with the cookies set in the headers
	 * 
	 * @param authToken: String
	 * @param URI
	 *            : String
	 * @return response
	 */
	@Override
	public Response deleteWithPathParameters(String authToken, String URI) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = setAuthToken(authToken).when().delete(URI);
			String infoMessage = "deleteWithPathParameters: \" using authToken " + authToken + " and URI " + URI
					+ " and received response " + res;
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "deleteWithPathParameters: \" using authToken " + authToken + " and URI " + URI
					+ " failed and received response " + res;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;
	}

	/**
	 * This method is used to set the json request body in the request
	 * 
	 * @return requestspec
	 */
	@Override
	public RequestSpecification setFormParameters() throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		RequestSpecification requestSpec = null;
		try {
			RequestSpecBuilder builder = new RequestSpecBuilder();
			builder.setContentType("application/json; charset=UTF-8");
			requestSpec = builder.build();
			requestSpec.log().all();
			String infoMessage = "setFormParameters: \" request specification " + requestSpec;
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "setFormParameters: \" request specification " + requestSpec;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return requestSpec;
	}

	/**
	 * This method is used to perform POST operation with path parameters and with the cookies set in the headers
	 * 
	 * @param authToken: String
	 * @param URI
	 *            : String
	 * @return res:response
	 */
	@Override
	public Response postWithPathParameters(String authToken, String URI) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = setAuthToken(authToken).spec(setFormParameters()).when().post(URI);
			String infoMessage = "postWithPathParameters: \" using authToken " + authToken + " with URI " + URI
					+ " response recevied is " + res;
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "postWithPathParameters: \" using authToken " + authToken + " with URI " + URI
					+ " failed with response as " + res;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;
	}

	/**
	 * This method is used to perform DELETE operation with query parameters and with the cookies set in the headers
     *
	 * 
	 * @param authToken: String
	 * @param queryParameters
	 *            :Map
	 * @param URI
	 *            : String
	 * @return res:response
	 */
	@Override
	public Response deleteWithQueryParameters(String authToken, Map<String, ?> queryParameters, String URI)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Response res = null;
		try {
			res = setAuthToken(authToken).parameters(queryParameters).when().delete(URI);
			String infoMessage = "deleteWithQueryParameters: \" using authToken " + authToken + " with URI " + URI
					+ " and query parameters " + queryParameters + " response recevied is " + res;
			logger.info(infoMessage);
		} catch (Exception e) {
			String errMsg = "deleteWithQueryParameters: \" using authToken " + authToken + " with URI " + URI
					+ " and query parameters " + queryParameters + " failed with response as " + res;
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return res;
	}

}
