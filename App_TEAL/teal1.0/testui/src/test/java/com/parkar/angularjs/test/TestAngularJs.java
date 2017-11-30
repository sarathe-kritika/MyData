package com.parkar.angularjs.test;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.parkar.angularJS.page.objects.AngularJSHomePage;
import com.parkar.angularJS.page.objects.AngularTemplatePage;
import com.parkar.angularJS.page.objects.TutorialPage;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.testng.BaseUITest;

public class TestAngularJs extends BaseUITest {
	@BeforeMethod
	public void setup(Method m) throws ParkarCoreCommonException {
		}

	@AfterMethod
	public void cleanup(Method m, ITestResult result)
			throws ParkarCoreUIException {
		System.out.println("This is my cleaup in test!!!!!!!!!!");

	}
	
	@Test(groups = "TEAL", testName = "test_ALM82079_AngularJS", description = "Test for AngularJS site")
	public void test_ALM82079_AngularJS() throws ParkarCoreCommonException {
		
		reporter.reportStep("Step 1 - Navigate to AngularJS site.");
		AngularJSHomePage angularJSHomePage= new AngularJSHomePage(driver);
		
		reporter.reportStep("Step 2 - Navigate to Tutorial Page");
		TutorialPage tutorialPage = angularJSHomePage.gotToTutorialPage();
		
		reporter.reportStep("Step 3 - Navigate to Angular Template");
		AngularTemplatePage angularTemplatePage = tutorialPage.goToAngularTemplatePage();
		
		assertion.verifyTrue(angularTemplatePage.angularTemplateLbl.isDisplayed()," Verify Angular Template Page");
		
	}

}
