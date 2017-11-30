package com.parkar.api.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.api.rest.json.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.path.json.JsonPath;

public class RequestTest {
	private Request requestParser;
	private File file;
	private ObjectMapper mapper;
	private JsonNode root;

	@Before
	public void setUp() throws JsonProcessingException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		file = new File(classLoader.getResource("requestTest.json").getFile());
		mapper = new ObjectMapper();
		root = mapper.readTree(file);
	}

	@Test
	public void test1_updateNodeValue() throws  ParkarCoreAPIException {

		String jsonPath = "commentNotes[0].id";
		requestParser = new Request(root);
		Object valueToupdate = 800;
		requestParser.updateNodeValue(jsonPath, valueToupdate);
		assertEquals(true, valueToupdate.equals(JsonPath.with(requestParser.getRequest().toString()).get(jsonPath)));

		jsonPath = "employee.id";
		valueToupdate = "";
		requestParser.updateNodeValue(jsonPath, valueToupdate);
		assertEquals(true, valueToupdate.equals(JsonPath.with(requestParser.getRequest().toString()).get(jsonPath)));

		jsonPath = "employee.id";
		valueToupdate = 180;
		requestParser.updateNodeValue(jsonPath, valueToupdate);
		assertEquals(true, valueToupdate.equals(JsonPath.with(requestParser.getRequest().toString()).get(jsonPath)));

		jsonPath = "commentNotes[0].notes[0].commentNoteId";
		valueToupdate = 1;
		requestParser.updateNodeValue(jsonPath, valueToupdate);
		assertEquals(true, valueToupdate.equals(JsonPath.with(requestParser.getRequest().toString()).get(jsonPath)));

		jsonPath = "relations.id";
		valueToupdate = 182;
		requestParser.updateNodeValue(jsonPath, valueToupdate);
		assertEquals(true,
				valueToupdate.equals(JsonPath.with(requestParser.getRequest().toString()).get("relations.id[0]")));
		assertEquals(true,
				valueToupdate.equals(JsonPath.with(requestParser.getRequest().toString()).get("relations.id[1]")));

		jsonPath = "commentNotes[0].comment.name";
		valueToupdate = "European Business";
		requestParser.updateNodeValue(jsonPath, valueToupdate);
		assertEquals(true, valueToupdate.equals(JsonPath.with(requestParser.getRequest().toString()).get(jsonPath)));
	}

	@Test
	public void test1_deleteNode() throws ParkarCoreAPIException {

		String jsonPath = "employee.id";
		requestParser = new Request(root);
		requestParser.deleteNode(jsonPath);
		assertEquals(false, requestParser.isNodePresent(jsonPath));

		jsonPath = "commentNotes[0].id";
		requestParser = new Request(root);
		requestParser.deleteNode(jsonPath);
		assertEquals(false, requestParser.isNodePresent(jsonPath));

		jsonPath = "commentNotes[0].notes[0].commentNoteId";
		requestParser = new Request(root);
		requestParser.deleteNode(jsonPath);
		assertEquals(false, requestParser.isNodePresent( jsonPath));

		jsonPath = "commentNotes[0].notes";
		requestParser = new Request(root);
		requestParser.deleteNode(jsonPath);
		assertEquals(false, requestParser.isNodePresent(jsonPath));

		jsonPath = "relations.id";
		requestParser = new Request(root);
		requestParser.deleteNode(jsonPath);
		assertEquals(false, requestParser.isNodePresent("relations.id[0]"));
		assertEquals(false, requestParser.isNodePresent("relations.id[1]"));

	}

}
