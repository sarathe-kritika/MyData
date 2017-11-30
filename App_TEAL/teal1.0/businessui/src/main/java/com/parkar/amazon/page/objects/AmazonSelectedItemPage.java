package com.parkar.amazon.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;
import com.parkar.helpers.BasicBrowserHelper;

public class AmazonSelectedItemPage extends BaseUIPage{
	
	private static final String AMAZON_LOGO = "//div[@id='nav-logo']";
	
	@ParkarFindBy(id = "add-to-cart-button", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton addToCartBtn;
	
	public AmazonSelectedItemPage(WebDriver driver)
			throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
		
	}

	public AmazonCartPage addItemToCart() throws ParkarCoreCommonException{
		String winHandleBefore = driver.getWindowHandle();
		BasicBrowserHelper.switchingToNewWindowOpened(driver, winHandleBefore);
		addToCartBtn.click();
		return new AmazonCartPage(driver);
		
	}
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(AMAZON_LOGO);
	}
}
