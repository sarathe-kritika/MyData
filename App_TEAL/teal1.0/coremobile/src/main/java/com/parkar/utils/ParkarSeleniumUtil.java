package com.parkar.utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;

/**
 * 
 * Do not add logs inside this class.
 *
 */
public class ParkarSeleniumUtil {

	/**
	 * Wait for element to be enable attributes
	 * 
	 * @param driver WebDriver
	 * @param elt WebElement
	 * @param timeout int
	 */
	public static void waitForElementToBeEnabled(AppiumDriver<?> driver,
			WebElement elt, int timeout) {
		new WebDriverWait(driver, timeout).until(enableOfElement(elt));
	}

	/**
	 * Wait for element to be disable attributes
	 * 
	 * @param driver WebDriver
	 * @param elt WebElement
	 * @param timeout int
	 */
	public static void waitForElementToBeDisabled(AppiumDriver<?> driver,
			WebElement elt, int timeout) {
		new WebDriverWait(driver, timeout).until(disableOfElement(elt));
	}

	/**
	 * Wait for element to be displayed attributes
	 * 
	 * @param driver WebDriver
	 * @param elt WebElement
	 * @param timeout int
	 */
	public static void waitForElementToBeDisplayed(AppiumDriver<?> driver,
			WebElement elt, int timeout) {
		new WebDriverWait(driver, timeout).until(displayOfElement(elt));
	}

	/**
	 * Wait for element to be disappear attributes
	 * 
	 * @param driver WebDriver
	 * @param elt WebElement
	 * @param timeout int
	 */
	public static void waitForElementToBeDisappear(AppiumDriver<?> driver,
			WebElement elt, int timeout) {
		new WebDriverWait(driver, timeout).until(disappearOfElement(elt));
	}

