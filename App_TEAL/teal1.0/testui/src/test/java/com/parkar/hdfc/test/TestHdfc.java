package com.parkar.hdfc.test;

import java.lang.reflect.Method;

import org.sikuli.script.FindFailed;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.hdfc.page.objects.HdfcContinueNetbankingPage;
import com.parkar.hdfc.page.objects.HdfcCustomerCarePage;
import com.parkar.hdfc.page.objects.HdfcHomePage;
import com.parkar.hdfc.page.objects.HdfcLoginPage;
import com.parkar.hdfc.page.objects.HdfcPhoneBankingPage;
import com.parkar.testng.BaseUITest;

public class TestHdfc extends BaseUITest {

	@BeforeMethod
	public void setup(Method m) throws ParkarCoreCommonException {
	}

	@AfterMethod
	public void cleanup(Method m, ITestResult result) throws ParkarCoreUIException {
		System.out.println("This is my cleaup in test!!!!!!!!!!");

	}

	@Test(groups = "TEAL", testName = "test_ALM82099_HdfcSite", description = "Test for Hdfc site")
	public void test_ALM82099_HdfcSite() throws ParkarCoreCommonException, FindFailed, InterruptedException  {

		reporter.reportStep("Step 1 - Navigate to Hdfc site.");
		HdfcHomePage hdfcHomePage = new HdfcHomePage(driver);

		reporter.reportStep("Step 2 - Navigate to Login page.");
		HdfcContinueNetbankingPage hdfcContinueNetBankingPage = hdfcHomePage.goToContinueNetbankingPage();

		reporter.reportStep("Step 3 - Navigate to Netbanking page");
		HdfcLoginPage hdfcLoginPage = hdfcContinueNetBankingPage.goToLoginPage();

		reporter.reportStep("Step 4 - Switch back to Hdfc homepage");
		hdfcLoginPage.switchBackHomePage();

		reporter.reportStep("Step 5 - Navigate to Customer care page");
		HdfcCustomerCarePage hdfcCustomerCarePage = hdfcHomePage.goToCustomerCarePage();

		reporter.reportStep("Step 6 - Navigate to Phonebanking page");
		HdfcPhoneBankingPage hdfcPhoneBankingPage = hdfcCustomerCarePage.clickOnCallUsLnk();

		assertion.verifyTrue(hdfcPhoneBankingPage.openingLbl.isDisplayed(), "Verify Account opening assistance table is displayed");
		assertion.verifyTrue(hdfcPhoneBankingPage.accountHolderLbl.isDisplayed(), "Verify Account holders queries is displayed");

	}

}
