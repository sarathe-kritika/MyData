package com.parkar.amazon.test;

import java.util.Map;

import org.testng.annotations.Test;

import com.parkar.amazon.page.objects.AmazonHomePage;
import com.parkar.amazon.page.objects.AmazonLoginPage;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.testng.BaseMobileTest;
import com.parkar.utils.common.CSVReader;

public class TestAmazon extends BaseMobileTest{   
		@Test(groups = "TEAL", testName = "test_ALM82066_LoginAmazon", description = "Login Amazon functionality")
		public void test_ALM82066_LoginWordPress() throws ParkarCoreCommonException{
			
			// Read test data
			Map<String, String> testData = CSVReader.readCSV("TestCase_Amazon.csv", "ALM82066");
			String userName = testData.get("UserName");
			String password = testData.get("Password");
			reporter.reportStep("Step 1 - Navigate to Amazon Login Page");
			AmazonHomePage amazonHomePage=new AmazonHomePage(driver);
			AmazonLoginPage amazonLoginPage=amazonHomePage.navigateToLoginPage();
			
			reporter.reportStep("Step 2 - Login to Amazon account");
			amazonLoginPage.loginToAmazon(userName, password);
		}

}
