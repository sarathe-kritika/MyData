package com.parkar.api.rest.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.dateutils.DateParser;
import com.parkar.exception.ParkarCoreCommonException;

public class JsonBuildHelper {

	private static final String quote = "\"";
	private static DateParser dateParser = new DateParser();

	/**
	 * 
	 * Set the date format for the date parser to work
	 * @param map :Map
	 */
	public static void setDateFormat(Map<String, String> map){
		dateParser = new DateParser(map);
	}
	
	/**
	 * @param startIndex:int
	 * @param endIndex:int
	 * @param map:Map
	 * @return rs:List of Map
	 */
	public static List<Map<String,Object>> mapInRange(int startIndex, int endIndex, Map<String,Object> map){
		List<Map<String,Object>> rs = new ArrayList<Map<String,Object>>();
		for(int i = startIndex; i <= endIndex;i++){
			rs.add(substitute(i,map));
		}
		return rs;
	}
	
	
	@SuppressWarnings("unchecked")
	private static Map<String,Object> substitute(int index,Map<String,Object> map){
		Map<String, Object> temp = new HashMap<String,Object>();
		for(String key : map.keySet()){
			if(map.get(key) instanceof String){
				temp.put(key,((String)map.get(key)).replace("{index}", Integer.toString(index)));
			}else if (map.get(key) instanceof Map){
				temp.put(key, substitute(index,(Map<String,Object>)map.get(key)));
			}else if (map.get(key) instanceof List){
				if (((List<?>)map.get(key)).get(0) instanceof Map){
					List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
					for(Object o : (List<?>)map.get(key)){
						tempList.add(substitute(index,(Map<String,Object>)o));	
					}
					temp.put(key, tempList);	
				}else{
					temp.put(key, map.get(key));	
				}
			}else{
				temp.put(key, map.get(key));
			}
		}
		return temp;
	}
	
	
	@SafeVarargs
	public static <T> Map<String,Object> map(T... objects) throws ParkarCoreAPIException{
		if(objects.length % 2 != 0){
			throw new ParkarCoreAPIException("You cannot give odd number of element");
		}else if( objects.length==0 ){
			throw new ParkarCoreAPIException("You need to prepare at least two argument");
		}
				
		Map<String, Object> m = new LinkedHashMap<String, Object>();
		for(int i=0; i < objects.length; i+=2){
			if (objects[i] instanceof String)
				m.put((String) objects[i], objects[i+1]);
			else
				throw new ParkarCoreAPIException("Key " + objects[i] + " must be string"); 
		}
		return m;
	}
	
	
	@SafeVarargs
	public static List<Object> list(Object... objects) throws ParkarCoreAPIException{
		return Arrays.asList(objects);
	}


	@SafeVarargs
	public static <T> Map<String,Object> objRef(T... objects) throws ParkarCoreAPIException {
		if( objects.length==0 ){
			throw new ParkarCoreAPIException("You need to prepare at least two argument");
		}else if(objects.length == 1){
			if(objects[0] instanceof String){
				return map("qualifier",objects[0]);	
			}else{
				return map("id",objects[0]);
			}
		}else if(objects.length == 2 ){
			if(objects[1] instanceof String){
				return map("id",objects[0],"qualifier",objects[1]);	
			}else{
				return map("id",objects[1],"qualifier",objects[0]);
			}
		}else{
			return map("id",null,"qualifier",null);
		}
	}

