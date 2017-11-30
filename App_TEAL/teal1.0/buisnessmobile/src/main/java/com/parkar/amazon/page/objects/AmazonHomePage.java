package com.parkar.amazon.page.objects;

import io.appium.java_client.AppiumDriver;

import com.parkar.basepageobject.BaseMobilePage;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.findbysImp.ParkarFindBy;

public class AmazonHomePage extends BaseMobilePage{
	
	//private static final String AMAZON_LOGO="nav-logo";

	@ParkarFindBy(webId="nav-logobar-greeting", via=ParkarLocateVia.ByElementClickable, timeout=80)
	IBaseLink NAV_GREETING_LNK;
	
	public AmazonHomePage(AppiumDriver<?> driver) throws ParkarCoreCommonException {
		super(driver);
		//waitForApplicationToLoad(AMAZON_LOGO, 80);
		// TODO Auto-generated constructor stub
	}
	
	public AmazonLoginPage navigateToLoginPage() throws ParkarCoreCommonException{
		NAV_GREETING_LNK.click();
		return new AmazonLoginPage(driver);
	}

}
