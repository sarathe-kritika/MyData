package com.parkar.gmail.test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.gmail.page.objects.ComposeMailPage;
import com.parkar.gmail.page.objects.DashboardPage;
import com.parkar.gmail.page.objects.HomePage;
import com.parkar.gmail.page.objects.LoginPasswordPage;
import com.parkar.gmail.page.objects.LoginUserIDPage;
import com.parkar.testng.BaseUITest;
import com.parkar.utils.common.CSVReader;

public class TestGmail extends BaseUITest {
	
	private String subject;
	private String userName;
	private String password;
	private String to;
	private String body;

	@BeforeMethod
	public void setup(Method m) throws ParkarCoreCommonException {
	}

	@AfterMethod
	public void cleanup(Method m, ITestResult result)throws ParkarCoreUIException {
		System.out.println("This is my cleaup in test!!!!!!!!!!");
		// code whatever you need.
	}

	@Test(groups = "TEAL", testName = "test_ALM82056_ComposeMail", description = "Verify ComposeMail functionality")
	public void test_ALM82056_ComposeMail() throws ParkarCoreCommonException {
		// Read test data
		List<Map<String, String>> testCSVData = CSVReader.readCSV("TestCase_Gmail.csv");
		Map<String, String> testData = testCSVData.get(0);
		userName = testData.get("UserName");
		password = testData.get("Password");
		to = testData.get("To");
		subject = testData.get("Subject");
		body = testData.get("Body");
		subject = subject + dateGenerator.getToday();
		
		reporter.reportStep("Step 1 - Login to Gmail account");
		LoginUserIDPage loginIdPage = new LoginUserIDPage(driver);
		
		reporter.reportStep("Step 2 - Set user name");
		LoginPasswordPage loginPasswordPage = loginIdPage.loginAsUser(userName);
		
		reporter.reportStep("Step 3 - Set password");
		HomePage homePage = loginPasswordPage.setPassword(password);

		reporter.reportStep("Step 4 - Navigate to Compose email page");
		ComposeMailPage composeMailPage = homePage.goToComposeMailPage();
		
		reporter.reportStep("Step 5 - Compose and send email");
		composeMailPage.sendEmail(to, subject, body);
		
		reporter.reportStep("Step 6 - Search sent email");
		DashboardPage dashBoardPage= homePage.searchEmail(subject);
		
		assertion.verifyTrue(dashBoardPage.getEmailSubject(subject).isPresent()," Verify email received");
		
		reporter.reportStep("Step 7 - Logout from application");
		loginPasswordPage = homePage.logOut();

	}

	@Test(groups = "TEAL", testName = "test_ALM82057_DeleteMail", description = "Verify DeleteMail Functionality", dependsOnMethods = "test_ALM82056_ComposeMail")
	public void test_ALM82057_DeleteMail() throws ParkarCoreCommonException {
		// Read test data
		List<Map<String, String>> testCSVData = CSVReader.readCSV("TestCase_Gmail.csv");
		Map<String, String> testData = testCSVData.get(0);
		userName = testData.get("UserName");
		password = testData.get("Password");
		to = testData.get("To");
		body = testData.get("Body");
		
		reporter.reportStep("Step 1 - Login to Gmail account");
		LoginUserIDPage loginIdPage = new LoginUserIDPage(driver);
		
		reporter.reportStep("Step 2 - Set user name");
		LoginPasswordPage loginPasswordPage = loginIdPage.loginAsUser(userName);
		
		reporter.reportStep("Step 3 - Set password");
		HomePage homePage = loginPasswordPage.setPassword(password);

		reporter.reportStep("Step 4 - Search for email");
		DashboardPage dashBoardPage = homePage.searchEmail(subject);

		reporter.reportStep("Step 5 - Delete email");
		dashBoardPage.selectAllEmail("2");
		dashBoardPage.deleteAllEmail();
		
		reporter.reportStep("Step 6 - Verify the email is deleted");
		dashBoardPage = homePage.searchEmail(subject);
		
		assertion.verifyTrue(dashBoardPage.getSearchEmailMsg("Some messages in Trash or Spam match your search.").isPresent(), "Verify email is not present in Trash");
	}

	
}
