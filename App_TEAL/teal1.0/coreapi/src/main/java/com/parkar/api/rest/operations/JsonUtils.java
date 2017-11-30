package com.parkar.api.rest.operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.restassured.response.Response;
import com.parkar.logging.ParkarLogger;
import com.parkar.api.rest.exception.ParkarCoreAPIException;

public class JsonUtils {
	final static Logger logger = Logger.getLogger(APIResponse.class);
	public static final String BREAK_LINE = "</br>";
	private String errorMsg = "";

	/**
	 * This method is for verifying if JsonArray is sorted as per sort order for
	 * the given field name in array node.
	 * 
	 * @param jsonArr:JSONArray
	 * @param fieldName:String
	 * @param isAscOrder:boolean
	 * @return boolean
	 * @throws JSONException
	 * 			: JSON exception
	 */
	public boolean verifyJsonSort(JSONArray jsonArr, String fieldName, boolean isAscOrder) throws JSONException {
		if (jsonArr.length() > 0) {
			if (jsonArr.getJSONObject(0).get(fieldName) instanceof Integer) {
				return isIntListSorted(jsonArr, fieldName, isAscOrder);
			} else {
				return isStrListSorted(jsonArr, fieldName, isAscOrder);
			}
		}
		return false;
	}

	/**
	 * This method is for checking if JSON array is sorted according to integer
	 * type field value as per sort order.
	 * 
	 * @param jsonArr:JSONArray
	 * @param fieldName:String
	 * @param isSorted:boolean
	 * @return boolean
	 * @throws JSONException
	 * 			: JSON exception
	 */
	private boolean isIntListSorted(JSONArray jsonArr, String fieldName, boolean isAscOrder)
			throws JSONException {
		List<Integer> intList = new ArrayList<Integer>();
		List<Integer> expectedIntList = new ArrayList<Integer>();
		for (int i = 0; i < jsonArr.length(); i++) {
			intList.add(jsonArr.getJSONObject(i).getInt(fieldName));
			expectedIntList.add(jsonArr.getJSONObject(i).getInt(fieldName));
		}
		sortIntList(intList, isAscOrder);
		if (intList.equals(expectedIntList)) {
			return true;
		}
		return false;
	}

	/**
	 * This method is for checking if JSON array is sorted according to string
	 * type field value as per sort order
	 * 
	 * @param jsonArr:JSONArray
	 * @param fieldName:String
	 * @param isSorted:boolean
	 * @return boolean
	 * @throws JSONException
	 * 			: JSON exception
	 */
	private boolean isStrListSorted(JSONArray jsonArr, String fieldName, boolean isAscOrder)
			throws JSONException {
		List<String> strList = new ArrayList<String>();
		List<String> expectedStrList = new ArrayList<String>();
		for (int i = 0; i < jsonArr.length(); i++) {
			strList.add(jsonArr.getJSONObject(i).getString(fieldName));
			expectedStrList.add(jsonArr.getJSONObject(i).getString(fieldName));
		}
		sortStringList(strList, isAscOrder);
		if (strList.equals(expectedStrList)) {
			return true;
		}
		return false;
	}

	/**
	 * This method is for sorting string list in ascending or descending order
	 * as per sort order.
	 * 
	 * @param list:List
	 * @param isAscOrder:boolean
	 * @return List
	 */
	private List<String> sortStringList(List<String> list, boolean isAscOrder) {
		if (isAscOrder) {
			Collections.sort(list);
		} else {
			Collections.sort(list);
			Collections.reverse(list);
		}
		return list;
	}

	/**
	 * This method is for sorting integer list in ascending order or descending
	 * order as per sort order.
	 * 
	 * @param list:List
	 * @param isAscOrder:boolean
	 * @return List
	 */
	private List<Integer> sortIntList(List<Integer> list, boolean isAscOrder) {
		if (isAscOrder) {
			Collections.sort(list);
		} else {
			Collections.sort(list);
			Collections.reverse(list);
		}
		return list;
	}

