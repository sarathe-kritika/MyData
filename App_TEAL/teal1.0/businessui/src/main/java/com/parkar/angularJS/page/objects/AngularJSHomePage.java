package com.parkar.angularJS.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseClick;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class AngularJSHomePage extends BaseUIPage {

	private static final String ANGULARJS_IMG = "//img[@class='AngularJS-small']";

	@ParkarFindBy(xpath = "//ul[@class='nav']/li[4]/a", via = ParkarLocateVia.ByElementClickable)
	public IBaseClick learnDrpDwn;

	@ParkarFindBy(xpath = "//div[@class='container']//a[contains(text(),'Tutorial')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink tutorialLnk;

	public AngularJSHomePage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		// TODO Auto-generated constructor stub
		waitForApplicationToLoad();
	}

	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForAngularRequestsToFinish();
		waitForApplicationToLoad(ANGULARJS_IMG);
	}
	
	public TutorialPage gotToTutorialPage()
			throws ParkarCoreCommonException {
		learnDrpDwn.click();
		tutorialLnk.click();
		waitForAngularRequestsToFinish();
		return new TutorialPage(driver);
	}


}
