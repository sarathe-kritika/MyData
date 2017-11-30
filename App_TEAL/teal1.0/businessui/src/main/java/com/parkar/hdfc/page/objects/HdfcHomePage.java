package com.parkar.hdfc.page.objects;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseButton;
import com.parkar.element.interfaces.IBaseLink;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarFindBy;
import com.parkar.helpers.BasicBrowserHelper;

public class HdfcHomePage extends BaseUIPage {
	
	String patersonMap="resources\\images\\customer_Care.png"; 
	
	static String winHandleHome = null;
	private static final String HDFC_LOGO = "//img[@alt='HDFC Bank logo']";
	
	@ParkarFindBy(id = "loginsubmit", via = ParkarLocateVia.ByElementClickable)
	public IBaseButton loginBtn;
	
	@ParkarFindBy(xpath = "//a[contains(text(),'Customer Care')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink customerCareLnk;
	
	@ParkarFindBy(xpath = "//a[contains(text(),'Queries, Feedback or Complaints')]", via = ParkarLocateVia.ByElementClickable)
	public IBaseLink queriesLnk;
	
	@ParkarFindBy(xpath = "//div[@id='cee_closeBtn']/img", via = ParkarLocateVia.ByElementClickable, timeout=20)
	public IBaseLink hatTrickLnk;

	public HdfcHomePage(WebDriver driver) throws ParkarCoreCommonException {
		super(driver);
		waitForApplicationToLoad();
	}
	
	private void waitForApplicationToLoad() throws ParkarCoreUIException {
		waitForPageToLoad();
		waitForApplicationToLoad(HDFC_LOGO);
	}
	
	public HdfcContinueNetbankingPage goToContinueNetbankingPage() throws ParkarCoreCommonException, InterruptedException {
		Thread.sleep(3000);
		  try {
		   hatTrickLnk.click();
		  } catch (Exception e) {
		  }
		loginBtn.click();
		winHandleHome = driver.getWindowHandle();
		BasicBrowserHelper.switchingToNewWindowOpened(driver, winHandleHome);
		return new HdfcContinueNetbankingPage(driver);
	}
	
	public HdfcCustomerCarePage goToCustomerCarePage() throws ParkarCoreCommonException, FindFailed{
		Pattern location = new Pattern(patersonMap);
		Screen src = new Screen();
		src.setAutoWaitTimeout(50);
		src.find(location).highlight(3).hover();
		//customerCareLnk.hover();
		
		queriesLnk.click();
		BasicBrowserHelper.switchingToNewWindowOpened(driver, winHandleHome);
	    return new HdfcCustomerCarePage(driver);
	}

}
