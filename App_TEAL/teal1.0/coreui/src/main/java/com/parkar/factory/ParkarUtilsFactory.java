package com.parkar.factory;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.exception.ParkarCoreUIException;
import com.parkar.logging.ParkarLogger;
import com.parkar.testng.Configurator;

public class ParkarUtilsFactory {
	final static Logger logger = Logger.getLogger(ParkarUtilsFactory.class);
	private static Configurator configurator = Configurator.getInstance();

	/**
	 * Initializes browser. RemoteWebDriver, IE , CHROME and Firefox(default)
	 * 
	 * @param context
	 *            :ITestContext
	 * @return WebDriver : object of initialized webdriver
	 * @throws Exception : customized exception
	 */
	public static WebDriver initWebDriver(final ITestContext context) throws Exception {
		ParkarLogger.traceEnter();
		Map<String, String> parameterMap = context.getCurrentXmlTest().getAllParameters();
		DesiredCapabilities capability = new DesiredCapabilities();
		WebDriver driver = null;
		try {
			if (parameterMap.containsKey("browser")) {
				String browser = parameterMap.get("browser");
				if (StringUtils.equalsIgnoreCase(browser, "chrome")) {
					System.setProperty("webdriver.chrome.driver", configurator.getDirectoryParameter("chrome_driver"));
					capability = DesiredCapabilities.chrome();
					logger.info("Browser parameter is Chrome");
					driver= new ChromeDriver();
				} else if (StringUtils.equalsIgnoreCase(browser, "ie")) {
					//disable IE pop-up blocker
					String cmd = "REG ADD \"HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\New Windows\" /F /V \"PopupMgr\" /T REG_DWORD /D 0";
					try {
						Runtime.getRuntime().exec(cmd);
					} catch (Exception e) {
						String message = "Set registry for IE failed, cmd is: " + cmd;
						logger.error(message , e);
						throw new ParkarCoreUIException(message, e);
					}
					System.setProperty("webdriver.ie.driver", configurator.getDirectoryParameter("ie_driver"));
					capability = DesiredCapabilities.internetExplorer();
					capability.setCapability("ignoreZoomSetting", true);
					capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
					driver= new InternetExplorerDriver(capability);
				} else if (StringUtils.equalsIgnoreCase(browser, "firefox")) {
					logger.info("Browser parameter is Firefox");
					capability = DesiredCapabilities.firefox();
					driver = new FirefoxDriver();
				}else{
					logger.info("Browser parameter invalid so default browser Firefox will be used");
					capability = DesiredCapabilities.firefox();
					driver = new FirefoxDriver();
				}
			} else {
				logger.info("Cannot find browser parameter, firefox will be used as default browser");
				capability = DesiredCapabilities.firefox();
				driver = new FirefoxDriver();
			}
			/*String remoteWebDriverIp =parameterMap.containsKey("remoteDriverIP") ? parameterMap.get("remoteDriverIP") : "localhost";
			String remoteWebDriverPort =parameterMap.containsKey("remoteDriverPort") ? parameterMap.get("remoteDriverPort") : "4444";
			String url = "http://" + remoteWebDriverIp + ":" + remoteWebDriverPort + "/wd/hub";
			driver = new RemoteWebDriver(new URL(url), capability);*/
		} catch (Exception e) {
			if (e instanceof ParkarCoreCommonException)
				throw e;
			String message = "Get Webdriver Failed :";
			logger.error(message, e);
			throw new ParkarCoreUIException(message, e);
		}
		ParkarLogger.traceLeave();
		return driver;
	}
}