	/***
	 * This method is used for comparing the response with the given
	 * expectedjson.
	 * 
	 * @param res:Response
	 * @param expectedJson:String
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public ValidationResults jsonCompare(Response res, String expectedJson) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Object actualEle = null;
		Object expectedEle = null;
		boolean compareJSON = false;
		errorMsg = "";
		try {
			char actualJsonChar = res.asString().trim().charAt(0);
			char expectedJsonChar = expectedJson.trim().charAt(0);

			if (actualJsonChar == '{') {
				actualEle = new JSONObject(res.asString());
			} else if (actualJsonChar == '[') {
				actualEle = new JSONArray(res.asString());
			}
			if (expectedJsonChar == '{') {
				expectedEle = new JSONObject(expectedJson);
			} else if (expectedJsonChar == '[') {
				expectedEle = new JSONArray(expectedJson);
			}
			if ((actualEle instanceof JSONObject) && (expectedEle instanceof JSONObject)) {
				compareJSON = compareMap((JSONObject) actualEle, (JSONObject) expectedEle, true);
			} else if ((actualEle instanceof JSONArray) && (expectedEle instanceof JSONArray)) {
				compareJSON = compareList((JSONArray) actualEle, (JSONArray) expectedEle, true);
			} else {
				errorMsg = "Either actual or expected JSON are not an object of Map/List";
				return new ValidationResults(compareJSON, errorMsg);
			}
		} catch (Exception e) {

			errorMsg = "Either expected or actual is an in-valid json file. Exiting";
			logger.error(errorMsg);
			throw new ParkarCoreAPIException(errorMsg, e);
		}
		ParkarLogger.traceLeave();
		return new ValidationResults(compareJSON, errorMsg);
	}

	/**
	 * This method is used for checking whether response contains the given
	 * expectedjson.
	 * 
	 * @param res:Response
	 * @param expectedJson
	 *            :String
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public ValidationResults jsonContains(Response res, String expectedJson) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		Object actualEle = null;
		Object expectedEle = null;
		boolean containsJSON = false;
		errorMsg = "";
		char actualJsonChar = res.asString().trim().charAt(0);
		char expectedJsonChar = expectedJson.trim().charAt(0);
		try {
			if (actualJsonChar == '{') {
				actualEle = new JSONObject(res.asString());
			} else if (actualJsonChar == '[') {
				actualEle = new JSONArray(res.asString());
			}
			if (expectedJsonChar == '{') {
				expectedEle = new JSONObject(expectedJson);
			} else if (expectedJsonChar == '[') {
				expectedEle = new JSONArray(expectedJson);
			}
			if ((actualEle instanceof JSONObject) && (expectedEle instanceof JSONObject)) {
				containsJSON = findAndCompareMap((JSONObject) actualEle, (JSONObject) expectedEle);
			} else if ((actualEle instanceof JSONArray) && (expectedEle instanceof JSONArray)) {
				containsJSON = findAndCompareList((JSONArray) actualEle, (JSONArray) expectedEle);
			} else if ((actualEle instanceof JSONObject) && (expectedEle instanceof JSONArray)
					&& (((JSONArray) expectedEle).length() == 1)
					&& (((JSONArray) expectedEle).get(0) instanceof JSONObject)) {
				containsJSON = findAndCompareMap((JSONObject) actualEle, (JSONObject) ((JSONArray) expectedEle).get(0));
			} else if ((actualEle instanceof JSONArray) && (expectedEle instanceof JSONObject)) {
				containsJSON = findAndCompareNestedMapInList((JSONArray) actualEle, (JSONObject) expectedEle);
			} else if ((actualEle instanceof JSONObject) && (expectedEle instanceof JSONArray)) {
				containsJSON = findAndCompareNestedListInMap((JSONObject) actualEle, (JSONArray) expectedEle);
			}
		} catch (Exception e) {

			errorMsg = "Either expected or actual is an in-valid json file. Exiting";
			logger.error(errorMsg);
			throw new ParkarCoreAPIException(errorMsg, e);
		}
		ParkarLogger.traceLeave();
		return new ValidationResults(containsJSON, errorMsg);
	}

	/***
	 * This method will find and compare nested list in a map .
	 * 
	 * @param actualMap:JSONObject
	 * @param expectedList:JSONArray
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	private boolean findAndCompareNestedListInMap(JSONObject actualMap, JSONArray expectedList)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		boolean blnCompareList = false;
		Object actValue;
		try {
			Iterator<?> itr = actualMap.keySet().iterator();
			while (itr.hasNext()) {
				actValue = actualMap.get((String) itr.next());
				if (actValue instanceof JSONArray) {
					blnCompareList = findAndCompareList((JSONArray) actValue, expectedList);
				} else if (actValue instanceof JSONObject) {
					blnCompareList = findAndCompareNestedListInMap((JSONObject) actValue, expectedList);
				}
				if (blnCompareList)
					break;
			}
			ParkarLogger.traceLeave();
			return blnCompareList;
		} catch (Exception e) {
			String errMsg = "Exception Occured, Expected list : " + expectedList + " not found in Actual map "
					+ actualMap + "Due to :" + e.getMessage();
			logger.error(errMsg);
			throw new ParkarCoreAPIException(errMsg, e);
		}
	}

	/***
	 * This method will find and compare nested map in list.
	 * 
	 * @param actualList:
	 *            JSONArray
	 * @param expectedMap:
	 *            JSONObject
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	private boolean findAndCompareNestedMapInList(JSONArray actualList, JSONObject expectedMap)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		boolean blnCompareList = false;
		Object actValue = null;
		try {
			for (int i = 0; i < actualList.length(); i++) {
				actValue = actualList.get(i);
				if (actValue instanceof JSONObject) {
					blnCompareList = findAndCompareMap((JSONObject) actValue, expectedMap);
				} else if (actValue instanceof JSONArray) {
					blnCompareList = findAndCompareNestedMapInList((JSONArray) actValue, expectedMap);
				}
				if (blnCompareList)
					break;
			}
			ParkarLogger.traceLeave();
			return blnCompareList;
		} catch (Exception e) {
			String errMsg = "Exception Occured, Expected Map : " + expectedMap + " not found in  Actual list "
					+ actualList + "Due to :" + e.getMessage();
			logger.error(errMsg);
			throw new ParkarCoreAPIException(errMsg, e);
		}
	}

	/***
	 * This method will find whether the actual list contains the expected list.
	 * 
	 * @param actualList:JSONArray
	 * @param expectedList:JSONArray
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	private boolean findAndCompareList(JSONArray actualList, JSONArray expectedList)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		boolean blnCompareJSONContains = false;
		try {
			if (expectedList.length() == 0) {
				return true;
			}
			if ((actualList.length() >= expectedList.length())) {
				if (errorMsg == "")
					errorMsg = "";
				blnCompareJSONContains = compareList(actualList, expectedList, false);
				if (blnCompareJSONContains)
					return blnCompareJSONContains;
			}
			for (int i = 0; i < actualList.length(); i++) {
				Object actValue = actualList.get(i);
				if (actValue instanceof JSONArray) {
					blnCompareJSONContains = findAndCompareList((JSONArray) actValue, expectedList);
				} else if (actValue instanceof JSONObject) {
					blnCompareJSONContains = findAndCompareNestedListInMap((JSONObject) actValue, expectedList);
				}
				if (blnCompareJSONContains) {
					return blnCompareJSONContains;
				}
			}
			ParkarLogger.traceLeave();
			return blnCompareJSONContains;
		} catch (Exception e) {
			String errMsg = "Exception Occured, Expected list : " + expectedList + " not found in  Actual list "
					+ actualList + "Due to :" + e.getMessage();
			logger.error(errMsg);
			throw new ParkarCoreAPIException(errMsg, e);
		}
	}

	/***
	 * This method will compare two lists.
	 * 
	 * @param actualList:JSONArray
	 * @param expectedList:JSONArray
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	private boolean compareList(JSONArray actualList, JSONArray expectedList, boolean isJsonCompare)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		boolean blnCompareList = true;
		Object expValue = null;
		Object actValue = null;
		try {
			if (actualList.length() != expectedList.length() && isJsonCompare) {
				if (errorMsg == "")
					errorMsg = "Actual and Expected list in JSON don't have same number of elements. Expected list: "
							+ expectedList.toString();
				return false;
			}
			for (int i = 0; i < expectedList.length(); i++) {
				expValue = expectedList.get(i);
				boolean blnNestedCompareList = false;
				for (int j = 0; j < actualList.length(); j++) {
					actValue = actualList.get(j);
					blnNestedCompareList = compareObj(actValue, expValue, isJsonCompare);
					if (blnNestedCompareList) {
						actualList.remove(j);
						break;
					}
				}
				if (!blnNestedCompareList) {
					blnCompareList = blnNestedCompareList;
					if (errorMsg == "")
						errorMsg = "Not able to find expected value: " + expValue + " in Actual";
					break;
				}
			}
			ParkarLogger.traceLeave();
			return blnCompareList;
		} catch (Exception e) {
			String errMsg = "Not able to find expected value: " + expValue + " in Actual" + actValue + "Due to :"
					+ e.getMessage();
			logger.error(errMsg);
			throw new ParkarCoreAPIException(errMsg, e);
		}
	}

	/***
	 * This method will find whether the actual map contains the expected map.
	 * 
	 * @param actualMap:JSONObject
	 * @param expectedMap:JSONObject
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	private boolean findAndCompareMap(JSONObject actualMap, JSONObject expectedMap)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		boolean blnCompareJSONContains = false;
		Object actValue = null;
		try {
			if (expectedMap.length() == 0)
				return true;
			if ((actualMap.length() >= expectedMap.length())) {
				if (errorMsg == "")
					errorMsg = "";
				blnCompareJSONContains = compareMap(actualMap, expectedMap, false);
				if (blnCompareJSONContains)
					return blnCompareJSONContains;
			}
			Iterator<?> itr = actualMap.keySet().iterator();
			while (itr.hasNext()) {
				actValue = actualMap.get((String) itr.next());
				if (actValue instanceof JSONObject) {
					blnCompareJSONContains = findAndCompareMap((JSONObject) actValue, expectedMap);
				} else if (actValue instanceof JSONArray) {
					blnCompareJSONContains = findAndCompareNestedMapInList((JSONArray) actValue, expectedMap);
				}
				if (blnCompareJSONContains) {
					return blnCompareJSONContains;
				}
			}
			ParkarLogger.traceLeave();
			return blnCompareJSONContains;
		} catch (Exception e) {
			String errMsg = " Exception Occured, Expected Map :  " + expectedMap + " doesn't exist in Actual" + actValue
					+ "Due to :" + e.getMessage();
			logger.error(errMsg);
			throw new ParkarCoreAPIException(errMsg, e);
		}
	}

	/***
	 * This method will compare two maps.
	 * 
	 * @param actualMap:JSONObject
	 * @param expectedMap:JSONObject
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	private boolean compareMap(JSONObject actualMap, JSONObject expectedMap, boolean isJsonCompare)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		boolean blCompareMap = false;
		Object expkey = null;
		Object expValue = null;
		try {
			if ((actualMap.length() != expectedMap.length()) && isJsonCompare) {
				if (errorMsg == "")
					errorMsg = "Actual and Expected map in JSON don't have same number of elements. Expected map: "
							+ expectedMap.toString();
				return false;
			}
			Iterator<?> itr = expectedMap.keySet().iterator();
			while (itr.hasNext()) {
				expkey = itr.next();
				expValue = expectedMap.get((String) expkey);
				if (!(actualMap.has((String) expkey))) {
					if (errorMsg == "")
						errorMsg = "Key value pair " + expkey + "=" + expValue + " doesn't exist in Actual";
					return false;
				}
				Object actValue = actualMap.get((String) expkey);
				blCompareMap = compareObj(actValue, expValue, isJsonCompare);
				if (!blCompareMap) {
					if (errorMsg == "")
						errorMsg = "Not able to find expected value: " + expValue + " in Actual";
					return false;
				}
			}
			ParkarLogger.traceLeave();
			return blCompareMap;
		} catch (Exception e) {
			String errMsg = " Exception Occured, Key value pair " + expkey + "=" + expValue
					+ " doesn't exist in Actual. Due to :" + e.getMessage();
			logger.error(errMsg);
			throw new ParkarCoreAPIException(errMsg, e);
		}
	}

	/***
	 * This method will compare the two objects based on actual and expected
	 * value.
	 * 
	 * @param actValue:Object
	 * @param expValue:Object
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	private boolean compareObj(Object actValue, Object expValue, boolean isJsonCompare)
			throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		boolean blnCompareObject = false;
		try {
			if ((actValue instanceof JSONObject) && (expValue instanceof JSONObject)) {
				blnCompareObject = compareMap((JSONObject) actValue, (JSONObject) expValue, isJsonCompare);
			} else if ((actValue instanceof JSONArray) && (expValue instanceof JSONArray)) {
				blnCompareObject = compareList((JSONArray) actValue, (JSONArray) expValue, isJsonCompare);
			} else if ((actValue instanceof JSONArray) && (expValue instanceof JSONObject)
					&& (((JSONArray) actValue).length() == 1)
					&& (((JSONArray) actValue).get(0) instanceof JSONObject)) {
				blnCompareObject = compareMap((JSONObject) ((JSONArray) actValue).get(0), (JSONObject) expValue,
						isJsonCompare);
			} else if ((actValue instanceof JSONObject) && (expValue instanceof JSONArray)
					&& (((JSONArray) expValue).length() == 1)
					&& (((JSONArray) expValue).get(0) instanceof JSONObject)) {
				blnCompareObject = compareMap((JSONObject) actValue, (JSONObject) ((JSONArray) expValue).get(0),
						isJsonCompare);
			} else if ((actValue instanceof JSONArray) && (expValue instanceof JSONObject)) {
				blnCompareObject = findAndCompareNestedMapInList((JSONArray) actValue, (JSONObject) expValue);
			} else if ((actValue instanceof JSONObject) && (expValue instanceof JSONArray)) {
				blnCompareObject = findAndCompareNestedListInMap((JSONObject) actValue, (JSONArray) expValue);
			} else {
				blnCompareObject = compareValue(actValue, expValue);
			}
			ParkarLogger.traceLeave();
			return blnCompareObject;
		} catch (Exception e) {
			String errMsg = "Exception Occured, Expected value : " + expValue + " is not equal to  the  Actual value "
					+ actValue + " Due to : " + e.getMessage();
			logger.error(errMsg);
			throw new ParkarCoreAPIException(errMsg, e);
		}
	}

	/***
	 * This method will compare values based on actual and expected values.
	 * 
	 * @param actValue:Object
	 * @param expValue:Object
	 * @return boolean
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	private boolean compareValue(Object actValue, Object expValue) throws ParkarCoreAPIException {
		ParkarLogger.traceEnter();
		boolean compareValue = false;
		String expEvalValue = expValue.toString();
		try {
			if ((actValue instanceof Number) && (expValue instanceof Number)) {
				if (actValue.equals(expValue)) {
					compareValue = true;
				} else {
					if (errorMsg == "")
						errorMsg = "Not able to find expected value: " + expValue + " in Actual";
				}
			} else if ((actValue.getClass() == null) && (expValue.getClass() == null)) {
				compareValue = true;
			} else if ((actValue instanceof String) && (expValue instanceof String)) {
				if (((String) actValue).equalsIgnoreCase((String) expValue)) {
					compareValue = true;
				} else {
					if (errorMsg == "")
						errorMsg = "Not able to find expected value: " + expEvalValue + " in Actual";
				}
			} else if ((actValue instanceof Boolean) && (expValue instanceof Boolean)) {
				if (actValue.toString().equalsIgnoreCase(expEvalValue)) {
					compareValue = true;
				} else {
					if (errorMsg == "")
						errorMsg = "Not able to find expected value: " + expEvalValue + " in Actual";
				}
			} else if ((actValue instanceof Number) && (expValue instanceof String)) {
				if (actValue.toString().equalsIgnoreCase(expEvalValue)) {
					compareValue = true;
				} else {
					if (errorMsg == "")
						errorMsg = "Not able to find expected value: " + expEvalValue + " in Actual";
				}
			}else if(actValue.equals(null) && expValue.equals(null)){
				compareValue = true;
			}
			
			else {
				if (errorMsg == "")
					errorMsg = "Not able to find expected value: " + expEvalValue + " in Actual";
			}
		} catch (Exception e) {
			String errMsg = "Exception Occured , Not able to find expected value: " + expValue + " in Actual. Due to :"
					+ e.getMessage();
			logger.error(errMsg);
			throw new ParkarCoreAPIException(errMsg, e);
		}
		ParkarLogger.traceLeave();
		return compareValue;
	}

}
