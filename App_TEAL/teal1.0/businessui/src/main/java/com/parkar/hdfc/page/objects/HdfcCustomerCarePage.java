package com.parkar.hdfc.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.findbysImp.ParkarFindBy;
import com.parkar.helpers.BasicBrowserHelper;

public class HdfcCustomerCarePage extends BaseUIPage{
	
	static String winHandleCustomer = null;
	
	@ParkarFindBy(xpath = "//a[contains(text(),'Call us')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink callUsLnk;
	
	@ParkarFindBy(xpath = "//a[contains(text(),'NRI Customers')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink nriCustomerLnk;


	public HdfcCustomerCarePage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public HdfcPhoneBankingPage clickOnCallUsLnk() throws ParkarCoreCommonException {
		winHandleCustomer = driver.getWindowHandle();
		callUsLnk.click();
		driver.switchTo().frame("ceeboxiframe");
		nriCustomerLnk.click();
		driver.close();
		BasicBrowserHelper.switchingToNewWindowOpened(driver, HdfcHomePage.winHandleHome);
		return new HdfcPhoneBankingPage(driver);
	}

}
