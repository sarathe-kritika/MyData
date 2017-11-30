package com.parkar.findbysImp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreMobileException;
import com.parkar.utils.CommandPrompt;
import com.parkar.utils.common.FileUtils;

public class ParkarFindByFactory {
	private final static Logger LOGGER = Logger.getLogger(ParkarFindByFactory.class);
	private static final String WEB_VIEW = "CHROMIUM";
	private static CommandPrompt commandPrompt;
	private static Properties prop;
	private static final String APPIUM_FIND_BY_FILE = "appiumFindBy.properties";
	private static final String WEB_FIND_BY = "com.parkar.findbysImp.ParkarWebFindBy";
	private static final String ANDROID_FIND_BY = "com.parkar.findbysImp.ParkarAndroidFindBy";
	private static final String IOS_FIND_BY = "com.parkar.findbysImp.ParkarIOSFindBy";
	private static final String SELENDROID_FIND_BY = "com.parkar.findbysImp.ParkarSelendroidFindBy";
	static {
		commandPrompt = new CommandPrompt();
	}

	/**
	 * Get By for element
	 * 
	 * @param driver
	 * @param parkarFindBy
	 * @return
	 * @throws ParkarCoreMobileException
	 */
	public static By getByType(AppiumDriver<?> driver, ParkarFindBy parkarFindBy){
		String className = getClassName(driver);
		IParkarFindBy iParkarFindBy = null;
		if (StringUtils.isNotBlank(className)) {
			try {
				iParkarFindBy = (IParkarFindBy) Class.forName(className).newInstance();
				return iParkarFindBy.buildFindBy(parkarFindBy);
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				String msg = "Failed to instantiate IParkarFindBy object";
				LOGGER.error(msg, e);
			}
		}
		return null;
	}

	/**
	 * Get className FindBy from driver instance
	 * 
	 * @param driver
	 * @return String: className
	 */
	private static String getClassName(AppiumDriver<?> driver) {
		if (StringUtils.containsIgnoreCase(driver.getContext(), WEB_VIEW)) {
			return WEB_FIND_BY;
		}
		//This if block will be removed once selendroid is deprecated
		if (driver instanceof AndroidDriver) {
			String deviceName = (String) driver.getCapabilities().getCapability("deviceName");
			commandPrompt = new CommandPrompt();
			if (commandPrompt.checkSelendroidMode(deviceName)) {
				return SELENDROID_FIND_BY;
			} else {
				return ANDROID_FIND_BY;
			}
		}
		if(driver instanceof IOSDriver){
			return IOS_FIND_BY;
		}
		try {
			prop = FileUtils.readProperties(FileUtils.getAbsolutePath(APPIUM_FIND_BY_FILE));
		} catch (ParkarCoreCommonException e) {
			LOGGER.error("Failed to read appiumDevice.properties file", e);
		}
		for (String key : prop.stringPropertyNames()) {
			if (StringUtils.containsIgnoreCase(key, driver.toString())) {
				return prop.getProperty(key);
			}
		}
		return null;
	}
}
