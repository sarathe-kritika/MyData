package com.parkar.amazon.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseLabel;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class AmazonCartPage extends BaseUIPage {
	
	private static final String AMAZON_LOGO = "//div[@id='nav-logo']";
	
	@ParkarFindBy(xpath = "//input[@value='Delete']", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton deleteBtn;
	
	@ParkarFindBy(xpath = "//h1[contains(text(),'Your Shopping Cart is empty.')]", via = ParkarLocateVia.ByElementVisible)
	public IBaseLabel cartEmptyLbl;
	
	@ParkarFindBy(xpath = "//h1[contains(text(),'Added to Cart')]", via = ParkarLocateVia.ByElementVisible)
	public IBaseLabel itemAddInCartLbl;

	public AmazonCartPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}

	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(AMAZON_LOGO);
	}
	
	public void removeItemFromCart() throws ParkarCoreCommonException{
		deleteBtn.click();
	}
	

}
