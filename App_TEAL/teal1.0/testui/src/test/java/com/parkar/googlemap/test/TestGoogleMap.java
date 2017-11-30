package com.parkar.googlemap.test;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.googlemap.page.objects.GoogleMapHomePage;
import com.parkar.googlemap.page.objects.GoogleSearchLocationResultPage;
import com.parkar.googlemap.page.objects.GoogleSelectedCityPage;
import com.parkar.testng.BaseUITest;

public class TestGoogleMap extends BaseUITest{
	
	@BeforeMethod
	public void setup(Method m) throws ParkarCoreCommonException {
		}

	@AfterMethod
	public void cleanup(Method m, ITestResult result)
			throws ParkarCoreUIException {
		System.out.println("This is my cleaup in test!!!!!!!!!!");

	}

	@Test(groups = "TEAL", testName = "test_ALM82766_SearchPatersonCity", description = "Search paterson city in google map")
	public void test_ALM82766_SearchPatersonCity() throws Exception {
		
	    reporter.reportStep("Step 1 - Navigate to Google map url");
		GoogleMapHomePage googleMapHomePage = new GoogleMapHomePage(driver);
		
		reporter.reportStep("Step 2 - Search New York location");
		GoogleSearchLocationResultPage googleSearchResultPage = googleMapHomePage.searchLocation("New York, NY, United States");
		
	    reporter.reportStep("Step 3 - Select Paterson city ");
		GoogleSelectedCityPage googleSelectedCityPage = googleSearchResultPage.clickPatersonCity();
        
		reporter.reportStep("Step 4 - Hover on William Paterson university ");
	    googleSelectedCityPage.hoverLocation();
	    
	    assertion.verifyTrue(googleSelectedCityPage.patersonTxt.isDisplayed(),"Verify Paterson city is selected");
	    
	    reporter.reportStep("Step 5 - Move Page man on Google Map ");
	    googleMapHomePage.movePageManOnGoogleMap();
	    
	   }
	
	@Test(groups = "TEAL", testName = "test_ALM82767_SearchLiverpoolCity", description = "Search Liverpool city in google map")
	public void test_ALM82767_SearchLiverpoolCity() throws Exception {
		
		
		reporter.reportStep("Step 1 - Navigate to Google map url");
		GoogleMapHomePage googleMapHomePage = new GoogleMapHomePage(driver);
		
		reporter.reportStep("Step 2 - Search Sydney location");
	    GoogleSearchLocationResultPage googleSearchResultPage = googleMapHomePage.searchLocation("Sydney, New South Wales, Australia");
	    
	    reporter.reportStep("Step 3 - Select Liverpool city");
	    GoogleSelectedCityPage googleSelectedCityPage = googleSearchResultPage.clickLiverPoolCity();
	    
	    assertion.verifyTrue(googleSelectedCityPage.liverPoolTxt.isDisplayed(),"Verify Liverpool city is selected");
	     
	    reporter.reportStep("Step 4 - Search direction");
	    googleSelectedCityPage.direction("Green Valley ");
	   
	    reporter.reportStep("Step 5 - Select Car route");
	    googleSelectedCityPage.selectCarRoute();
	    
	    reporter.reportStep("Step 6 - Select Bus route");
	    googleSelectedCityPage.selectBusRoute();
	    
	    reporter.reportStep("Step 7 - Select Walk route");
	    googleSelectedCityPage.selectWalkRoute();
	    
	   
	    
	    }

}
