package com.parkar.oppenheimerfunds.test;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.oppenheimerfunds.page.objects.FundsPage;
import com.parkar.oppenheimerfunds.page.objects.HistoricalPricesPage;
import com.parkar.oppenheimerfunds.page.objects.HomePage;
import com.parkar.oppenheimerfunds.page.objects.IndividualInvestorPage;
import com.parkar.oppenheimerfunds.page.objects.MutualFundPage;
import com.parkar.testng.BaseUITest;
import com.parkar.utils.common.CSVReader;

public class TestOppenheimerFunds extends BaseUITest{
	
	@BeforeMethod
	public void setup(Method m) throws ParkarCoreCommonException {
		}

	@AfterMethod
	public void cleanup(Method m, ITestResult result) throws ParkarCoreUIException {
		System.out.println("This is my cleaup in test!!!!!!!!!!");

	}

	@Test(groups = "TEAL", testName = "test_ALM82101_CompareHistoricalPriceData", description = "Verify compare historical price data")
	public void test_ALM82101_CompareHistoricalPriceData() throws ParkarCoreCommonException {
		// Read test data
		Map<String, String> testData = (Map<String, String>) CSVReader.readCSV("TestCase_OppenheimerFunds.csv" , 1);
		String fundName = testData.get("Fund_Name");
		String shareClass = testData.get("Share_Class");
		
		reporter.reportStep("Step 1 - Navigate to OppenheimerFunds url");
		HomePage homePage  = new HomePage(driver);
		
		reporter.reportStep("Step 2 - Navigate to Individual investor page");
		IndividualInvestorPage individualInvestorPage = homePage.goToIndividualInvestorPage();
		
		reporter.reportStep("Step 3 - Navigate to Mutual fund page");
		MutualFundPage mutualFundPage = individualInvestorPage.goToMutualFundsPage();

		reporter.reportStep("Step 4 - Navigate to Historical price page");
		HistoricalPricesPage historicalPricesPage = mutualFundPage.goToHistoricalPricesPage();
		
		reporter.reportStep("Step 5 - Get the historical price data for specified fund name and share class");
		historicalPricesPage.selectFirstFundAndShareClass(fundName, shareClass);
		
		assertion.verifyTrue(historicalPricesPage.getGraphHeader(fundName, shareClass).isPresent()," Verify graph header");
		
		
	}
	
	@Test(groups = "TEAL", testName = "test_ALM82102_PerformanceAndPricingData", description = "Verify fund name of performance and pricing data")
	public void test_ALM82102_PerformanceAndPricingData() throws ParkarCoreCommonException{
		//Read test data
		Map<String, String> testData = (Map<String, String>) CSVReader.readCSV("TestCase_OppenheimerFunds.csv" , 2);
		String shareClass = testData.get("Share_Class");
		
		reporter.reportStep("Step 1 - Navigate to OppenheimerFunds url");
		HomePage homePage  = new HomePage(driver);
		
		reporter.reportStep("Step 2 - Navigate to Individual investor page");
		IndividualInvestorPage individualInvestorPage = homePage.goToIndividualInvestorPage();
		
		reporter.reportStep("Step 3 - Navigate to Mutual fund page");
		MutualFundPage mutualFundPage = individualInvestorPage.goToMutualFundsPage();

		reporter.reportStep("Step 4 - Navigate to Funds page");
		FundsPage fundsPage = mutualFundPage.goToFundsPage();
		
		reporter.reportStep("Step 5 - Set performance and pricing share");
		fundsPage.setPerformanceAndPricingShare();
				
		assertion.verifyTrue(fundsPage.firstFundNameLnk.getText().contains(shareClass),"Verify Fund share class");
				
	}

}
