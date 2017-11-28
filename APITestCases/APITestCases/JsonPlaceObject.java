package com.parkar.business;

import com.parkar.api.rest.driver.APIDriver;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.api.rest.operations.APIResponse;

public class JsonPlaceObject extends BaseAPIObject{

	public String orderId = "";
	public static final String SCHEMA = "createOrder.json";
	public static final String END_POINT = "/posts";
	public static final String POST_ORDER_URI = END_POINT;
	public static final String GET_ORDER_URI = END_POINT;
	public static final String GET_ORDER_SINGLE_URI = END_POINT;
	public static final String PUT_ORDER_URI = END_POINT;
	public static final String DELETE_ORDER_URI = END_POINT + "/{id}";

	public JsonPlaceObject(APIDriver apiDriver) {
		super(apiDriver);
		// TODO Auto-generated constructor stub
	}
	

	public APIResponse getList() throws ParkarCoreAPIException {
		return get(GET_ORDER_URI);
	}

}
