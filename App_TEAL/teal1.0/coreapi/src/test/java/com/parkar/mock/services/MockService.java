/*package com.parkar.mock.services;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.matching.MatchResult;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;

public class MockService {

	@ClassRule
	public static WireMockRule wireMockRule = new WireMockRule(9999);

	*//**
	 * startMockedServices starts get , post , put and delete type of mocked services
	 * 
	 *//*
	@BeforeClass
	public static void startMockedServices() {
		startGetMockServices();
		startPostMockedServices();
		startPutMockedServices();
		startDeleteMockedServices();
		startLoginMockedService();
	}
	
	*//**
	 * startGetMockServices creates mocked services which expect get request. 
	 *
	 *//*

	public static void startGetMockServices() {

		wireMockRule.stubFor(get(WireMock.urlMatching("/ShiftMockService/getShiftWithQueryParameter.*"))
				.withQueryParam("id", WireMock.matching("[1-9]"))
				.withQueryParam("tenantId", WireMock.equalTo("combined"))
				.willReturn(aResponse().withBodyFile("getShift.json").withStatus(200)));

		wireMockRule.stubFor(get(WireMock.urlMatching("/ShiftMockService/getShiftWithNoQueryParam"))
				.willReturn(aResponse().withBodyFile("getShift.json").withStatus(200)));

		wireMockRule.stubFor(get(WireMock.urlMatching("/ShiftMockService/getShiftWithPathParameter/[1-9]"))
				.willReturn(aResponse().withBodyFile("getShift.json").withStatus(200)));

	}

	*//**
	 * startPostMockedServices creates mocked services which expect post request. 
	 *
	 *//*
	public static void startPostMockedServices() {

		wireMockRule.stubFor(WireMock.post(WireMock.urlMatching("/ShiftMockService/createAuthToken"))
				.withHeader("Cookie", WireMock.equalTo("test"))
				.willReturn(aResponse().withHeader("Cookie", "test").withStatus(200)));

		wireMockRule.stubFor(WireMock.post(WireMock.urlMatching("/ShiftMockService/createShiftWithBody"))
				.withRequestBody(WireMock.matchingJsonPath("$.employee"))
				.willReturn(aResponse().withStatus(200).withHeader("content-type", "application/json")
						.withBodyFile("createShift.json").withTransformers("body-transformer")));

		wireMockRule.stubFor(WireMock.post(WireMock.urlMatching("/ShiftMockService/createShiftWithHeaderParam"))
				.withHeader("content-type", WireMock.containing("application/json"))
				.willReturn(aResponse().withStatus(200).withHeader("content-type", "application/json")
						.withBodyFile("updateShift.json")));

		wireMockRule.stubFor(WireMock.post(WireMock.urlMatching("/ShiftMockService/createShift.*"))
				.withRequestBody(WireMock.matchingJsonPath("$.employee"))
				.withQueryParam("tenantId", WireMock.equalTo("combined"))
				.willReturn(aResponse().withStatus(200).withHeader("content-type", "application/json")
						.withBodyFile("createShift.json").withTransformers("body-transformer")));

		wireMockRule.stubFor(WireMock.post(WireMock.urlMatching("/ShiftMockService/createShiftWithQueryParam.*"))
				.withQueryParam("tenantId", WireMock.containing("combined")).willReturn(aResponse()
						.withBodyFile("createShift.json").withStatus(200).withTransformers("body-transformer")));

		wireMockRule
				.stubFor(WireMock.post(WireMock.urlMatching("/ShiftMockService/createShiftWithPathParameter/[1-9].*"))
						.willReturn(aResponse().withStatus(200).withHeader("content-type", "application/json")
								.withBodyFile("createShift.json").withTransformers("body-transformer")));

	}
	
	*//**
	 * startPutMockedServices creates mocked services which expect put request. 
	 *
	 *//*

	public static void startPutMockedServices() {

		wireMockRule.stubFor(WireMock.put(WireMock.urlMatching("/ShiftMockService/updateShiftWithPathParam/[1-9]"))
				.willReturn(aResponse().withStatus(200).withHeader("content-type", "application/json")
						.withBodyFile("updateShift.json").withTransformers("body-transformer")));

		wireMockRule.stubFor(WireMock.put(WireMock.urlMatching("/ShiftMockService/updateShiftWithQueryParam.*"))
				.withQueryParam("tenantId", WireMock.equalTo("combined"))
				.willReturn(aResponse().withStatus(200).withHeader("content-type", "application/json")
						.withBodyFile("updateShift.json").withTransformers("body-transformer")));

		wireMockRule.stubFor(WireMock.put(WireMock.urlMatching("/ShiftMockService/updateShiftWithBody"))
				.withRequestBody(WireMock.matchingJsonPath("$.employee"))
				.willReturn(aResponse().withStatus(200).withHeader("content-type", "application/json")
						.withBodyFile("updateShift.json").withTransformers("body-transformer")));

	}

	*//**
	 * startDeleteMockedServices creates mocked services which expect delete request. 
	 * 
	 *//*
	public static void startDeleteMockedServices() {

		wireMockRule.stubFor(WireMock.delete(WireMock.urlMatching("/ShiftMockService/deleteShiftWithPathParam/[1-9]"))
				.willReturn(aResponse().withStatus(204).withTransformers("body-transformer")));

		wireMockRule.stubFor(WireMock.delete(WireMock.urlMatching("/ShiftMockService/deleteShiftWithQueryParam.*"))
				.withQueryParam("tenantId", WireMock.equalTo("combined")).withQueryParam("id", WireMock.equalTo("7"))
				.willReturn(aResponse().withStatus(204).withTransformers("body-transformer")));

	}
	
	public static void startLoginMockedService(){
		wireMockRule.stubFor(WireMock.post(WireMock.urlMatching("/MockService/loginService"))
				.withHeader("X-OpenAM-Username",  WireMock.containing("Tcmgr"))
				.withHeader("X-OpenAM-Password",  WireMock.containing("kronites"))
				 .willReturn(aResponse()
			        .withStatus(200)
			        .withHeader("Set-Cookie", "name=Tcmgr;password=kronites")
			       .withBodyFile("login.json")
			        .withTransformers("body-transformer")));
	}
	

}
*/