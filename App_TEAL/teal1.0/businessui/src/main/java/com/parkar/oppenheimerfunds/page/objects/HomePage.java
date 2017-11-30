package com.parkar.oppenheimerfunds.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class HomePage extends BaseUIPage {
	
	private static final String OPPENHEIMER_LOGO = "//div[@class='sidebar']/div[@class='logo']";
		
	@ParkarFindBy(xpath = "//div[@id='megamenuProducts']//a[contains(text(),'Mutual Funds')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink mutualFundLnk;
	
	@ParkarFindBy(xpath = "//section//div[@class='logo']//following-sibling::div//a[text()='Individual Investor']", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink individualInvestorLnk;

	public HomePage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(OPPENHEIMER_LOGO);
	}
	
	public IndividualInvestorPage goToIndividualInvestorPage() throws ParkarCoreCommonException  {
		individualInvestorLnk.click();
		return new IndividualInvestorPage(driver);	
	}
	
}
