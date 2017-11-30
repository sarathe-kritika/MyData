package com.parkar.gmail.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseCheckBox;
import com.parkar.element.interfaces.IBaseLabel;
import com.parkar.element.interfaces.IBaseTextBox;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.enums.LocatorType;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class HomePage extends BaseUIPage {

	private static final String GMAIL_LOGO = "//span[@class='gb_Rb']";

	@ParkarFindBy(xpath = "//div[contains(text(),'COMPOSE')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton composeBtn;
	
	@ParkarFindBy(xpath = "//a[@class='gb_b gb_8a gb_R']", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton userLogoLnk;
	
	@ParkarFindBy(xpath = "//input[@id='gbqfq']", via = ParkarLocateVia.ByElementClickable)
	public IBaseTextBox searchEmailTxtBx;
	
	@ParkarFindBy(xpath = "//button[@id='gbqfb']", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton searchEmailBtn;
		
	@ParkarFindBy(xpath = "//a[contains(text(),'Sign out')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton logOutBtn;
	
	@ParkarFindBy(xpath = "//a[contains(text(),'Inbox')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton inboxBtn;

	public HomePage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}

	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(GMAIL_LOGO);
	}

	public ComposeMailPage goToComposeMailPage() throws ParkarCoreCommonException {
		composeBtn.click();
		return new ComposeMailPage(driver);
	}

	public LoginPasswordPage logOut() throws ParkarCoreCommonException {
		userLogoLnk.click();
		logOutBtn.click();
		return new LoginPasswordPage(driver);
	}

	public DashboardPage searchEmail(String data) throws ParkarCoreCommonException {
		searchEmailTxtBx.setText(data);
		searchEmailBtn.click();
		return new DashboardPage(driver);
	}
	
	public IBaseLabel getEmailSubject(String partialLocator) throws ParkarCoreUIException {
		String locator = "//b[contains(text(),'" + partialLocator + "')]";
		return initElement(locator, LocatorType.XPATH, ParkarLocateVia.ByElementPresent);
	}
	
	public IBaseCheckBox getSelectAllEmailCheckBox(String partialLocator) throws ParkarCoreUIException {
		String locator = "//div[@class='D E G-atb'][" + partialLocator + "]//div[@class='T-Jo-auh']";
		return initElement(locator, LocatorType.XPATH, ParkarLocateVia.ByElementClickable);
	}
	
}