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

public class TestMailsMoveToFolders extends BaseUITest {
	
	private String subject;
	private String userName;
	private String password;
	private String to;
	private String body;

	@BeforeMethod
	public void setup(Method m) throws ParkarCoreCommonException {
	}

	@AfterMethod
	public void cleanup(Method m, ITestResult result)
			throws ParkarCoreUIException {

	}

 @Test(groups = "TEAL1", testName = "test_ALM82058_MoveToTrash", description = "Verify move to trash functionality")
 public void test_ALM82058_MoveToTrash() throws ParkarCoreCommonException{
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
		ComposeMailPage composeMail = homePage.goToComposeMailPage();
		
		reporter.reportStep("Step 5 - Compose and send email");
		composeMail.sendEmail(to, subject, body);
		
		reporter.reportStep("Step 6 - Search sent email");
		DashboardPage dashBoardPage= homePage.searchEmail(subject);
		
		reporter.reportStep("Step 7 - Delete email");
		dashBoardPage.selectAllEmail("2");
		dashBoardPage.deleteAllEmail();
		
		reporter.reportStep("Step 8 - Verify Email in Trash");
		homePage.searchEmail("in:trash "+subject);
		assertion.verifyTrue(dashBoardPage.getEmailSubject(subject).isPresent()," Verify email inside trash");		
 }
 
 @Test(groups = "TEAL1", testName = "test_ALM82059_MoveToInbox", description = "Verify move to Inbox functionality", dependsOnMethods="test_ALM82058_MoveToTrash")
 public void test_ALM82059_MoveToInbox() throws ParkarCoreCommonException{
		List<Map<String, String>> testCSVData = CSVReader.readCSV("TestCase_Gmail.csv");
		Map<String, String> testData = testCSVData.get(0);
		userName = testData.get("UserName");
		password = testData.get("Password");
		to = testData.get("To");
		body = testData.get("Body");

		reporter.reportStep("Step 1 - Login to gmail account");
		LoginUserIDPage loginIdPage = new LoginUserIDPage(driver);
		
		reporter.reportStep("Step 2 - Set user name");
		LoginPasswordPage loginPasswordPage = loginIdPage.loginAsUser(userName);
		
		reporter.reportStep("Step 3 - Set password");
		HomePage homePage = loginPasswordPage.setPassword(password);
		
		reporter.reportStep("Step 4 - Verify Email inside trash box");
		DashboardPage dashBoardPage= homePage.searchEmail("in:trash "+subject);
		assertion.verifyTrue(dashBoardPage.getEmailSubject(subject).isPresent()," Verify email inside trash");
		
		reporter.reportStep("Step 5- Move Email from Trash to Inbox");
		dashBoardPage.selectAllEmail("2");
		dashBoardPage.moveToInbox();   
		
		reporter.reportStep("Step 6 - Search and verify email not available in trash");
		homePage.searchEmail("in:trash "+subject);
		assertion.verifyTrue(dashBoardPage.getSearchEmailMsg("No messages matched your search").isPresent(), "Verify email is not present in trash");
		
 }

 
 
}