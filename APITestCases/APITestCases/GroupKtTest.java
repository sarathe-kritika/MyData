package com.parkar.tests;

import org.testng.annotations.Test;

import com.parkar.api.rest.base.BaseAPITest;
import com.parkar.api.rest.operations.APIResponse;
import com.parkar.business.GroupKtAPIObject;
import com.parkar.exception.ParkarCoreCommonException;

public class GroupKtTest extends BaseAPITest{
	@Test(groups = "TEAL", testName = "test_ALM43677_GetOrder", description = "Get an Order")
	public void test_ALM43677_GetOrder() throws ParkarCoreCommonException {
	
//		apiDriver.GLOBAL_COOKIE="";
		reporter.reportStep("Step 1 - Login to Order Management System.");
		GroupKtAPIObject groupKtAPIObject = new GroupKtAPIObject(apiDriver);
		groupKtAPIObject.login("abcd", "pqrs");

		reporter.reportStep("Step 2 - Get perticular Order");
		APIResponse response = groupKtAPIObject.getList();

		System.out.println("You are after response object");
		reporter.reportStep("Step 3 - Verify API response after finding perticular id.");
		assertion.verifyEquals(response.getStatusCode(), 200, "Verify status code of API repsonse");
		assertion.verifyTrue(response.contains("India"),"Verify String");
	}

}
