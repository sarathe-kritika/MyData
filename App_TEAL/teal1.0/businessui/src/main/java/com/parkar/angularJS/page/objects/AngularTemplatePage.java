package com.parkar.angularJS.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class AngularTemplatePage extends TutorialPage {
	
	private static final String TEMPLATE_LBL = "//h2[@id='view-and-template']";
	
	@ParkarFindBy(xpath = "//h2[@id='view-and-template']", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink angularTemplateLbl;
	
	public AngularTemplatePage(WebDriver driver)throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	

	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(TEMPLATE_LBL);
	}

}
