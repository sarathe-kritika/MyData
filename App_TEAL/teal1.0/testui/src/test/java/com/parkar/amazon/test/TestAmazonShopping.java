package com.parkar.amazon.test;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.parkar.amazon.page.objects.AmazonCartPage;
import com.parkar.amazon.page.objects.AmazonHomePage;
import com.parkar.amazon.page.objects.AmazonLoginPage;
import com.parkar.amazon.page.objects.AmazonSearchResultPage;
import com.parkar.amazon.page.objects.AmazonSelectedItemPage;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.testng.BaseUITest;
import com.parkar.utils.common.CSVReader;

public class TestAmazonShopping extends BaseUITest {

	private String userName;
	private String password;

	@BeforeMethod
	public void setup(Method m) throws ParkarCoreCommonException {
		}

	@AfterMethod
	public void cleanup(Method m, ITestResult result)
			throws ParkarCoreUIException {
		System.out.println("This is my cleaup in test!!!!!!!!!!");

	}

	@Test(groups = "TEAL", testName = "test_ALM82066_AddItemInCart", description = "Verify AddToCart functionality")
	public void test_ALM82066_AddItemInCart() throws ParkarCoreCommonException {
		
		// Read test data
		Map<String, String> testData = CSVReader.readCSV("TestCase_Amazon.csv", "ALM82066");
		userName = testData.get("UserName");
		password = testData.get("Password");

		reporter.reportStep("Step 1 - Click on signIn user");
		AmazonHomePage amazonHomePage = new AmazonHomePage(driver);
		AmazonLoginPage amazonLoginPage = amazonHomePage.goToLoginPage();

		reporter.reportStep("Step 2 - Login to Amazon account");
		amazonHomePage= amazonLoginPage.login(userName, password);

		reporter.reportStep("Step 3 - Search Item for purchase");
		AmazonSearchResultPage amazonSearchResultPage = amazonHomePage.searchItem("earphones");

		reporter.reportStep("Step 4 - Select a searched Item");
		AmazonSelectedItemPage amazonSelectedItemPage = amazonSearchResultPage.goToAmazonSelectedItemPage();

		reporter.reportStep("Step 5 - Add Item to cart");
		AmazonCartPage amazonCartPage = amazonSelectedItemPage.addItemToCart();
		
		assertion.verifyTrue(amazonCartPage.itemAddInCartLbl.isDisplayed(),"Verify Item get added succesfully");

	}

	@Test(groups = "TEAL", testName = "test_ALM82067_DeleteItemFromCart", description = "Verify delete item from cart Functionality", dependsOnMethods = "test_ALM82066_AddItemInCart")
	public void test_ALM82067_DeleteItemFromCart() throws ParkarCoreCommonException {
		
		// Read test data
		Map<String, String> testData = CSVReader.readCSV("TestCase_Amazon.csv", "ALM82067");
		userName = testData.get("UserName");
		password = testData.get("Password");

		AmazonHomePage amazonHomePage = new AmazonHomePage(driver);

		reporter.reportStep("Step 1 - Click on signIn user");
		AmazonLoginPage amazonLoginPage = amazonHomePage.goToLoginPage();

		reporter.reportStep("Step 2 - Login to Amazon account");
		amazonHomePage = amazonLoginPage.login(userName, password);

		reporter.reportStep("Step 3 - Click on cart link");
		AmazonCartPage amazonCartPage = amazonHomePage.goToCartPage();

		reporter.reportStep("Step 4 - Remove Item from cart");
		amazonCartPage.removeItemFromCart();

		reporter.reportStep("Step 5 - Verify Item got removed from cart");
		amazonCartPage= amazonHomePage.goToCartPage();

		assertion.verifyTrue(amazonCartPage.cartEmptyLbl.isDisplayed(),"Verify there is no Item in cart");

	}

}
