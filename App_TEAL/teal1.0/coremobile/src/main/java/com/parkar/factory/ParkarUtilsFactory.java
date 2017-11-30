package com.parkar.factory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreMobileException;
import com.parkar.logging.ParkarLogger;
import com.parkar.testng.Configurator;
import com.parkar.utils.common.FileUtils;

public class ParkarUtilsFactory {
	final static Logger logger = Logger.getLogger(ParkarUtilsFactory.class);
	private static Configurator configurator = Configurator.getInstance();
	private static final String DEVICE_PROPERTIES = "device.properties";

	/**
	 * Initializes browser. RemoteWebDriver, IE , CHROME and Firefox(default)
	 * 
	 * @param context
	 *            :ITestContext
	 * @return WebDriver : object of initialized webdriver
	 * @throws Exception
	 *             : customized exception
	 */
	public static AppiumDriver<?> initWebDriver(final ITestContext context) throws Exception {
		ParkarLogger.traceEnter();
		Map<String, String> parameterMap = context.getCurrentXmlTest().getAllParameters();
		DesiredCapabilities capability = new DesiredCapabilities();
		AppiumDriver<?>  driver = null;
		Properties prop = FileUtils.readProperties(FileUtils.getAbsolutePath(DEVICE_PROPERTIES));
		try {
			if (parameterMap.containsKey("platform")) {
				String platform = parameterMap.get("platform");
				if (StringUtils.equalsIgnoreCase(platform, "android")) {
					capability.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
			        capability.setCapability(MobileCapabilityType.DEVICE_NAME, prop.get("android.DEVICE_NAME"));
			        capability.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, prop.get("android.APP_ACTIVITY"));
			        capability.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, prop.get("android.APP_PACKAGE"));
			        capability.setCapability(AndroidMobileCapabilityType.VERSION, prop.get("android.VERSION"));
			        capability.setCapability(MobileCapabilityType.APP, prop.get("android.APP"));     
			        capability.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, prop.get("android.APP_WAIT_ACTIVITY"));
			        URL url = new URL((String)prop.get("android.URL"));
			        driver = new AndroidDriver(url, capability); 
					
				} else if (StringUtils.equalsIgnoreCase(platform, "iOS")) {
					//Add IOS details here.
				}else if(StringUtils.equalsIgnoreCase(platform, "Chrome")){
					String frontEndServer = configurator.getParameter("Frontend_Server");
					 capability.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
					 capability.setCapability(MobileCapabilityType.DEVICE_NAME, prop.get("chrome.DEVICE_NAME"));
					 capability.setCapability(MobileCapabilityType.BROWSER_NAME,prop.get("chrome.BROWSER_NAME"));
					 capability.setCapability(MobileCapabilityType.VERSION, prop.get("chrome.VERSION"));
					 capability.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, prop.get("chrome.APP_WAIT_ACTIVITY"));
				     URL url = new URL((String)prop.get("chrome.URL"));
				     logger.info("Browser parameter is Chrome");
				     driver = new AndroidDriver(url,capability);
				     driver.get(frontEndServer);
				}
			} else {
				
			}
		} catch (Exception e) {
			if (e instanceof ParkarCoreCommonException)
				throw e;
			String message = "Get Webdriver Failed:";
			logger.error(message, e);
			throw new ParkarCoreMobileException(message, e);
		}
		ParkarLogger.traceLeave();
		return driver;
	}
}