	public static Request build(Map<String, Object> dataMap) throws ParkarCoreAPIException {
		try {
			return new Request(toJson(dataMap));
		} catch (Exception e) {
			throw new ParkarCoreAPIException(e.getMessage(),e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Request build(List<Object> dataList) throws ParkarCoreAPIException {
		try {
			List<String> results= new ArrayList<String>();
			for(Object data : dataList){
				if(data instanceof Map){
					results.add(toJson((Map<String, Object>)data));
				}else{
					results.add( genernateValueNode(data) );
				}
			}
			return new Request( results.stream().collect(Collectors.joining("," , "[", "]")) );
		} catch (Exception e) {
			throw new ParkarCoreAPIException(e.getMessage(),e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static String toJson(Map<String, Object> dataMap) throws ParkarCoreCommonException {
		ArrayList<String> resultList = new ArrayList<String>();

		for(String key:dataMap.keySet()){
			Object value = dataMap.get(key);
			if(value instanceof List || value instanceof Object[]){
				ArrayList<String> temp = new ArrayList<String>();
				for(Object o : value instanceof Object[] ? Arrays.asList(value) : (List<?>)value){
					if(o instanceof Map){
					temp.add( toJson( (Map<String, Object>) o) );
					}else{
						temp.add( genernateValueNode(o) );
					}
				}
				resultList.add(quote + key + quote + " : " + temp.stream().collect(Collectors.joining("," , "[", "]")));
			}else if (value instanceof Map){
				resultList.add(quote + key + quote + " : " + toJson( (Map<String, Object>)dataMap.get(key) ));
			}else{
				resultList.add(genernateValueNode(key, dataMap.get(key)));
			}
		}
		return resultList.isEmpty() ? "" : resultList.stream().collect(Collectors.joining("," , "{" ,"}"));
	}
	
	public static Request build(String schemaName, Map<String, Object> dataMap) throws ParkarCoreAPIException {
		try {
			JSONObject root = getRootNode(schemaName);
			return new Request(toJson((JSONObject) root, dataMap));
		} catch (Exception e) {
			throw new ParkarCoreAPIException(e.getMessage(),e);
		}
	}
	
	private static JSONObject getRootNode(String schemaName) throws FileNotFoundException, IOException, ParseException{
		JSONObject jsonSchema;
		Object obj =  new JSONParser().parse(new FileReader(schemaName));
		jsonSchema = (JSONObject) obj;
		return (JSONObject) ((JSONObject) jsonSchema.get(jsonSchema.keySet().iterator()
				.next().toString())).get("properties");
	}
	
	@SuppressWarnings("unchecked")
	private static String toJson(JSONObject schema, Map<String, Object> dataMap) throws ParkarCoreCommonException {
		ArrayList<String> resultList = new ArrayList<String>();

		for(String key:dataMap.keySet()){
			JSONObject typeObj = (JSONObject) schema.get(key);
			if(typeObj != null){
				
				String type = (String) typeObj.get("type");
				if(type.equalsIgnoreCase("array")){
					JSONObject nextNode = (JSONObject) ((JSONObject) typeObj.get("items")).get("properties");

					Object value = dataMap.get(key);
					if(value instanceof List || value instanceof Object[]){
						ArrayList<String> temp = new ArrayList<String>();
						for(Object o : value instanceof Object[] ? Arrays.asList(value) : (List<?>)value){
							temp.add( toJson(nextNode, (Map<String, Object>) o) );
						}
						resultList.add(quote + key + quote + " : " + temp.stream().collect(Collectors.joining("," , "[", "]")));
					}else{
						throw new RuntimeException("Expecting value of key "+ key + " to be a list or array; but got "+ value.getClass());
					}
				}else if (type.equalsIgnoreCase("object")) {
					JSONObject nextNode = (JSONObject) typeObj.get("properties");
					resultList.add(quote + key + quote + " : " + toJson(nextNode, (Map<String, Object>)dataMap.get(key) ));
				}else{
					resultList.add(genernateValueNode(key, dataMap.get(key), type));
				}
			}
		}
		return resultList.isEmpty() ? "" : resultList.stream().collect(Collectors.joining("," , "{" ,"}"));
	}
	
	private static String genernateValueNode(Object value) throws ParkarCoreCommonException{
		if (value instanceof String){
			if(dateParser.isValidatePattern((String)value)){
				return quote + dateParser.parseDatePattern((String)value) + quote;
			}else{
				return quote + value + quote;
			}
		}else{
			return value.toString();
		}
	}
	
	
	private static String genernateValueNode(String key, Object value) throws ParkarCoreCommonException{
		if (value instanceof String){
			if(dateParser.isValidatePattern((String)value)){
				return quote + key + quote + " : " +  quote + dateParser.parseDatePattern((String)value) + quote;
			}else{
				return quote + key + quote + " : " +  quote + value + quote;
			}
		}else{
			return quote + key + quote + " : " + value;
		}
	}
	
	
	private static String genernateValueNode(String key, Object value,String type) throws ParkarCoreCommonException{
		if (value instanceof String){
			if(dateParser.isValidatePattern((String)value)){
				return quote + key + quote + " : " +  quote + dateParser.parseDatePattern((String)value) + quote;
			}else{
				if(type.equalsIgnoreCase("boolean")){
					if(((String) value).equalsIgnoreCase("true")){
						return quote + key + quote + " : " + true ;
					}else if(((String) value).equalsIgnoreCase("false")){
						return quote + key + quote + " : " +  false;
					}else{
						return quote + key + quote + " : " +  quote + value + quote;
					}
				}else{
					return quote + key + quote + " : " +  quote + value + quote;
				}
			}
		}else{
			return quote + key + quote + " : " + value;
		}
	}
}
