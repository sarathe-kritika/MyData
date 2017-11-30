package com.parkar.api.rest;

import java.util.Map;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.parkar.api.rest.exception.ParkarCoreAPIException;

public interface IAPIOperations {

	public  Response postWithQueryParameters(String authToken, Map<String, ?> queryParameters, String URI)
			throws ParkarCoreAPIException;

	public  RequestSpecification setAuthToken(String authToken) throws ParkarCoreAPIException;

	public  RequestSpecification setFormParameters(String formParametersJson) throws ParkarCoreAPIException;

	public  Response postWithFormParameters(String authToken, String formParametersJson, String URI,
			String contentType) throws ParkarCoreAPIException;

	public  Response postWithFormParameters(String authToken, String formParametersJson, String URI)
			throws ParkarCoreAPIException;

	public  Response getWithQueryparameters(String authToken, Map<String, ?> queryParameters, String URI)
			throws ParkarCoreAPIException;

	public  Response putWithQueryparameters(String authToken, Map<String, ?> queryParameters, String URI)
			throws ParkarCoreAPIException;

	public  Response getWithPathParameters(String authToken, String URI) throws ParkarCoreAPIException;

	public  Response getWithNoParameters(String authToken, String URI) throws ParkarCoreAPIException;

	public  Response putWithFormParameters(String authToken, String formParametersJson, String URI)
			throws ParkarCoreAPIException;

	public  Response putWithPathParameters(String authToken, String URI) throws ParkarCoreAPIException;

	public  Response deleteWithPathParameters(String authToken, String URI) throws ParkarCoreAPIException;

	public  RequestSpecification setFormParameters() throws ParkarCoreAPIException;

	public  Response postWithPathParameters(String authToken, String URI) throws ParkarCoreAPIException;

	public  Response deleteWithQueryParameters(String authToken, Map<String, ?> queryParameters, String URI)
			throws ParkarCoreAPIException;

	public void setBaseURL(String baseUrl);

	public Response postWithHeaderParameters(Map<String, ?> headerParameters, String URI) throws ParkarCoreAPIException;

}
