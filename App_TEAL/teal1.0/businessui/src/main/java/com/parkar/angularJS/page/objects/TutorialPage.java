package com.parkar.angularJS.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class TutorialPage extends BaseUIPage {

	private static final String ANGULAR_LOGO = "//img[@class='logo']";

	@ParkarFindBy(xpath = "//a[contains(text(),'Angular Templates')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink selectTutorialLnk;

	public TutorialPage(WebDriver driver)
			throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(ANGULAR_LOGO);
	}

	public AngularTemplatePage goToAngularTemplatePage() throws ParkarCoreCommonException {
		selectTutorialLnk.click();
		return new AngularTemplatePage(driver);		
	}


}
