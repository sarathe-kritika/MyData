package com.parkar.api.rest.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.dateutils.DateParser;
import com.parkar.exception.ParkarCoreCommonException;

public class JsonReaderHelper {
	private static final String quote = "\"";

	/**
	 * Read file and return a Json node
	 * @param fileName: String
	 * @return a Json node
	 * @throws ParkarCoreAPIException
	 * 			: parkar core API exception
	 */
	public static JsonNode read(String fileName) throws ParkarCoreAPIException{
		byte[] jsonData;
		try {
			jsonData = Files.readAllBytes(Paths.get(fileName));
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readTree(jsonData);
		} catch (IOException e) {
			throw new ParkarCoreAPIException(e.getMessage(), e);
		} 
	}
	
	/**
	 * Get the value from Json node
	 * 
	 * @param path: String
	 * @param node: JsonNode
	 * @return the value in String
	 */
	public static String getValue(String path,JsonNode node){
		String toJsonPath = "/" + path.replace(".", "/").replace("[", "/").replace("]", "");
		String value = node.at(toJsonPath).asText();
		return value.isEmpty() ? null : value;
	}
	
	/**
	 * Set the value to Json node
	 * 
	 * @param value: String
	 * @param path: String
	 * @param node: JsonNode
	 */
	public static void setValue(String value, String path, JsonNode node){
		if(path.contains(".")){
			String objectPath = path.substring(0,path.lastIndexOf("."));
			String key = path.substring(path.lastIndexOf(".") + 1);
			String toJsonPath = "/" + objectPath.replace(".", "/").replace("[", "/").replace("]", "");
			if(!node.at(toJsonPath).isMissingNode())
				((ObjectNode)node.at(toJsonPath)).put(key, value);
		}else{
			if(!node.isMissingNode())
				((ObjectNode)node).put(path, value);
		}
	}
	
	/**
	 * Set the value to Json node
	 * 
	 * @param value: int
	 * @param path: String
	 * @param node: JsonNode
	 */
	public static void setValue(int value, String path, JsonNode node){
		if(path.contains(".")){
			String objectPath = path.substring(0,path.lastIndexOf("."));
			String key = path.substring(path.lastIndexOf(".") + 1);
			String toJsonPath = "/" + objectPath.replace(".", "/").replace("[", "/").replace("]", "");
			if(!node.at(toJsonPath).isMissingNode())
				((ObjectNode)node.at(toJsonPath)).put(key, value);
		}else{
			if(!node.isMissingNode())
				((ObjectNode)node).put(path, value);
		}
	}
	
	/**
	 * Set the value to Json node
	 * 
	 * @param value: boolean
	 * @param path: String
	 * @param node: JsonNode
	 */
	public static void setValue(boolean value, String path, JsonNode node){
		if(path.contains(".")){
			String objectPath = path.substring(0,path.lastIndexOf("."));
			String key = path.substring(path.lastIndexOf(".") + 1);
			String toJsonPath = "/" + objectPath.replace(".", "/").replace("[", "/").replace("]", "");
			if(!node.at(toJsonPath).isMissingNode())
				((ObjectNode)node.at(toJsonPath)).put(key, value);
		}else{
			if(!node.isMissingNode())
				((ObjectNode)node).put(path, value);
		}
	}
	
	/**
	 * Set the value to Json node
	 * 
	 * @param value: float
	 * @param path: String
	 * @param node: JsonNode
	 */
	public static void setValue(float value, String path, JsonNode node){
		if(path.contains(".")){
			String objectPath = path.substring(0,path.lastIndexOf("."));
			String key = path.substring(path.lastIndexOf(".") + 1);
			String toJsonPath = "/" + objectPath.replace(".", "/").replace("[", "/").replace("]", "");
			if(!node.at(toJsonPath).isMissingNode())
				((ObjectNode)node.at(toJsonPath)).put(key, value);
		}else{
			if(!node.isMissingNode())
				((ObjectNode)node).put(path, value);
		}
	}
	
	/**
	 * Build Json node
	 * 
	 * @param node: JsonNode
	 * @return a String of traverseTree
	 * @throws ParkarCoreCommonException
	 * 			: parkar core common exception
	 */
	public static String build(JsonNode node) throws ParkarCoreCommonException{
		return traverseTree(node,new DateParser());
	}
	
	/**
	 * Build Json node
	 * @param node: JsonNode
	 * @param dp: DateParser
	 * @return a String of traverseTree
	 * @throws ParkarCoreCommonException
	 * 			: parkar core common exception
	 */
	public static String build(JsonNode node, DateParser dp) throws ParkarCoreCommonException{
		return traverseTree(node,dp);
	}
	
	/**
	 * Traverse tree
	 * @param node: JsonNode	
	 * @param dp: DateParser
	 * @return a String of traverseTree
	 * @throws ParkarCoreCommonException
	 * 			: parkar core common exception
	 */
	private static String traverseTree(JsonNode node, DateParser dp) throws ParkarCoreCommonException{
		ArrayList<String> resultList = new ArrayList<String>();
		Iterator<Entry<String, JsonNode>> it = node.fields();
		
		while(it.hasNext()){
			Entry<String, JsonNode> t = it.next();

			if(t.getValue().isBoolean() || t.getValue().isIntegralNumber()){
				resultList.add(quote + t.getKey() + quote + " : " + t.getValue());
				continue;
			}
			if(t.getValue().isTextual() || t.getValue().isValueNode()){
				
				if(dp.isValidatePattern(t.getValue().asText())){
					resultList.add(quote + t.getKey() + quote + " : " + quote + dp.parseDatePattern(t.getValue().asText()) + quote);
				}else{
					resultList.add(quote + t.getKey() + quote + " : " + t.getValue());
				}
			}else if(t.getValue().isObject()){
				resultList.add(quote + t.getKey() + quote + " : " + traverseTree(t.getValue(),dp));
			}else if(t.getValue().isArray()){
				ArrayList<String> temp = new ArrayList<String>();
				for(JsonNode n : t.getValue())
					temp.add(traverseTree(n,dp));
				
				if(temp.size()!=0){
					resultList.add(quote + t.getKey() + quote + " : " + temp.stream().collect(Collectors.joining("," , "[", "]")));
				}
			}
		}
		return resultList.isEmpty() ? "" : resultList.stream().collect(Collectors.joining("," , "{" ,"}"));
	}
	
}
