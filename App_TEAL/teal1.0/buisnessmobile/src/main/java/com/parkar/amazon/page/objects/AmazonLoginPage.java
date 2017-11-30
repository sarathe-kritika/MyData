package com.parkar.amazon.page.objects;

import io.appium.java_client.AppiumDriver;

import com.parkar.basepageobject.BaseMobilePage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseTextBox;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.findbysImp.ParkarFindBy;

public class AmazonLoginPage extends BaseMobilePage{
	
	public static final String LOGIN_ID="//input[@id='ap_email_login']";
	
	@ParkarFindBy(webId="ap_email_login", via=ParkarLocateVia.ByElementPresent)
	IBaseTextBox LOGIN_ID_TXT_BX;
	
	@ParkarFindBy(webXpath="//form[@id='ap_login_form']//input[@id='ap_password']", via=ParkarLocateVia.ByElementPresent)
	IBaseTextBox PASSWORD_TXT_BX;
	
	@ParkarFindBy(webId="signInSubmit", via=ParkarLocateVia.ByElementClickable)
	IBaseButton SIGN_IN_BTN;
	
	public AmazonLoginPage(AppiumDriver<?> driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad(LOGIN_ID);
		// TODO Auto-generated constructor stub
	}
	
	public AmazonHomePage loginToAmazon(String userName, String password) throws ParkarCoreCommonException{
		LOGIN_ID_TXT_BX.setText(userName);
		PASSWORD_TXT_BX.setText(password);
		SIGN_IN_BTN.click();
		return new AmazonHomePage(driver);
	}

}