	/**
	 * An expectation for checking an element is displayed.
	 * 
	 * @param elt WebElement
	 * @return
	 */
	private static ExpectedCondition<Boolean> displayOfElement(
			final WebElement elt) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return elt.isDisplayed();
				} catch (NoSuchElementException e) {
					return false;
				} catch (StaleElementReferenceException e) {
					return false;
				}
			}

			@Override
			public String toString() {
				return String.format("element containing '%s' to be enabled:",
						elt.toString());
			}
		};
	}

	/**
	 * An expectation for checking an element is not displayed.
	 * 
	 * @param elt WebElement
	 * @return
	 */
	private static ExpectedCondition<Boolean> disappearOfElement(
			final WebElement elt) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return !elt.isDisplayed();
				} catch (NoSuchElementException e) {
					return true;
				} catch (StaleElementReferenceException e) {
					return true;
				}
			}

			@Override
			public String toString() {
				return String.format("element containing '%s' to be enabled:",
						elt.toString());
			}
		};
	}

	/**
	 * An expectation for checking an element is disabled.
	 * 
	 * @param elt WebElement
	 * @return
	 */
	private static ExpectedCondition<Boolean> enableOfElement(
			final WebElement elt) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return elt.isEnabled();
				} catch (NoSuchElementException e) {
					return false;
				} catch (StaleElementReferenceException e) {
					return false;
				}
			}

			@Override
			public String toString() {
				return String.format("element containing '%s' to be enabled:",
						elt.toString());
			}
		};
	}

	/**
	 * An expectation for checking an element is disabled.
	 * 
	 * @param elt WebElement
	 * @return
	 */
	private static ExpectedCondition<Boolean> disableOfElement(
			final WebElement elt) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return !elt.isEnabled();
				} catch (NoSuchElementException e) {
					return true;
				} catch (StaleElementReferenceException e) {
					return true;
				}
			}

			@Override
			public String toString() {
				return String.format(
						"element containing '%s' to be disabled: ",
						elt.toString());
			}
		};
	}

	public static File takeScreenShot(AppiumDriver<?> driver) {
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		return ((TakesScreenshot) augmentedDriver)
				.getScreenshotAs(OutputType.FILE);
	}

	/**
	 * Wait for page to be loaded based on the HTML document.readyState
	 * attributes
	 * 
	 * @param driver
	 *            WebDriver
	 */
	public static void waitForDocumentStateToBeComplete(AppiumDriver<?> driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		new WebDriverWait(driver, 60).until(pageLoadCondition);
	}

	/**
	 * Scroll the element into view before do anything
	 * 
	 * @param driver
	 *            WebDriver
	 * @param elt
	 *            WebElement
	 */
	public static void scrollIntoView(AppiumDriver<?> driver, WebElement elt) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", elt);
	}

	/**
	 * Customized click to click the element using java script. Usually we use
	 * this method to trigger elements generated by AngularJS that could not
	 * recognized properly by Selenium driver
	 * 
	 * @param driver
	 *            WebDriver
	 * @param elt
	 *            WebElement
	 */
	public static void clickOnInvisible(AppiumDriver<?> driver, WebElement elt) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				elt);
	}

	/**
	 * Find frame until it is present in the DOM
	 * 
	 * @param driver
	 *            WebDriver
	 * @param locator
	 *            By
	 * @param timeout
	 *            int
	 * @throws NoSuchElementException
	 *             throw a NoSuchElementException
	 * @throws InvocationTargetException
	 *             throw a InvocationTargetException
	 * @throws IllegalArgumentException
	 *             throw a IllegalArgumentException
	 * @throws IllegalAccessException
	 *             throw a IllegalAccessException
	 * @throws SecurityException
	 *             throw a SecurityException
	 * @throws NoSuchMethodException
	 *             throw a NoSuchMethodException
	 */
	public static void frameToBeAvailableAndSwitchToIt(AppiumDriver<?> driver,
			By locator, int timeout) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		(new WebDriverWait(driver, timeout))
				.until(invokeElementExpectedCondition(locator,
						"frameToBeAvailableAndSwitchToIt"));
	}

	/**
	 * Find element until it is Clickable
	 * 
	 * @param driver
	 *            WebDriver
	 * @param locator
	 *            By
	 * @param timeout
	 *            int
	 * @throws NoSuchElementException
	 *             throw a NoSuchElementException
	 * @throws InvocationTargetException
	 *             throw a InvocationTargetException
	 * @throws IllegalArgumentException
	 *             throw a IllegalArgumentException
	 * @throws IllegalAccessException
	 *             throw a IllegalAccessException
	 * @throws SecurityException
	 *             throw a SecurityException
	 * @throws NoSuchMethodException
	 *             throw a NoSuchMethodException
	 * @return WebElement
	 */
	public static WebElement elementToBeClickable(AppiumDriver<?> driver, By locator,
			int timeout) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return (new WebDriverWait(driver, timeout))
				.until(invokeElementExpectedCondition(locator,
						"elementToBeClickable"));
	}

	/**
	 * Find element until it is present in the DOM
	 * 
	 * @param driver
	 *            WebDriver
	 * @param locator
	 *            By
	 * @param timeout
	 *            int
	 * @throws InvocationTargetException
	 *             throw a InvocationTargetException
	 * @throws IllegalArgumentException
	 *             throw a IllegalArgumentException
	 * @throws IllegalAccessException
	 *             throw a IllegalAccessException
	 * @throws SecurityException
	 *             throw a SecurityException
	 * @throws NoSuchMethodException
	 *             throw a NoSuchMethodException
	 * @return WebElement
	 */
	public static WebElement presenceOfElementLocated(AppiumDriver<?> driver,
			By locator, int timeout) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return (new WebDriverWait(driver, timeout))
				.until(invokeElementExpectedCondition(locator,
						"presenceOfElementLocated"));
	}

	/**
	 * Find element until it is visible
	 * 
	 * @param driver
	 *            WebDriver
	 * @param locator
	 *            By
	 * @param timeout
	 *            int
	 * @throws InvocationTargetException
	 *             throw a InvocationTargetException
	 * @throws IllegalArgumentException
	 *             throw a IllegalArgumentException
	 * @throws IllegalAccessException
	 *             throw a IllegalAccessException
	 * @throws SecurityException
	 *             throw a SecurityException
	 * @throws NoSuchMethodException
	 *             throw a NoSuchMethodException
	 * @return WebElement
	 */
	public static WebElement visibilityOfElementLocated(AppiumDriver<?> driver,
			By locator, int timeout) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return (new WebDriverWait(driver, timeout))
				.until(invokeElementExpectedCondition(locator,
						"visibilityOfElementLocated"));
	}

	/**
	 * Return true if the element is invisible in the given timeout and return
	 * false otherwise
	 * 
	 * @param driver
	 *            WebDriver
	 * @param locator
	 *            By
	 * @param timeout
	 *            int
	 * @return True or False
	 */
	public static Boolean waitUntilElementIsInvisible(AppiumDriver<?> driver,
			By locator, int timeout) {
		return (new WebDriverWait(driver, timeout))
				.until(invokeElementExpectedConditionByInvisiblility(locator));
	}

	/**
	 * Return true if the element is invisible in the given timeout and return
	 * false otherwise
	 * 
	 * @param driver
	 *            WebDriver
	 * @param locator
	 *            By
	 * @param timeout
	 *            int
	 * @return List of WebElements
	 * @throws InvocationTargetException
	 *             throw a InvocationTargetException
	 * @throws IllegalArgumentException
	 *             throw a IllegalArgumentException
	 * @throws IllegalAccessException
	 *             throw a IllegalAccessException
	 * @throws SecurityException
	 *             throw a SecurityException
	 * @throws NoSuchMethodException
	 *             throw a NoSuchMethodException
	 */
	public static List<WebElement> presenceOfAllElementsLocatedBy(
			AppiumDriver<?> driver, By locator, int timeout)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return (new WebDriverWait(driver, timeout))
				.until(invokeElementsExpectedCondition(locator,
						"presenceOfAllElementsLocatedBy"));
	}

	/**
	 * Return true if the element is invisible in the given timeout and return
	 * false otherwise
	 * 
	 * @param driver
	 *            WebDriver
	 * @param locator
	 *            By
	 * @param timeout
	 *            int
	 * @return List of WebElements
	 * @throws NoSuchMethodException
	 *             throw a NoSuchMethodException
	 * @throws SecurityException
	 *             throw a SecurityException
	 * @throws IllegalAccessException
	 *             throw a IllegalAccessException
	 * @throws IllegalArgumentException
	 *             throw a IllegalArgumentException
	 * @throws InvocationTargetException
	 *             throw a InvocationTargetException
	 */
	public static List<WebElement> visibilityOfAllElementsLocatedBy(
			AppiumDriver<?> driver, By locator, int timeout)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return (new WebDriverWait(driver, timeout))
				.until(invokeElementsExpectedCondition(locator,
						"visibilityOfAllElementsLocatedBy"));
	}

	/**
	 * Choose proper condition method to invoke based on passed argument
	 * 
	 * @param locator
	 *            By
	 * @param methodName
	 *            String
	 * @return ExpectedCondition<WebElement>
	 * @throws NoSuchMethodException
	 *             throw a NoSuchMethodException
	 * @throws SecurityException
	 *             throw a SecurityException
	 * @throws IllegalAccessException
	 *             throw a IllegalAccessException
	 * @throws IllegalArgumentException
	 *             throw a IllegalArgumentException
	 * @throws InvocationTargetException
	 *             throw a InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	private static ExpectedCondition<WebElement> invokeElementExpectedCondition(
			By locator, String methodName) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = ExpectedConditions.class.getDeclaredMethod(methodName,
				new Class[] { By.class });
		return (ExpectedCondition<WebElement>) method.invoke(null, locator);
	}

	/**
	 * Execute the javascript code using JavascriptExecutor with given
	 * parameters
	 * 
	 * @param driver
	 *            WebDriver
	 * @param javascriptToExecute
	 *            String
	 * @param parameters
	 *            Object...
	 * @return Return value of executed javascript code with given parameters.
	 */
	public static Object executeScript(AppiumDriver<?> driver,
			String javascriptToExecute, Object... parameters) {
		return ((JavascriptExecutor) driver).executeScript(javascriptToExecute,
				parameters);
	}

	/**
	 * Choose proper condition method to invoke based on passed argument
	 * 
	 * @param locator
	 *            By
	 * @param methodName
	 *            String
	 * @return ExpectedCondition<List<WebElement>>
	 * @throws NoSuchMethodException
	 *             throw a NoSuchMethodException
	 * @throws SecurityException
	 *             throw a SecurityException
	 * @throws IllegalAccessException
	 *             throw a IllegalAccessException
	 * @throws IllegalArgumentException
	 *             throw a IllegalArgumentException
	 * @throws InvocationTargetException
	 *             throw a InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	private static ExpectedCondition<List<WebElement>> invokeElementsExpectedCondition(
			By locator, String methodName) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = ExpectedConditions.class.getDeclaredMethod(methodName,
				new Class[] { By.class });
		return (ExpectedCondition<List<WebElement>>) method.invoke(null,
				locator);
	}

	/**
	 * Find based on the invisibility of element based on the passing locator
	 * 
	 * @param locator
	 *            By
	 * @return ExpectedCondition<Boolean>
	 */
	private static ExpectedCondition<Boolean> invokeElementExpectedConditionByInvisiblility(
			By locator) {
		return ExpectedConditions.invisibilityOfElementLocated(locator);
	}

}