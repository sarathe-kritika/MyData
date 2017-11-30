package com.parkar.factory;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.findbysImp.ParkarBaseElementDecorator;
import com.parkar.logging.ParkarLogger;
import com.parkar.testng.Configurator;

public class ParkarBasePageFactory extends PageFactory {

	final static Logger logger = Logger.getLogger(ParkarBasePageFactory.class);
	/**
	 * Initializes page
	 * 
	 * @param driver:WebDriver
	 * @param seleniumPageObject:BasePage
	 * @throws ParkarCoreCommonException throws a ParkarCoreCommonException
	 */
	public static void initPage(WebDriver driver, BaseUIPage seleniumPageObject) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		ParkarBaseElementDecorator decorator;
		try{
			decorator = new ParkarBaseElementDecorator(
					new ParkarDefaultElementLocatorFactory(driver, Integer.parseInt(Configurator.getInstance().getParameter("timeout"))), 
					seleniumPageObject);
		} catch (Exception e1) {
			if (e1 instanceof ParkarCoreCommonException)
				throw e1;
			String message1 = "Page initilization failed :";
			logger.error(message1 , e1);
			throw new ParkarCoreUIException(message1, e1);
		}
	    PageFactory.initElements(decorator, seleniumPageObject);
		logger.info("Page initilization is OK.");
		ParkarLogger.traceLeave();
	}
}
