package com.parkar.business;

import com.parkar.api.rest.driver.APIDriver;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.api.rest.json.Request;
import com.parkar.api.rest.operations.APIResponse;

public class OrderManagmentAPIObject extends BaseAPIObject {

	public String orderId = "";
	public static final String SCHEMA = "createOrder.json";
	public static final String END_POINT = "/order";
	public static final String POST_ORDER_URI = END_POINT;
	public static final String GET_ORDER_URI = END_POINT;
	public static final String GET_ORDER_SINGLE_URI = END_POINT;
	public static final String PUT_ORDER_URI = END_POINT;
	public static final String DELETE_ORDER_URI = END_POINT + "/{id}";

	public OrderManagmentAPIObject(APIDriver apiDriver) {
		super(apiDriver);
		// TODO Auto-generated constructor stub
	}

	public APIResponse create(Request request) throws ParkarCoreAPIException {
		return post(POST_ORDER_URI, request);
	}

	public APIResponse getList() throws ParkarCoreAPIException {
		return get(GET_ORDER_URI);
	}

	public APIResponse find() throws ParkarCoreAPIException {
		parameters.put("id", this.orderId);
		return get(GET_ORDER_SINGLE_URI, parameters);
	}

	public APIResponse findDelete() throws ParkarCoreAPIException
	{
		parameters.put("id", this.orderId);
		return delete(DELETE_ORDER_URI, parameters);
	}
	
	public APIResponse findUpdate(Request request) throws ParkarCoreAPIException
	{
		parameters.put("id", this.orderId);
		return put(PUT_ORDER_URI, request, parameters);
	}
}
