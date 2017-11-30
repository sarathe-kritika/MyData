package com.parkar.wordpress.test;

import java.util.Map;

import org.testng.annotations.Test;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.testng.BaseMobileTest;
import com.parkar.utils.common.CSVReader;
import com.parkar.wordpress.page.objects.LoginPage;

public class TestWordPress extends BaseMobileTest{

	@Test(groups = "TEAL", testName = "test_ALM82066_LoginWordPress", description = "Login WordPress functionality")
	public void test_ALM82067_LoginWordPress() throws ParkarCoreCommonException {
		
		// Read test data
		Map<String, String> testData = CSVReader.readCSV("TestCase_WordPress.csv", "ALM82067");
		String userName = testData.get("UserName");
		String password = testData.get("Password");
		
		reporter.reportStep("Step 1 - Login to WordPress account");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginApp(userName, password);
	}
}
