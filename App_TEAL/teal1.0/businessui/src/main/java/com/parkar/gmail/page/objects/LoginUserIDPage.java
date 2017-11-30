package com.parkar.gmail.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseTextBox;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class LoginUserIDPage extends BaseUIPage {

	private static final String GOOGLE_LOGO = "//div[@aria-label='Google']";

	@ParkarFindBy(name = "Email", via = ParkarLocateVia.ByElementClickable)
	public IBaseTextBox emailTxtBx;

	@ParkarFindBy(name = "signIn", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton nextBtn;

	public LoginUserIDPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}

	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(GOOGLE_LOGO);
	}

	public LoginPasswordPage loginAsUser(String name) throws ParkarCoreCommonException {
		emailTxtBx.setText(name);
		nextBtn.click();
		return new LoginPasswordPage(driver);
	}

}
