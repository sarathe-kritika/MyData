package com.parkar.gmail.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseLabel;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.enums.LocatorType;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class DashboardPage extends HomePage {

	@ParkarFindBy(xpath = "//div[@gh='tm']//div[@class='ar9 T-I-J3 J-J5-Ji']", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton deleteMailBtn;

	@ParkarFindBy(xpath = "//span[@class='CJ']", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton moreBtn;

	@ParkarFindBy(xpath = "//a[@class='J-Ke n0 aBU']", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton trashBtn;

	@ParkarFindBy(xpath = "//div[@class='D E G-atb'][2]//div[contains(text(),'Move to Inbox')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton moveToInboxBtn;

	public DashboardPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
	}
	

	public void deleteAllEmail() throws ParkarCoreCommonException {
		deleteMailBtn.click();
		waitForPageToLoad();
	}
	
	public void selectAllEmail(String index) throws ParkarCoreUIException{
		getSelectAllEmailCheckBox(index).setCheck(true);	
	}
	

	public void moveToInbox() throws ParkarCoreCommonException {
		moveToInboxBtn.click();
	}
	
	public IBaseLabel getSearchEmailMsg(String partialLocator) throws ParkarCoreUIException {
		String locator = "//td[contains(text(),'" + partialLocator + "')]";
		return initElement(locator, LocatorType.XPATH, ParkarLocateVia.ByElementPresent);
	}
}
