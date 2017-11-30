package com.parkar.hdfc.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseLabel;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.findbysImp.ParkarFindBy;
import com.parkar.helpers.BasicBrowserHelper;

public class HdfcLoginPage extends BaseUIPage{
	
	@ParkarFindBy(xpath = "//td[contains(text(),'NetBanking Login')]", via = ParkarLocateVia.ByElementVisible)
	public IBaseLabel loginLbl;
	
	

	public HdfcLoginPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public HdfcHomePage switchBackHomePage() throws ParkarCoreCommonException{
		driver.close();
		BasicBrowserHelper.switchToWindow(driver, HdfcHomePage.winHandleHome);
		return new HdfcHomePage(driver);
		
	}

}
