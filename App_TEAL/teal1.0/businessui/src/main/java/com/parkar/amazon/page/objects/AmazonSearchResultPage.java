package com.parkar.amazon.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class AmazonSearchResultPage extends BaseUIPage{
	
	private static final String AMAZON_LOGO = "//div[@id='nav-logo']";
	
	@ParkarFindBy(xpath = "//li[@id='result_0']//img", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink searchItemImg;
	
	
	public AmazonSearchResultPage(WebDriver driver)throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	
	public AmazonSelectedItemPage goToAmazonSelectedItemPage() throws ParkarCoreCommonException{
		searchItemImg.click();
		return new AmazonSelectedItemPage(driver);
		
	}
	
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(AMAZON_LOGO);
	}

	

}
