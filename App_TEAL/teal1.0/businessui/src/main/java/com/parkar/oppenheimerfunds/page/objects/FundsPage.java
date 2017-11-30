package com.parkar.oppenheimerfunds.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.element.interfaces.IBaseCheckBox;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class FundsPage extends MutualFundPage {
	
	private static final String FUNDS_TXT = "//a[@class='funds-landing-ui track-it active']";

	 @ParkarFindBy(xpath ="//input[@value='E']", via = ParkarLocateVia.ByElementClickable)
	 public IBaseCheckBox shareEClassChkBx;
	 
	 @ParkarFindBy(xpath ="//input[@value='A']", via = ParkarLocateVia.ByElementClickable)
	 public IBaseCheckBox shareAClassChkBx;
	 
	 @ParkarFindBy(xpath="//table[@id='fundTable']//tr[1]//a[@class='track-it']",via=ParkarLocateVia.ByElementClickable)
	 public IBaseLink firstFundNameLnk;

	public FundsPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	
	 private void waitForApplicationToLoad() throws ParkarCoreUIException {
	  waitForPageToLoad();
	  waitForApplicationToLoad(FUNDS_TXT);
	 }

	 public void setPerformanceAndPricingShare() throws ParkarCoreUIException{
	  waitForPageToLoad();
	  shareAClassChkBx.setCheck(false);
	  shareEClassChkBx.setCheck(true);
	  waitForPageToLoad();
	 }
	 
	
	 

}
