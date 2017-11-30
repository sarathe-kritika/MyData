package com.parkar.hdfc.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseLabel;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.findbysImp.ParkarFindBy;

public class HdfcPhoneBankingPage extends BaseUIPage {
	
	@ParkarFindBy(xpath = "//td[@class='txt_body']//td[@class='table1'][1]/table", via = ParkarLocateVia.ByElementVisible)
	public IBaseLabel openingLbl;
	
	@ParkarFindBy(xpath = "//td[@class='txt_body']//td[@class='table1'][2]/table", via = ParkarLocateVia.ByElementVisible)
	public IBaseLabel accountHolderLbl;

	public HdfcPhoneBankingPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

}
