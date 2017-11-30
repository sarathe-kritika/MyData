package com.parkar.oppenheimerfunds.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class IndividualInvestorPage extends BaseUIPage{
	
	private static final String MYPORTFOLIO_TXT = "//nav[@class='nav']//a[contains(text(),'My Portfolio')]";

	@ParkarFindBy(xpath = "//div[@class='row']//a[contains(text(),'Products')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink productLnk;
	
	@ParkarFindBy(xpath = "//div[@class='popover-body']//a[contains(text(),'Mutual Funds')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink mutualFundsLnk;
	
	public IndividualInvestorPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(MYPORTFOLIO_TXT);
	}
	
	public MutualFundPage goToMutualFundsPage() throws ParkarCoreCommonException{
		productLnk.hover();
		mutualFundsLnk.click();
		return new MutualFundPage(driver);
	}
	
	
	

}
