package com.parkar.basepageobject;

import io.appium.java_client.AppiumDriver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.parkar.element.BaseElement;
import com.parkar.enums.LocatorType;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreMobileException;
import com.parkar.factory.ParkarBasePageFactory;
import com.parkar.helpers.BasicPageElementHelper;
import com.parkar.helpers.BasicPageSyncHelper;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.IReporter;
import com.parkar.report.ParkarReporter;
import com.parkar.testng.Configurator;
import com.parkar.utils.ParkarLocatorLoaderUtil;
import com.paulhammant.ngwebdriver.WaitForAngularRequestsToFinish;

/**
 *
 */
public abstract class BaseMobilePage {
	public AppiumDriver<?> driver;
	protected HashMap<String, Properties> locatorMap;
	protected List<String> pageHierarchyList;
	final static Logger logger = Logger.getLogger(BaseMobilePage.class);
	protected IReporter reporter = ParkarReporter.getInstance();
	private static final int timeout = Integer.parseInt(Configurator.getInstance().getParameter("timeout"));
	// for logging purpose
	public String navigation;
	/**
	 * Initializes page with the webDriver 
	 * 
	 * @param driver:WebDriver
	 * @throws ParkarCoreCommonException throws a ParkarCoreCommonException
	 */
	public BaseMobilePage(AppiumDriver<?> driver) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		try {
			this.driver = driver;
			// for logging purpose
			navigation = getNavigation(this.getClass(), "");
			ParkarBasePageFactory.initPage(driver, this);
		} catch (Exception e) {
			if (e instanceof ParkarCoreCommonException)
				throw e;
			String errorMsg = "init Parkar page object failed with an exception: ";
			logger.error(errorMsg, e);
			throw new ParkarCoreMobileException(errorMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Initialize Element
	 * 
	 * @param locator:String
	 * @param methodName:ParkarLocateVia
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 * @return BaseElement
	 */
	public BaseElement initElement(String locator, ParkarLocateVia methodName) throws ParkarCoreMobileException{

		ParkarLogger.traceEnter();
		BaseElement baseElement = null;
		try {
			int timeout = Integer.parseInt(Configurator.getInstance().getParameter("timeout"));
			Method method = BasicPageElementHelper.class.getDeclaredMethod(methodName.getMethodName(),
					new Class[] { AppiumDriver.class, By.class, int.class });
			baseElement = new BaseElement(driver,
					(WebElement) method.invoke(null, driver, determineLocatorType(locator), timeout), locator,
					locator, navigation);
		} catch (Exception e) {
			String errorMsg = "init element failed witn an exception: ";
			logger.error(errorMsg, e);
			throw new ParkarCoreMobileException(errorMsg, e);
		}
		ParkarLogger.traceLeave();
		return baseElement;
	}
	
	/**
	 * Initialize Element with timeout
	 * 
	 * @param locator:String
	 * @param methodName:ParkarLocateVia
	 * @param timeout:int
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 * @return BaseElement
	 */
	public BaseElement initElement(String locator, ParkarLocateVia methodName, int timeout) throws ParkarCoreMobileException{

		ParkarLogger.traceEnter();
		BaseElement baseElement = null;
		try {
			Method method = BasicPageElementHelper.class.getDeclaredMethod(methodName.getMethodName(),
					new Class[] { AppiumDriver.class, By.class, int.class });
			baseElement = new BaseElement(driver,
					(WebElement) method.invoke(null, driver, determineLocatorType(locator), timeout), locator,
					locator, navigation);
		} catch (Exception e) {
			String errorMsg = "init element failed witn an exception: ";
			logger.error(errorMsg, e);
			throw new ParkarCoreMobileException(errorMsg, e);
		}
		ParkarLogger.traceLeave();
		return baseElement;
	}
	
	/**
	 * Initialize Element
	 * 
	 * @param locator:String
	 * @param type:LocatorType
	 * @param methodName:ParkarLocateVia
	 * @return BaseElement
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public BaseElement initElement(String locator, LocatorType type, ParkarLocateVia methodName)
			throws ParkarCoreMobileException {

		ParkarLogger.traceEnter();
		BaseElement baseElement = null;
		try {
			int timeout = Integer.parseInt(Configurator.getInstance().getParameter("timeout"));
			Method method = BasicPageElementHelper.class.getDeclaredMethod(methodName.getMethodName(),
					new Class[] { AppiumDriver.class, By.class, int.class });
			baseElement = new BaseElement(driver,
					(WebElement) method.invoke(null, driver, determineLocatorType(locator, type), timeout), locator,
					locator, navigation);
		} catch (Exception e) {
			String errorMsg = "initElement element failed with an exception: ";
			logger.error(errorMsg, e);
			throw new ParkarCoreMobileException(errorMsg, e);
		}

		return baseElement;
	}

	/**
	 * Initialize Element
	 * 
	 * @param locator:String
	 * @param type:LocatorType
	 * @param methodName:ParkarLocateVia
	 * @param timeout:int
	 * @return BaseElement
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public BaseElement initElement(String locator, LocatorType type, ParkarLocateVia methodName, int timeout)
			throws ParkarCoreMobileException {

		ParkarLogger.traceEnter();
		BaseElement baseElement = null;
		try {
			Method method = BasicPageElementHelper.class.getDeclaredMethod(methodName.getMethodName(),
					new Class[] { AppiumDriver.class, By.class, int.class });
			baseElement = new BaseElement(driver,
					(WebElement) method.invoke(null, driver, determineLocatorType(locator, type), timeout), locator,
					locator, navigation);
		} catch (Exception e) {
			String errorMsg = "initElement element failed with an exception: ";
			logger.error(errorMsg, e);
			throw new ParkarCoreMobileException(errorMsg, e);
		}

		return baseElement;
	}
	

	
	/**
	 * Initialize Elements
	 * 
	 * @param locator:String
	 * @param methodName:ParkarLocateVia
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 * @return BaseElement
	 */
	@SuppressWarnings("unchecked")
	public List<BaseElement> initElements(String locator, ParkarLocateVia methodName) throws ParkarCoreMobileException{
		ParkarLogger.traceEnter();
		List<BaseElement> baseElement = new ArrayList<BaseElement>();
		if(!methodName.getMethodName().equals("findElementsByPresence")){
			throw new ParkarCoreMobileException("This method does not support the given method " + methodName.getMethodName());
		}
		try {
			int timeout = Integer.parseInt(Configurator.getInstance().getParameter("timeout"));
			Method method = BasicPageElementHelper.class.getDeclaredMethod(methodName.getMethodName(),
					new Class[] { AppiumDriver.class, By.class, int.class });
			List<WebElement> temp = (List<WebElement>)method.invoke(null, driver, determineLocatorType(locator), timeout);
			for(WebElement e:temp){
				baseElement.add(new BaseElement(driver, e, locator, locator, navigation));
			}
		} catch (Exception e) {
			String errorMsg = "init elements failed witn an exception: ";
			logger.error(errorMsg, e);
			throw new ParkarCoreMobileException(errorMsg, e);
		}
		ParkarLogger.traceLeave();
		return baseElement;
	}
	
	/**
	 * Initialize Elements with timeout
	 * 
	 * @param locator:String
	 * @param methodName:ParkarLocateVia
	 * @param timeout:int
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 * @return BaseElement
	 */
	@SuppressWarnings("unchecked")
	public List<BaseElement> initElements(String locator, ParkarLocateVia methodName, int timeout) throws ParkarCoreMobileException{
		ParkarLogger.traceEnter();
		List<BaseElement> baseElement = new ArrayList<BaseElement>();
		if(!methodName.getMethodName().equals("findElementsByPresence")){
			throw new ParkarCoreMobileException("This method does not support the given method " + methodName.getMethodName());
		}
		
		try {
			Method method = BasicPageElementHelper.class.getDeclaredMethod(methodName.getMethodName(),
					new Class[] { AppiumDriver.class, By.class, int.class });
			List<WebElement> temp = (List<WebElement>)method.invoke(null, driver, determineLocatorType(locator), timeout);
			for(WebElement e:temp){
				baseElement.add(new BaseElement(driver, e, locator, locator, navigation));
			}
		} catch (Exception e) {
			String errorMsg = "init elements failed witn an exception: ";
			logger.error(errorMsg, e);
			throw new ParkarCoreMobileException(errorMsg, e);
		}
		ParkarLogger.traceLeave();
		return baseElement;
	}
	
	/**
	 * Initialize Elements
	 * 
	 * @param locator:String
	 * @param type:LocatorType
	 * @param methodName:ParkarLocateVia
	 * @return BaseElement
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	@SuppressWarnings("unchecked")
	public List<BaseElement> initElements(String locator, LocatorType type, ParkarLocateVia methodName)
			throws ParkarCoreMobileException {

		ParkarLogger.traceEnter();
		List<BaseElement> baseElement = new ArrayList<BaseElement>();
		if(!methodName.getMethodName().equals("findElementsByPresence")){
			throw new ParkarCoreMobileException("This method does not support the given method " + methodName.getMethodName());
		}
		try {
			int timeout = Integer.parseInt(Configurator.getInstance().getParameter("timeout"));
			Method method = BasicPageElementHelper.class.getDeclaredMethod(methodName.getMethodName(),
					new Class[] { AppiumDriver.class, By.class, int.class });
			List<WebElement> temp = (List<WebElement>) method.invoke(null, driver, determineLocatorType(locator), timeout);
			for(WebElement e:temp){
				baseElement.add(new BaseElement(driver, e, locator, locator, navigation));
			}
		} catch (Exception e) {
			String errorMsg = "initElement elements failed with an exception: ";
			logger.error(errorMsg, e);
			throw new ParkarCoreMobileException(errorMsg, e);
		}

		return baseElement;
	}

	/**
	 * Initialize Elements
	 * 
	 * @param locator:String
	 * @param type:LocatorType
	 * @param methodName:ParkarLocateVia
	 * @param timeout:int
	 * @return BaseElement
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	@SuppressWarnings("unchecked")
	public List<BaseElement> initElements(String locator, LocatorType type, ParkarLocateVia methodName, int timeout)
			throws ParkarCoreMobileException {

		ParkarLogger.traceEnter();
		List<BaseElement> baseElement = new ArrayList<BaseElement>();
		if(!methodName.getMethodName().equals("findElementsByPresence")){
			throw new ParkarCoreMobileException("This method does not support the given method " + methodName.getMethodName());
		}
		try {
			Method method = BasicPageElementHelper.class.getDeclaredMethod(methodName.getMethodName(),
					new Class[] { AppiumDriver.class, By.class, int.class });
			List<WebElement> temp = (List<WebElement>) method.invoke(null, driver, determineLocatorType(locator), timeout);
			for(WebElement e:temp){
				baseElement.add(new BaseElement(driver, e, locator, locator, navigation));
			}
		} catch (Exception e) {
			String errorMsg = "initElement elements failed with an exception: ";
			logger.error(errorMsg, e);
			throw new ParkarCoreMobileException(errorMsg, e);
		}

		return baseElement;
	}
	/**
	 * This public method for usage tracking
	 * 
	 * @param c:Class
	 * @param results:String
	 * @return String
	 */
	public String getNavigation(Class<?> c, String results) {
		if (c.getSuperclass() == null) {
			return "";
		}
		if (c.getSuperclass().getSimpleName().endsWith("BasePage")) {
			return c.getSimpleName();
		}
		results += getNavigation(c.getSuperclass(), results) + " -> " + c.getSimpleName();
		return results;
	}

	/**
	 * Go to new URL
	 * 
	 * @param url:String
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public void goToPage(String url) throws ParkarCoreMobileException {
		driver.get(url);
		waitForPageToLoad();
	}

	public AppiumDriver<?> getDriver() {
		return driver;
	}

	public String getNavigation() {
		return navigation;
	}

	/**
	 * To a new frame.
	 * 
	 * @param frame:String
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public void switchToFrame(String frame) throws ParkarCoreMobileException {
		BasicPageSyncHelper.waitForFrameToLoadAndSwitch(driver, frame);

	}

	/**
	 * Switch to Default content.
	 */
	public void switchToDefault() {
		driver.switchTo().defaultContent();
	}

	/**
	 * Wait on the page to be loaded based on the HTML Document state to set to
	 * be complete. Sometime this approach is not enough when Ajax calls are
	 * involved.
	 */
	public void waitForAngularRequestsToFinish() {
		WaitForAngularRequestsToFinish.waitForAngularRequestsToFinish((JavascriptExecutor) driver);
	}
	
	/**
	 * Wait until the visibility of given element locator is located on the page.
	 * @param locator:String
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public void waitForApplicationToLoad(String locator) throws ParkarCoreMobileException {
		BasicPageSyncHelper.waitForApplicationToLoad(driver, locator,timeout);
	}
	
	/**
	 * Wait until the the given locator element goes invisible on the page.
	 * @param locator:String
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public void waitForContentToLoad(String locator) throws ParkarCoreMobileException {
		BasicPageSyncHelper.waitForContentToLoad(driver, locator,timeout);
	}
	
	/**
	 * Wait until the visibility of given element locator is located on the page.
	 * @param locator:String
 	 * @param timeout:int
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public void waitForApplicationToLoad(String locator, int timeout) throws ParkarCoreMobileException {
		BasicPageSyncHelper.waitForApplicationToLoad(driver, locator,timeout);
	}
	
	/**
	 * Wait until the the given locator element goes invisible on the page.
	 * @param locator:String
	 * @param timeout:int
	 * @throws ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public void waitForContentToLoad(String locator, int timeout) throws ParkarCoreMobileException {
		BasicPageSyncHelper.waitForContentToLoad(driver, locator,timeout);
	}
	
	/**
	 * Wait on the page to be loaded based on the HTML Document state to set to
	 * be complete. Sometime this approach is not enough when Ajax calls are
	 * involved.
	 * 
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	public void waitForPageToLoad() throws ParkarCoreMobileException {
		BasicPageSyncHelper.waitForPageToLoad(driver);
	}
	
	/**
	 * Get By object.
	 * 
	 * @param locator:String
	 * @return By
	 */
	public By determineLocatorType(String locator) {
		return ParkarLocatorLoaderUtil.determineByType(locator);
	}
	/**
	 * Get By object.
	 * 
	 * @param locator:String
	 * @param type:LocatorType
	 * @return By
	 */
	public By determineLocatorType(String locator, LocatorType type) {
		return ParkarLocatorLoaderUtil.determineByType(locator, type);
	}
	
}
