package com.parkar.amazon.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.element.interfaces.IBaseTextBox;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class AmazonHomePage extends BaseUIPage{
	
	private static final String AMAZON_LOGO = "//div[@id='nav-logo']";
	
	@ParkarFindBy(id = "twotabsearchtextbox", via = ParkarLocateVia.ByElementClickable)
	public IBaseTextBox searchItemTxtBx;
	
	@ParkarFindBy(xpath = "//input[@value='Go']", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton searchBtn;
	
	@ParkarFindBy(id = "nav-link-yourAccount", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink signInLnk;
	
	@ParkarFindBy(id = "nav-cart", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink cartLnk;
	
	@ParkarFindBy(xpath = "//a[@id='nav-link-yourAccount']/span[2]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink myAccountLnk;
	
	@ParkarFindBy(xpath = "//a[@id='nav-item-signout']/span", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink signOutLnk;

	public AmazonHomePage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(AMAZON_LOGO);
	}
	
	public AmazonLoginPage goToLoginPage() throws ParkarCoreCommonException {
		signInLnk.click();
		return new AmazonLoginPage(driver);
	}
	
	public AmazonSearchResultPage searchItem(String item) throws ParkarCoreCommonException {
		searchItemTxtBx.setText(item);
		searchBtn.click();
		return new AmazonSearchResultPage(driver);
		
	}
	
	public AmazonCartPage goToCartPage() throws ParkarCoreCommonException{
		cartLnk.click();
		return new AmazonCartPage(driver);
	}
	
	
	public void logOut() throws ParkarCoreCommonException{
		myAccountLnk.hover();
		signOutLnk.click();
	}
	


}
