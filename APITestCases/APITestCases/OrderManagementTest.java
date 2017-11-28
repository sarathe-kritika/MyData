package com.parkar.tests;

import static com.parkar.api.rest.json.JsonBuildHelper.map;

import java.util.Map;
import java.lang.reflect.Method;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.parkar.api.rest.base.BaseAPITest;
import com.parkar.api.rest.json.Request;
import com.parkar.api.rest.operations.APIResponse;
import com.parkar.business.OrderManagmentAPIObject;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.utils.common.CSVReader;

public class OrderManagementTest extends BaseAPITest {

	private String orderId = null;
	public String userName = null;
	public String password = null;
	public String productName = null;
	public int quantity;
	public String updateProductName = null;
	public int updateQuantity;

	@Test(groups = "TEAL", testName = "test_ALM43676_CreateOrder", description = "Create an Order" ,priority=1)
	public void test_ALM43676_CreateOrder() throws ParkarCoreCommonException {
		Map<String, String> testData = CSVReader.readCSV("TestCase_OrderManagment.csv", 1);
		userName = testData.get("UserName");
		password = testData.get("Password");
		productName = testData.get("productName");
		quantity = Integer.parseInt(testData.get("quantity"));
		reporter.reportStep("Step 1 - Login to Order Management System.");
		OrderManagmentAPIObject orderManagmentAPIObject = new OrderManagmentAPIObject(apiDriver);
		orderManagmentAPIObject.login(userName, password);

		reporter.reportStep("Step 2 - Create product Order.");
		Request request = createShiftPayload(orderManagmentAPIObject, productName, quantity);
		APIResponse response = orderManagmentAPIObject.create(request);
		orderId = response.getNodeValue("results.order.id").toString();
		reporter.reportStep("Step 3 - Verify API response after creation.");
		assertion.verifyEquals(response.getStatusCode(), 200, "Verify status code of API repsonse");
		assertion.verifyEquals(response.getNodeValue("results.order.productName"), productName, "Verify product name");
		assertion.verifyEquals(response.getNodeValue("results.order.quantity"), quantity, "Verify quantity");
	}

	
	@Test(groups = "TEAL", testName = "test_ALM43677_GetOrder", description = "Get an Order", priority=2, dependsOnMethods="test_ALM43676_CreateOrder")
	public void test_ALM43677_GetOrder() throws ParkarCoreCommonException {
		Map<String, String> testData = CSVReader.readCSV("TestCase_OrderManagment.csv", 1);
		userName = testData.get("UserName");
		password = testData.get("Password");
		productName = testData.get("productName");
		quantity = Integer.parseInt(testData.get("quantity"));
		reporter.reportStep("Step 1 - Login to Order Management System.");
		OrderManagmentAPIObject orderManagmentAPIObject = new OrderManagmentAPIObject(apiDriver);
		orderManagmentAPIObject.login(userName, password);

		reporter.reportStep("Step 2 - Get perticular Order");
		orderManagmentAPIObject.orderId = orderId;
		APIResponse response = orderManagmentAPIObject.find();

		reporter.reportStep("Step 3 - Verify API response after finding perticular id.");
		assertion.verifyEquals(response.getStatusCode(), 200, "Verify status code of API repsonse");
		assertion.verifyEquals(response.getNodeValue("results.order.productName"), productName, "Verify product name");
		assertion.verifyEquals(response.getNodeValue("results.order.quantity"), quantity, "Verify quantity");
		assertion.verifyTrue(response.contains("Coffee"),"Verify String");
	}

	
	@Test(groups = "TEAL", testName = "test_ALM43678_UpdateOrder", description = "Create an Order",priority=3,dependsOnMethods="test_ALM43677_GetOrder")
	public void test_ALM43678_UpdateOrder() throws ParkarCoreCommonException {
		Map<String, String> testData = CSVReader.readCSV("TestCase_OrderManagment.csv", 1);
		userName = testData.get("UserName");
		password = testData.get("Password");
		productName = testData.get("productName");
		quantity = Integer.parseInt(testData.get("quantity"));
		updateProductName = testData.get("updateProductName");
		updateQuantity = Integer.parseInt(testData.get("updateQuantity"));

		reporter.reportStep("Step 1 - Login to Order Management System.");
		OrderManagmentAPIObject orderManagmentAPIObject = new OrderManagmentAPIObject(apiDriver);
		orderManagmentAPIObject.login(userName, password);

		reporter.reportStep("Step 2 - Get perticular order for update");
		orderManagmentAPIObject.orderId=orderId;
		Request request = updateShiftPayload(orderManagmentAPIObject, Integer.parseInt(orderManagmentAPIObject.orderId),
				updateProductName, updateQuantity);
		APIResponse response = orderManagmentAPIObject.findUpdate(request);
		System.out.println(orderId);
		
		reporter.reportStep("Step 3 - Verify API response after update.");
		assertion.verifyEquals(response.getStatusCode(), 200, "Verify status code of API repsonse");
		assertion.verifyEquals(response.getNodeValue("results.order.productName"), updateProductName,"Verify product name");
		assertion.verifyEquals(response.getNodeValue("results.order.quantity"), updateQuantity, "Verify quantity");
		assertion.verifyTrue(response.contains("Tea"),"Verify String");

	}

	@Test(groups = "TEAL", testName = "test_ALM43679_DeleteOrder", description = "Create an Order",priority=4, dependsOnMethods="test_ALM43678_UpdateOrder")
	public void test_ALM43679_DeleteOrder() throws ParkarCoreCommonException {
		Map<String, String> testData = CSVReader.readCSV("TestCase_OrderManagment.csv", 1);
		userName = testData.get("UserName");
		password = testData.get("Password");
		productName = testData.get("productName");
		quantity = Integer.parseInt(testData.get("quantity"));

		reporter.reportStep("Step 1 - Login to Order Management System.");
		OrderManagmentAPIObject orderManagmentAPIObject = new OrderManagmentAPIObject(apiDriver);
		orderManagmentAPIObject.login(userName, password);

		reporter.reportStep("Step 2 - Get perticular order for delete");
		orderManagmentAPIObject.orderId=orderId;
		APIResponse response = orderManagmentAPIObject.findDelete();

		reporter.reportStep("Step 3 - Verify API response after delete ");
		assertion.verifyEquals(response.getStatusCode(), 200, "Verify status code of API repsonse");
		assertion.verifyEquals(response.getNodeValue("results.order.productName"), updateProductName, "Verify product name");
		assertion.verifyEquals(response.getNodeValue("results.order.quantity"), updateQuantity, "Verify quantity");
		assertion.verifyFalse(response.contains(productName), "Verify Product Name");

	}

	private Request createShiftPayload(OrderManagmentAPIObject orderManagmentAPIObject, String productName,
			int quantity) throws ParkarCoreCommonException {
		Map<String, Object> order = map("productName", productName, "quantity", quantity);
		return orderManagmentAPIObject.buildPayLoad(order);
	}

	private Request updateShiftPayload(OrderManagmentAPIObject orderManagmentAPIObject, int id, String productName,
			int quantity) throws ParkarCoreCommonException {
		Map<String, Object> order = map("id", id, "productName", productName, "quantity", quantity);
		return orderManagmentAPIObject.buildPayLoad(order);
	}
}
