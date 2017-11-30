package com.parkar.api.rest.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.restassured.path.json.JsonPath;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.logging.ParkarLogger;

public class Request {

	private JsonNode rootNode;
	final static Logger logger = Logger.getLogger(Request.class);
	public static final String BREAK_LINE = "</br>";

	private enum OPER {
		UPDATE, DELETE
	}

	public Request(JsonNode rootNode) {
		super();
		this.rootNode = rootNode;
	}
	
	public Request(String payload) throws ParkarCoreAPIException {
		super();
		ObjectMapper mapper = new ObjectMapper();
		try {
			rootNode = mapper.readTree(payload);
		} catch (IOException e) {
			throw new ParkarCoreAPIException(e.getMessage(),e);
		}
	}
	
	public JsonNode getRequest() {
		return rootNode;
	}
	
	public void setRequest(JsonNode JsonReq) {
		rootNode = JsonReq;
	}

	/*
	 * This method updates the value of any particular node.
	 * 
	 * @param jsonPath:String
	 * 
	 * @Param valuToupdate:String
	 * 
	 * @throws ParkarCoreAPIException : throws core API exception
	 */
	public void updateNodeValue(String jsonPath, Object valueTobeUpdated) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		try {
			String infoMessage = "jsonPath :" + jsonPath + " ,valueTobeUpdated : " + valueTobeUpdated;
			logger.info(infoMessage);
			if (isNodePresent(jsonPath)) {
				findNodeAndPerformOperation(rootNode, jsonPath, valueTobeUpdated, OPER.UPDATE);
				infoMessage = " Updated JsonNode:" + rootNode;
				logger.info(infoMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParkarCoreAPIException(e);
		}
		ParkarLogger.traceLeave();
	}

	/*
	 * This method delete the node.
	 * 
	 * @param jsonPath:String
	 * 
	 * @throws ParkarCoreAPIException : throws core API exception
	 */
	public void deleteNode(String jsonPath) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		try {
			String infoMessage = "JsonPath :" + jsonPath;
			logger.info(infoMessage);
			if (isNodePresent(jsonPath)) {
				findNodeAndPerformOperation(rootNode, jsonPath, "", OPER.DELETE);
				infoMessage = " Updated JsonNode:" + rootNode;
				logger.info(infoMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParkarCoreAPIException(e);
		}
		ParkarLogger.traceLeave();

	}

	/*
	 * This method find the node recursively and update the value of the node .
	 * 
	 * @param node:JsonNode
	 * 
	 * @param path:String
	 * 
	 * @Param valueToBeUpdated :String
	 * 
	 * @Param Oper : OPER
	 * 
	 * @throws ParkarCoreAPIException : throws core API exception
	 */

	private void findNodeAndPerformOperation(JsonNode node, String path, Object valueToBeUpdated, OPER Oper)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		String infoMessage = " node: " + node + ", path:" + path + ", valueToBeUpdated:" + valueToBeUpdated
				+ ", Operation:" + Oper.toString();
		logger.info(infoMessage);

		String tokanizer = "\\.";
		List listOfPath = Arrays.asList(path.split(tokanizer));
		String listLIndetifier = "[";
		String listRIndetifier = "]";
		String firstNode = null;
		int index = -1;

		firstNode = (String) listOfPath.get(0);

		// If firstNode in JsonPath is an array
		if (firstNode.contains(listLIndetifier)) {
			index = Integer.parseInt(
					firstNode.substring(firstNode.indexOf(listLIndetifier) + 1, firstNode.indexOf(listRIndetifier)));
			firstNode = firstNode.substring(0, firstNode.indexOf(listLIndetifier));
		}

		// If JsonPath has more than one level
		if (listOfPath.size() > 1) {
			if (node.get(firstNode) instanceof ArrayNode) {
				if (index == -1) {
					Iterator<JsonNode> itr = node.get(firstNode).elements();
					while (itr.hasNext()) {
						findNodeAndPerformOperation(itr.next(), getNextNodeNameInJsonPath(path), valueToBeUpdated,
								Oper);
					}
				} else
					findNodeAndPerformOperation(node.get(firstNode).get(index), getNextNodeNameInJsonPath(path),
							valueToBeUpdated, Oper);
			} else if (node.get(firstNode) instanceof ObjectNode) {

				findNodeAndPerformOperation(node.get(firstNode), getNextNodeNameInJsonPath(path), valueToBeUpdated,
						Oper);
			}

		} else {
			switch (Oper) {
			case UPDATE:
				updateValue(node, (String) listOfPath.get(0), valueToBeUpdated);
				break;
			case DELETE:
				delete(node, (String) listOfPath.get(0));
				break;
			}
		}
		ParkarLogger.traceLeave();
	}

	private void delete(JsonNode node, String firstNode) {
		((ObjectNode) node).remove(firstNode);
	}

	/**
	 * This method update the value of the Node.
	 * 
	 * @param node:JsonNode
	 * @param strKey:String
	 * @param valueTobeUpdated
	 *            : Object
	 * @throws ParkarCoreAPIException
	 *             : throws core API exception
	 */
	private void updateValue(JsonNode node, String strKey, Object valueTobeUpdated) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		String infoMessage = "";

		if (valueTobeUpdated instanceof Integer)
			((ObjectNode) node).put(strKey, (Integer) valueTobeUpdated);
		else if (valueTobeUpdated instanceof String)
			((ObjectNode) node).put(strKey, (String) valueTobeUpdated);

		else if (valueTobeUpdated instanceof Boolean)
			((ObjectNode) node).put(strKey, (Boolean) valueTobeUpdated);
		else if (valueTobeUpdated instanceof Number)
			((ObjectNode) node).put(strKey, (Double) valueTobeUpdated);
		else {
			infoMessage = "Node at JSONPath is not of primitive type.";
			logger.error(infoMessage);
			throw new ParkarCoreAPIException("Node at JSONPath is not of primitive type.");
		}
		ParkarLogger.traceLeave();
	}

	private String getNextNodeNameInJsonPath(String jsonPath) {
		return jsonPath.substring(jsonPath.indexOf(".") + 1);
	}

	/**
	 * This method verifies whether jsonPath is a valid path in Node.
	 * 
	 * @param jsonPath:String
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 *             : throws core API exception
	 */
	public boolean isNodePresent(String jsonPath) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		JsonPath j = new JsonPath(this.rootNode.toString());
		Boolean value = false;
		String infoMessage = " node: " + this.rootNode + BREAK_LINE + " jsonPath:" + jsonPath + BREAK_LINE;
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
}
