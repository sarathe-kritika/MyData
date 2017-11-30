package com.parkar.oppenheimerfunds.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseLabel;
import com.parkar.element.interfaces.IBaseSelect;
import com.parkar.enums.LocatorType;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class HistoricalPricesPage extends MutualFundPage{
	
	private static final String HISTORICALPRICES_TXT = "//h2[contains(text(),'Fund Historical Prices')]";
    
	@ParkarFindBy(name="selected_fund[0]",via=ParkarLocateVia.ByElementClickable)
	 public IBaseSelect fundDrpDwn;
	
	@ParkarFindBy(name="selected_class[0]",via=ParkarLocateVia.ByElementClickable)
	 public IBaseSelect shareClassDrpDwn;
	
	@ParkarFindBy(xpath="//button[contains(text(),'Go')]",via=ParkarLocateVia.ByElementClickable)
	 public IBaseButton goBtn;
	
	public HistoricalPricesPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(HISTORICALPRICES_TXT);
	}
	
	public void selectFirstFundAndShareClass(String fundName , String shareClass) throws ParkarCoreUIException{
		fundDrpDwn.selectByVisibleText(fundName);
		shareClassDrpDwn.selectByVisibleText(shareClass);
		goBtn.click();
		
	}
	
	public IBaseLabel getGraphHeader(String fundName , String shareClass) throws ParkarCoreUIException {
		String locator = "//div[contains(text(),'" + fundName+" (Class "+shareClass+")')]";
		return initElement(locator, LocatorType.XPATH, ParkarLocateVia.ByElementPresent);
	}
	

}
