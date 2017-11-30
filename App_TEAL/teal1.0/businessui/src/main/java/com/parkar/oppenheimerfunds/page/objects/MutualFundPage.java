package com.parkar.oppenheimerfunds.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class MutualFundPage extends IndividualInvestorPage {
	
	private static final String MUTUALFUND_TXT = "//h1[contains(text(),'Mutual Funds')]";
	
	@ParkarFindBy(xpath = "//a[contains(text(),'Historical Prices')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink historicalPricesLnk;
	
	@ParkarFindBy(xpath = "//h1[contains(text(),'Mutual Funds')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink fundsLnk;
	
	public MutualFundPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(MUTUALFUND_TXT);
	}
	
	public HistoricalPricesPage goToHistoricalPricesPage() throws ParkarCoreCommonException  {
		historicalPricesLnk.click();
		return new HistoricalPricesPage(driver);
		
	}
	
	public FundsPage goToFundsPage() throws ParkarCoreCommonException {
		fundsLnk.click();
		return new FundsPage(driver);
	}
	
	
	

}
