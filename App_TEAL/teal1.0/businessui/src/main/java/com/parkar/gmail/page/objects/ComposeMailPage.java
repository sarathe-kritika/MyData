package com.parkar.gmail.page.objects;

import org.openqa.selenium.WebDriver;

import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseTextBox;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;

public class ComposeMailPage extends HomePage {

	private static final String NEW_MESSAGE_TXT ="//div[text()='New Message']";
	
	@ParkarFindBy(xpath = "//textarea[@name='to']", via = ParkarLocateVia.ByElementClickable)
	public IBaseTextBox toTxtBx;

	@ParkarFindBy(xpath = "//input[@name='subjectbox']", via = ParkarLocateVia.ByElementClickable)
	public IBaseTextBox subjectTxtBx;

	@ParkarFindBy(xpath = "//div[@class='Am Al editable LW-avf']", via = ParkarLocateVia.ByElementClickable)
	public IBaseTextBox bodyTxtBx;

	@ParkarFindBy(xpath = "//div[contains(text(),'Send')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton sendBtn;

	public ComposeMailPage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(NEW_MESSAGE_TXT);
	}
	public void sendEmail(String mailTo, String mailSubject, String mailBody) throws ParkarCoreUIException {
		toTxtBx.setText(mailTo);
		subjectTxtBx.setText(mailSubject);
		bodyTxtBx.setText(mailBody);
		sendBtn.click();
	}
}
