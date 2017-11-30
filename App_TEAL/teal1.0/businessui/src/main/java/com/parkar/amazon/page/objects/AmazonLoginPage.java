package com.parkar.amazon.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseTextBox;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class AmazonLoginPage extends BaseUIPage{
	
	private static final String LOGIN_TXT = "//h1[@class='a-spacing-small']";
	
	@ParkarFindBy(id = "ap_email", via = ParkarLocateVia.ByElementClickable)
	public IBaseTextBox userNameTxtBx;

	@ParkarFindBy(id = "ap_password", via = ParkarLocateVia.ByElementClickable)
	public IBaseTextBox passwordTxtBx;

	@ParkarFindBy(id = "signInSubmit", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton loginBtn;

	public AmazonLoginPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
		
	}
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(LOGIN_TXT);
	}

	public AmazonHomePage login(String username , String password) throws ParkarCoreCommonException {
		userNameTxtBx.setText(username);
		passwordTxtBx.setText(password);
		loginBtn.click();
		return new AmazonHomePage(driver);
	}
	

	

}
