package com.parkar.highcharts.test;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.highcharts.page.objects.HighChartsHomePage;
import com.parkar.testng.BaseUITest;
import com.parkar.utils.common.CSVReader;

public class TestHighCharts extends BaseUITest {

	@BeforeMethod
	public void setup(Method m) throws ParkarCoreCommonException {
	}

	@AfterMethod
	public void cleanup(Method m, ITestResult result)
			throws ParkarCoreUIException {
		System.out.println("This is my cleaup in test!!!!!!!!!!");

	}

	@Test(groups = "PTAF", testName = "test_ALM82070_ReadHighCharts", description = "Verify data of high charts")
	public void test_ALM82070_ReadHighCharts()
			throws ParkarCoreCommonException{

		// Read test data
		Map<String, String> verifyData_1 = CSVReader.readCSV("TestCase_HighCharts.csv", "Title");
		String chartTitle = verifyData_1.get("VerifyText");

		Map<String, String> verifyData_2 = CSVReader.readCSV("TestCase_HighCharts.csv", "Sub_Title");
		String chartSubTitle = verifyData_2.get("VerifyText");

		reporter.reportStep("Step 1 - Navigate to high charts url");
		HighChartsHomePage highChartsHomePage = new HighChartsHomePage(driver);

		assertion.verifyEquals(chartTitle,highChartsHomePage.averageTempLbl.getText(),"Verify the text monthly average temperature");

		assertion.verifyEquals(chartSubTitle,highChartsHomePage.sourceLbl.getText(),"Verify the source text of graph");

		reporter.reportStep("Step 2 - Read tool tip");
		highChartsHomePage.hoverDataPoints();

		assertion.verifyTrue(highChartsHomePage.tempToolTipLbl.isDisplayed(), "Verify temperature is displayed in tool tip");

	    assertion.verifyTrue(highChartsHomePage.monthToolTipLbl.isDisplayed(),"Verify month is displayed in tool tip");

		assertion.verifyTrue(highChartsHomePage.cityToolTipLbl.isDisplayed(),"Verify month is displayed in tool tip");
        
		reporter.reportStep("Step 3 - Download chart");
		highChartsHomePage.downloadGraph();
		
	}
	
	@Test(groups = "PTAF", testName = "test_ALM82070_RemoveGraph", description = "Verify remove graph of locations")
	public void test_ALM82070_RemoveGraph()
			throws ParkarCoreCommonException{

		reporter.reportStep("Step 1 - Navigate to high charts url");
		HighChartsHomePage highChartsHomePage = new HighChartsHomePage(driver);
		
		reporter.reportStep("Step 2 - Remove Tokyo graph from chart");
		highChartsHomePage.removeTokyoGraph();

		reporter.reportStep("Step 3 - Remove Newyork graph from chart");
		highChartsHomePage.removeNewYorkGraph();

		reporter.reportStep("Step 4 - Remove Berlin graph from chart");
		highChartsHomePage.removeBerlinGraph();

		reporter.reportStep("Step 5 - Remove London graph from chart");
		highChartsHomePage.removeLondonGraph();

		reporter.reportStep("Step 6 - Bring back all the location graph");
		highChartsHomePage.plotGraphAgain();
	}

}
