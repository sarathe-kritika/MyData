package com.parkar.hdfc.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseLabel;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.findbysImp.ParkarFindBy;

public class HdfcContinueNetbankingPage extends BaseUIPage {
	
	@ParkarFindBy(xpath = "//td[@class='nbl_ttl']", via = ParkarLocateVia.ByElementVisible)
	public IBaseLabel loginLbl;
	
	@ParkarFindBy(xpath = "//div[@class='button']//img", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton continueNetBankingBtn;
	
	@ParkarFindBy(xpath = "//a[contains(text(),'Customer Care')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink customerCareLnk;

	public HdfcContinueNetbankingPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public HdfcLoginPage goToLoginPage() throws ParkarCoreCommonException{
		continueNetBankingBtn.click();
		return new HdfcLoginPage(driver);
		
	}
	
	

}
