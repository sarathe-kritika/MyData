package com.parkar.gmail.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseTextBox;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class LoginPasswordPage extends BaseUIPage {
	
	private static final String SIGN_IN_BTN = "//input[@id='signIn']";

	@ParkarFindBy(name = "Passwd", via = ParkarLocateVia.ByElementClickable)
	public IBaseTextBox passwordTxtBx;

	@ParkarFindBy(id = "signIn", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton signInBtn;
	
	public LoginPasswordPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(SIGN_IN_BTN);
	}
	
	public HomePage setPassword(String password) throws ParkarCoreCommonException {
		passwordTxtBx.setText(password);
		signInBtn.click();
		return new HomePage(driver);
	}

}
