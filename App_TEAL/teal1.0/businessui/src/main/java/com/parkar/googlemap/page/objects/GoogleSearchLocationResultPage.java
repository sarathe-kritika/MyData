package com.parkar.googlemap.page.objects;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.exception.ParkarCoreCommonException;

public class GoogleSearchLocationResultPage extends BaseUIPage {
	
	String patersonMap="resources\\images\\PatersonMap.png";
	String liverpoolMap="resources\\images\\LiverPoolMap.png";

	
	public GoogleSearchLocationResultPage(WebDriver driver)
			throws ParkarCoreCommonException {
		super(driver);
		waitForPageToLoad();
		// TODO Auto-generated constructor stub
	}
	
	public GoogleSelectedCityPage clickPatersonCity() throws ParkarCoreCommonException, Exception{
		Pattern location = new Pattern(patersonMap);
		Screen src = new Screen();
		src.setAutoWaitTimeout(50);
		src.find(location).highlight(3).click();
		return new GoogleSelectedCityPage(driver); 
	}
	
	public GoogleSelectedCityPage clickLiverPoolCity() throws ParkarCoreCommonException, Exception{
		Pattern location = new Pattern(liverpoolMap);
		Screen src = new Screen();
		src.setAutoWaitTimeout(50);
		src.find(location).highlight(3).click(location);
		return new GoogleSelectedCityPage(driver); 
	}

}
