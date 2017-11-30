package com.parkar.element;

import io.appium.java_client.AppiumDriver;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.parkar.element.interfaces.IBaseElement;
import com.parkar.exception.ParkarCoreMobileException;
import com.parkar.helpers.BasicPageSyncHelper;
import com.parkar.logging.ParkarLogger;
import com.parkar.report.CoreReporter;
import com.parkar.report.ParkarReporter;
import com.parkar.report.StepStatus;
import com.parkar.testng.Configurator;
import com.parkar.utils.ParkarSeleniumUtil;

public class BaseElement implements IBaseElement {
	protected final WebElement element;
	protected final AppiumDriver<?> driver;
	final static Logger logger = Logger.getLogger(BaseElement.class);
	// for logging purpose
	public final String locatorKey;
	public final String locator;
	public final String navigation;
	private CoreReporter reporter = ParkarReporter.getInstance();
	final static int timeout = Integer.parseInt(Configurator.getInstance().getParameter("timeout"));

	public BaseElement(AppiumDriver<?> driver, final WebElement element, String locatorKey, String locator,
			String navigation) {
		this.element = element;
		this.driver = driver;
		this.locator = locator;
		this.navigation = navigation;
		this.locatorKey = locatorKey;
		highlight();
	}
	
	/**
	 * A alternative constructor in case of IBaseElement is passed instead of WebElement
	 * @param driver : Webdriver
	 * @param element : IBaseElement
	 * @param locatorKey : String
	 * @param locator : String
	 * @param navigation : String
	 */
	public BaseElement(AppiumDriver<?> driver, final IBaseElement element, String locatorKey, String locator,
			String navigation) {
		this.element = ((BaseElement)element).getWrappedElement();
		this.driver = driver;
		this.locator = locator;
		this.navigation = navigation;
		this.locatorKey = locatorKey;
		highlight();
	}
	
	public WebElement getWrappedElement(){
		return this.element;
	}

	/**
	 * Use this method to append text into an element, which may set its value.
	 *
	 * @param keysToSend
	 *            character sequence to append to the element
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 * 
	 */
	@Override
	public void appendText(CharSequence... keysToSend) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "appendText: " + asString(keysToSend) + " [ " + locatorKey + " : " + locator + " ]";
		try {
			String preText = element.getText();
			element.clear();
			element.sendKeys(preText + keysToSend);
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Use this method to simulate typing into an element, which may set its
	 * value.
	 *
	 * @param text
	 *            character sequence to send to the element
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void setText(CharSequence... text) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "setText: " + asString(text) + " [ " + locatorKey + " : " + locator + " ]";
		try {
			element.clear();
			element.sendKeys(text);
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Performs a context-click at middle of the given element. First performs a
	 * mouseMove to the location of the element.
	 * 
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void rightClick() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "rightClick: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			new Actions(driver).contextClick(element).perform();
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Performs a double-click at middle of the given element.
	 * 
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void doubleClick() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "doubleClick: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			new Actions(driver).doubleClick(element).perform();
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * To click checkbox. If given value is true it will check. If given value
	 * is false it will uncheck.
	 * 
	 * @param check boolean
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void setCheck(boolean check) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "setCheck: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			if (check != element.isSelected())
				element.click();
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Get Select drop down object
	 * 
	 * @return Select
	 */
	private Select getSelect(){
		ParkarLogger.traceEnter();
		String infoMsg = "getSelect: " + " [ " + locatorKey + " : " + locator + " ]";
		Select select = new Select(element);
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return select;
	}

	/**
	 * Get selected item value from a select box
	 * 
	 * @return selected element value
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public String getSelectedItemValue() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String text = null;
		String infoMsg = "getSelectedItemValue: %s" + " [ " + escapePercentage(locatorKey) + " : " + escapePercentage(locator) + " ]";
		try {
			text = getSelect().getFirstSelectedOption().getText();
			infoMsg = String.format(infoMsg, text);
			logger.info(infoMsg);
		} catch (Exception e) {
			infoMsg = String.format(infoMsg, text);
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
		return text;
	}

	/**
	 * Select all options that have a value matching the argument. That is, when
	 * given "foo" this would select an option like:
	 *
	 * &lt;option value="foo"&gt;Bar&lt;/option&gt;
	 *
	 * @param value
	 *            The value to match against
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void select(String value) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "select: " + value + " [ " + locatorKey + " : " + locator + " ]";
		try {
			getSelect().selectByValue(value);
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Select the option at the given index. This is done by examining the
	 * "index" attribute of an element, and not merely by counting.
	 *
	 * @param index
	 *            The option at this index will be selected
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void select(int index) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "select: " + index + " [ " + locatorKey + " : " + locator + " ]";
		try {
			getSelect().selectByIndex(index);
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Select all options that display text matching the argument. That is, when
	 * given "Bar" this would select an option like:
	 *
	 * &lt;option value="foo"&gt;Bar&lt;/option&gt;
	 *
	 * @param text
	 *            The visible text to match against
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	public void selectByVisibleText(String text) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "selectByVisibleText: " + text + " [ " + locatorKey + " : " + locator + " ]";
		try {
			getSelect().selectByVisibleText(text);
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Deselect all options that have a value matching the argument. That is,
	 * when given "foo" this would deselect an option like:
	 *
	 * &lt;option value="foo"&gt;Bar&lt;/option&gt;
	 *
	 * @param value
	 *            The value to match against
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 * 
	 */
	@Override
	public void deselect(String value) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "deselect: " + value + " [ " + locatorKey + " : " + locator + " ]";
		try {
			getSelect().deselectByValue(value);
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Deselect the option at the given index. This is done by examining the
	 * "index" attribute of an element, and not merely by counting.
	 *
	 * @param index
	 *            The option at this index will be deselected
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 * 
	 */
	@Override
	public void deselect(int index) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "deselect: " + index + " [ " + locatorKey + " : " + locator + " ]";
		try {
			getSelect().deselectByIndex(index);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
			logger.info(infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Deselect all options that display text matching the argument. That is,
	 * when given "Bar" this would deselect an option like:
	 *
	 * &lt;option value="foo"&gt;Bar&lt;/option&gt;
	 *
	 * @param text
	 *            The visible text to match against
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 *             I
	 */
	public void deselectByVisibleText(String text) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "deselectByVisibleText: " + text + " [ " + locatorKey + " : " + locator + " ]";
		try {
			getSelect().deselectByVisibleText(text);
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Click this element. If this causes a new page to load, you should discard
	 * all references to this element and any further operations performed on
	 * this element will throw a StaleElementReferenceException.
	 *
	 * Note that if click() is done by sending a native event (which is the
	 * default on most browsers/platforms) then the method will _not_ wait for
	 * the next page to load and the caller should verify that themselves.
	 *
	 * 
	 * There are some preconditions for an element to be clicked. The element
	 * must be visible and it must have a height and width greater then 0.
	 *
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void click() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "click: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			element.click();
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}
	

	/**
	 * If this current element is a form, or an element within a form, then this
	 * will be submitted to the remote server. If this causes the current page
	 * to change, then this method will block until the new page is loaded.
	 *
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void submit() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "submit: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			element.submit();
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * If this element is a text entry element, this will clear the value. Has
	 * no effect on other elements. Text entry elements are INPUT and TEXTAREA
	 * elements.
	 *
	 * Note that the events fired by this event may not be as you'd expect. In
	 * particular, we don't fire any keyboard or mouse events. If you want to
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void clear() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "clear: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			element.clear();
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Get the tag name of this element. <b>Not</b> the value of the name
	 * attribute: will return <code>"input"</code> for the element
	 * <code>&lt;input name="foo" /&gt;</code>.
	 *
	 * @return The tag name of this element.
	 */
	@Override
	public String getTagName(){
		ParkarLogger.traceEnter();
		String tagName = null;
		tagName = element.getTagName();
		String infoMsg = "getTagName: " + tagName + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return tagName;
	}

	/**
	 * Get the value of a the given attribute of the element. Will return the
	 * current value, even if this has been modified after the page has been
	 * loaded. More exactly, this method will return the value of the given
	 * attribute, unless that attribute is not present, in which case the value
	 * of the property with the same name is returned (for example for the
	 * "value" property of a textarea element). If neither value is set, null is
	 * returned. The "style" attribute is converted as best can be to a text
	 * representation with a trailing semi-colon. The following are deemed to be
	 * "boolean" attributes, and will return either "true" or null:
	 *
	 * async, autofocus, autoplay, checked, compact, complete, controls,
	 * declare, defaultchecked, defaultselected, defer, disabled, draggable,
	 * ended, formnovalidate, hidden, indeterminate, iscontenteditable, ismap,
	 * itemscope, loop, multiple, muted, nohref, noresize, noshade, novalidate,
	 * nowrap, open, paused, pubdate, readonly, required, reversed, scoped,
	 * seamless, seeking, selected, spellcheck, truespeed, willvalidate
	 *
	 * Finally, the following commonly mis-capitalized attribute/property names
	 * are evaluated as expected:
	 *
	 * <ul>
	 * <li>"class"
	 * <li>"readonly"
	 * </ul>
	 *
	 * @param name
	 *            The name of the attribute.
	 * @return The attribute/property's current value or null if the value is
	 *         not set.
	 */
	@Override
	public String getAttribute(String name) {
		ParkarLogger.traceEnter();
		String attributeName = null;
		attributeName = element.getAttribute(name);
		String infoMsg = "getAttribute: " + attributeName + " [ " + locatorKey + " : " + locator + " ] ";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return attributeName;
	}

	/**
	 * Determine whether or not this element is selected or not. This operation
	 * only applies to input elements such as checkboxes, options in a select
	 * and radio buttons.
	 *
	 * @return True if the element is currently selected or checked, false
	 *         otherwise.
	 */
	@Override
	public boolean isSelected() {
		ParkarLogger.traceEnter();
		boolean isSelected = element.isSelected();
		String infoMsg = "isSelected: " + isSelected + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return isSelected;
	}

	/**
	 * Is the element currently enabled or not? This will generally return true
	 * for everything but disabled input elements.
	 *
	 * @return True if the element is enabled, false otherwise.
	 */
	@Override
	public boolean isEnabled() {
		ParkarLogger.traceEnter();
		boolean isEnabled = element.isEnabled();
		String infoMsg = "isEnabled: " + isEnabled + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return isEnabled;
	}

	/**
	 * Get the visible (i.e. not hidden by CSS) innerText of this element,
	 * including sub-elements, without any leading or trailing whitespace.
	 *
	 * @return The innerText of this element.
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public String getText() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String text = null;
		String infoMsg = "getText: %s" + " [ " + escapePercentage(locatorKey) + " : " + escapePercentage(locator) + " ]";
		if (StringUtils.equalsIgnoreCase(element.getTagName(), "input")) {
			text = getAttribute("value");
		} else if (StringUtils.equalsIgnoreCase(element.getTagName(), "select")) {
			text = getSelectedItemValue();
		} else {
			try {
				text = element.getText();
			} catch (Exception e) {
				infoMsg = String.format(infoMsg, text);
				logger.error(infoMsg, e);
				throw new ParkarCoreMobileException(infoMsg, e);
			}
		}
		infoMsg = String.format(infoMsg, text);
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return text;
	}

	/**
	 * Find all elements within the current context using the given mechanism.
	 * When using xpath be aware that webdriver follows standard conventions: a
	 * search prefixed with "//" will search the entire document, not just the
	 * children of this current node. Use ".//" to limit your search to the
	 * children of this WebElement. This method is affected by the 'implicit
	 * wait' times in force at the time of execution. When implicitly waiting,
	 * this method will return as soon as there are more than 0 items in the
	 * found collection, or will return an empty list if the timeout is reached.
	 *
	 * @param by
	 *            The locating mechanism to use
	 * @return A list of all {@link IBaseElement}s, or an empty list if nothing
	 *         matches.
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 * @see org.openqa.selenium.By
	 * @see org.openqa.selenium.WebDriver.Timeouts
	 */
	@Override
	public List<IBaseElement> findElements(By by) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		List<IBaseElement> webElements = new ArrayList<IBaseElement>();
		List<WebElement> temp = null;
		String infoMsg = "findElements: " + by;
		try {
			temp = element.findElements(by);
			for(WebElement e:temp){
				webElements.add(new BaseElement(driver,
				e, locator,
				locator, navigation));
			}
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
		return webElements;
	}

	/**
	 * Find the first {@link IBaseElement} using the given method. See the note in
	 * {@link #findElements(By)} about finding via XPath. This method is
	 * affected by the 'implicit wait' times in force at the time of execution.
	 * The findElement(..) invocation will return a matching row, or try again
	 * repeatedly until the configured timeout is reached.
	 *
	 * findElement should not be used to look for non-present elements, use
	 * {@link #findElements(By)} and assert zero length response instead.
	 *
	 * @param by
	 *            The locating mechanism
	 * @return The first matching element on the current context.
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 * @see org.openqa.selenium.By
	 * @see org.openqa.selenium.WebDriver.Timeouts
	 */
	@Override
	public IBaseElement findElement(By by) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		WebElement webElement = null;
		String infoMsg = "findElement: " + by;
		try {
			webElement = element.findElement(by);
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
		return new BaseElement(driver,
				webElement, locator,
				locator, navigation);
	}

	/**
	 * Is this element displayed or not? This method avoids the problem of
	 * having to parse an element's "style" attribute.
	 *
	 * @return Whether or not the element is displayed
	 */
	@Override
	public boolean isDisplayed() {
		ParkarLogger.traceEnter();
		boolean isDisplayed = element.isDisplayed();
		String infoMsg = "isDisplayed: " + isDisplayed + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return isDisplayed;
	}

	/**
	 * Where on the page is the top left-hand corner of the rendered element?
	 *
	 * @return A point, containing the location of the top left-hand corner of
	 *         the element
	 */
	@Override
	public Point getLocation() {
		ParkarLogger.traceEnter();
		Point point = null;
		point = element.getLocation();
		String infoMsg = "getLocation: " + point + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return point;
	}

	/**
	 * What is the width and height of the rendered element?
	 *
	 * @return The size of the element on the page.
	 */
	@Override
	public Dimension getSize() {
		ParkarLogger.traceEnter();
		Dimension dimension = element.getSize();
		String infoMsg = "getSize: " + dimension + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return dimension;
	}

	/**
	 * @return The location and size of the rendered element
	 */
	@Override
	public Rectangle getRect() {
		ParkarLogger.traceEnter();
		Rectangle rectangle = element.getRect();
		String infoMsg = "getRect: " + rectangle + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return rectangle;
	}

	/**
	 * Get the value of a given CSS property. Color values should be returned as
	 * rgba strings, so, for example if the "background-color" property is set
	 * as "green" in the HTML source, the returned value will be
	 * "rgba(0, 255, 0, 1)".
	 *
	 * Note that shorthand CSS properties (e.g. background, font, border,
	 * border-top, margin, margin-top, padding, padding-top, list-style,
	 * outline, pause, cue) are not returned, in accordance with the <a href=
	 * "http://www.w3.org/TR/DOM-Level-2-Style/css.html#CSS-CSSStyleDeclaration">
	 * DOM CSS2 specification</a> - you should directly access the longhand
	 * properties (e.g. background-color) to access the desired values.
	 *
	 * @param propertyName
	 *            the css property name of the element
	 * @return The current, computed value of the property.
	 */
	@Override
	public String getCssValue(String propertyName) {
		ParkarLogger.traceEnter();
		String cssValue = element.getCssValue(propertyName);
		String infoMsg = "getCssValue: " + cssValue + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return cssValue;
	}
	
	/**
	 * Execute the javascript code using JavascriptExecutor with given parameters
	 * 
	 * @param javascriptToExecute Java script to execute
	 * @return Return value of executed java script code with given parameters.
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public Object executeScript(String javascriptToExecute, Object... parameters) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "executeScript: " + javascriptToExecute + " with parameter: " + parameters;
		Object retValue = null;
		try {
			retValue = ((JavascriptExecutor) driver).executeScript(javascriptToExecute, element, parameters);
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		
		ParkarLogger.traceLeave();
		return retValue;
	}


	/**
	 * Click an element using JavascriptExecutor. Use when normal click doesn't
	 * work.
	 * 
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void clickAsJScript() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
		String infoMsg = "clickAsJScript: " + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		reporter.deepReportStep(StepStatus.INFO, infoMsg);
		ParkarLogger.traceLeave();
	}

	/**
	 * Moves the mouse to the middle of the element. The element is scrolled
	 * into view and its location is calculated using getBoundingClientRect.
	 * 
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void hover() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		Actions builder = null;
		String infoMsg = "hover: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			builder = new Actions(driver);
			builder.moveToElement(element).build().perform();
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			try {
				builder.moveToElement(element).build().perform();
			} catch (Exception e1) {
				logger.error(infoMsg, e);
				reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
				throw new ParkarCoreMobileException(infoMsg, e);
			}
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Moves the mouse to an offset from the top-left corner of the element. The
	 * element is scrolled into view and its location is calculated using
	 * getBoundingClientRect.
	 * 
	 * @param xOffset
	 *            Offset from the top-left corner. A negative value means
	 *            coordinates left from the element.
	 * @param yOffset
	 *            Offset from the top-left corner. A negative value means
	 *            coordinates above the element.
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void hover(int xOffset, int yOffset) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		Actions builder = null;
		String infoMsg = "hover: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			builder = new Actions(driver);
			builder.moveToElement(element, xOffset, yOffset).build().perform();
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			try {
				builder.moveToElement(element, xOffset, yOffset).build().perform();
			} catch (Exception e1) {
				reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e1);
				logger.error(infoMsg, e1);
				throw new ParkarCoreMobileException(infoMsg, e1);
			}
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * @return The first selected option in this select tag (or the currently
	 *         selected option in a normal select)
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public IBaseElement getSelectedItem() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		WebElement webElement = null;
		String infoMsg = "getSelectedItem: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			webElement = getSelect().getFirstSelectedOption();
			logger.info(infoMsg);
		} catch (Exception e) {
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			logger.error(infoMsg, e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
		return new BaseElement(driver,
				webElement, locatorKey,
				locator, navigation);
	}

	/**
	 * Get list of all options belonging to given select element
	 * @return All options belonging to this select tag
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException: While creation of select element
	 */
	@Override
	public List<String> getAllOptions() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "getAllOptions: " + " [ " + locatorKey + " : " + locator + " ]";
		List<String> options = null;
		try {
			List<WebElement> list = getSelect().getOptions();
			options = new ArrayList<String>();
			for (WebElement webElement : list) {
				options.add(webElement.getText());
			}
		} catch (Exception e) {
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			logger.error(infoMsg, e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return options;
	}

	/**
	 * Get option count from a list
	 * 
	 * @return size
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException: While creation of select element
	 */
	@Override
	public int getSelectCount() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		int size = 0;
		String infoMsg = "getSelectCount: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			size = getSelect().getOptions().size();
		} catch (Exception e) {
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			logger.error(infoMsg, e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return size;
	}

	/**
	 * Select multiple options by value
	 * 
	 * @param values List
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void selectMultipelOptions(List<?> values) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "selectMultipelOptions: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			Select select = getSelect();
			if (values != null && values.size() > 0) {
				for (Object value : values) {
					if (value != null)
						select.selectByValue(value.toString());
				}
			}
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		logger.info(infoMsg);
		reporter.deepReportStep(StepStatus.INFO, infoMsg);
		ParkarLogger.traceLeave();
	}

	/**
	 * Select multiple options by index with given range.
	 * 
	 * @param startIndex int
	 * @param endIndex int
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void selectRange(int startIndex, int endIndex) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "selectRange: " + startIndex + " to " + endIndex + " [ " + locatorKey + " : " + locator + " ]";
		try {
			Select select = getSelect();
			for (int index = startIndex; index <= endIndex; index++) {
				select.selectByIndex(index);
			}
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		logger.info(infoMsg);
		reporter.deepReportStep(StepStatus.INFO, infoMsg);
		ParkarLogger.traceLeave();

	}

	/**
	 * @return All selected options belonging to this select tag
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException: While creation of select element
	 */
	@Override
	public List<?> getAllSelectedOptions() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "getAllSelectedOptions: " + " [ " + locatorKey + " : " + locator + " ]";
		List<IBaseElement> webElements = new ArrayList<IBaseElement>();
		try {
			for(WebElement e: getSelect().getAllSelectedOptions()){
				webElements.add(new BaseElement(driver, e, locatorKey, locator, navigation));
			}
		} catch (Exception e) {
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			logger.error(infoMsg, e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		logger.info(infoMsg);
		reporter.deepReportStep(StepStatus.INFO, infoMsg);
		ParkarLogger.traceLeave();
		return webElements;
	}

	/**
	 * @return Whether this select element support selecting multiple options at
	 *         the same time? This is done by checking the value of the
	 *         "multiple" attribute.
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException: While creation of select element
	 */
	@Override
	public boolean isMultiple() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		boolean isMultiple = getSelect().isMultiple();
		String infoMsg = "isMultiple: " + isMultiple + " [ " + locatorKey + " : " + locator + " ] multiple: ";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return isMultiple;
	}

	/**
	 * Clear all selected entries. This is only valid when the SELECT supports
	 * multiple selections.
	 *
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void deSelectAll() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "deSelectAll: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			getSelect().deselectAll();
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		logger.info(infoMsg);
		reporter.deepReportStep(StepStatus.INFO, infoMsg);
		ParkarLogger.traceLeave();
	}

	/**
	 * Select all option in combo box
	 * 
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void selectAll() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "selectAll: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			selectMultipelOptions(getAllOptions());
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}

	/**
	 * Check for element is not null Note: Use this method only if you using
	 * ParkarLocatVia as ByElementNotPresent. This method is design to check for
	 * element not present on page. Method will through exception if element is
	 * not present in DOM and not used ParkarLocatVia as ByElementNotPresent
	 * 
	 * @return boolean: Check for element is not null
	 */
	@Override
	public boolean isPresent(){
		ParkarLogger.traceEnter();
		boolean isElementPresent = element != null;
		String infoMsg = "isPresent:" + isElementPresent + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
		return isElementPresent;
	}

	/**
	 * A convenience method that performs click-and-hold at the location of the
	 * source element, moves to the location of the target element, then
	 * releases the mouse.
	 * @param target
	 *            element to move to and release the mouse at.
	 * @throws ParkarCoreMobileException : ParkarCoreUIException will be thrown on operation failure
	 */
	@Override
	public void dragAndDrop(IBaseElement target) throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		if (!(target instanceof Proxy)) {
			throw new ParkarCoreMobileException(
					"dragAndDrop method only works with instance of Proxy");
		}
		String text = null;
		String infoMsg = "Drag and drop: Drag element with locator [ " + locatorKey + " : " + locator + " ] to element";
		try {
			WebElement targetWebElement = (WebElement) Proxy.getInvocationHandler(target).invoke(target,
							BaseElement.class.getMethod("getWrappedElement", new Class[] {}), null);
			Actions builder = new Actions(driver);
			builder.dragAndDrop(this.getWrappedElement(), targetWebElement).build().perform();
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			infoMsg = String.format(infoMsg, text);
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg,
					BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		} catch (Throwable e) {
			infoMsg = String.format(infoMsg, text);
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg,
					BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();}


	/**
	 * Clicks (without releasing) in the middle of the given element. This is
	 * equivalent to: <i>Actions.moveToElement(onElement).clickAndHold()</i>
	 * @throws ParkarCoreMobileException : ParkarCoreUIException will be thrown on operation failure
	 */
	@Override
	public void clickAndHold() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		Actions builder = null;
		String infoMsg = "clickAndHold: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			builder = new Actions(driver);
			builder.clickAndHold(element).build().perform();
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();		
	}

	/**
	 * Releases the depressed left mouse button, in the middle of the given
	 * element. This is equivalent to:
	 * <i>Actions.moveToElement(onElement).release()</i>
	 *
	 * Invoking this action without invoking {@link #clickAndHold()} first will
	 * result in undefined behavior.
	 * @throws ParkarCoreMobileException : ParkarCoreUIException will be thrown on operation failure
	 */
	@Override
	public void release() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		Actions builder = null;
		String infoMsg = "release: " + " [ " + locatorKey + " : " + locator + " ]";
		try {
			builder = new Actions(driver);
			builder.release(element).build().perform();
			logger.info(infoMsg);
			reporter.deepReportStep(StepStatus.INFO, infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();		
	
		
	}
	/**
	 * Set an attribute of the WebElement
	 * 
	 * @param attributeName
	 *            The attribute to change
	 * @param value
	 *            The value to set
	 * 
	 * @exception ParkarCoreMobileException throws a  ParkarCoreUIException
	 */
	public void setAttribute(String attributeName, String value)
			throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		String infoMsg = "setAttribute: " + "[ " + element + " ]" + " attribute name: " + attributeName + " value: "
				+ value;
		try {
			ParkarSeleniumUtil.executeScript(driver, "arguments[0].setAttribute(arguments[1], arguments[2])",  element, attributeName, value);
			logger.info(infoMsg);
		} catch (Exception e) {
			logger.error(infoMsg, e);
			reporter.deepReportStep(StepStatus.FAIL, infoMsg, BasicPageSyncHelper.saveAsScreenShot(driver), e);
			throw new ParkarCoreMobileException(infoMsg, e);
		}
		ParkarLogger.traceLeave();
	}
	
	

	/**
	 * wait for the current element to be enabled.
	 * 
	 * 	@throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void waitForEnable() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		BasicPageSyncHelper.waitForElementToEnable(driver, this.element,timeout);
		String infoMsg = "waitForEnabled:" + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();
	}

	/**
	 * wait for the current element to be disabled.
	 * 
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void waitForDisable() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		BasicPageSyncHelper.waitForElementToDisable(driver, this.element,timeout);
		String infoMsg = "waitForDisable:" + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();		
	}
	
	/**
	 * wait for the current element to be displayed.
	 * 
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void waitForDisplay() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		BasicPageSyncHelper.waitForElementToDisplay(driver, this.element,timeout);
		String infoMsg = "waitForDisplay:" + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();		
	}
	
	/**
	 * wait for the current element to disappear.
	 * 
	 * @throws ParkarCoreMobileException throw a ParkarCoreUIException
	 */
	@Override
	public void waitForDisappear() throws ParkarCoreMobileException {
		ParkarLogger.traceEnter();
		BasicPageSyncHelper.waitForElementToDisappear(driver, this.element,timeout);
		String infoMsg = "waitForDisppear:" + " [ " + locatorKey + " : " + locator + " ]";
		logger.info(infoMsg);
		ParkarLogger.traceLeave();		
	}
	
	/**
	 * Convert character sequence as a string.
	 * 
	 * @param charSequence
	 * @return string
	 */
	private String asString(CharSequence... charSequence) {
		String text = (charSequence == null ? null : "");
		if (charSequence != null)
			for (int i = 0; i < charSequence.length; i++) {
				text = text + charSequence[i];
			}
		return text;
	}

	
	/**
	 * This is a method should be called when we need to format our infoMsg with
	 * locator which contains % in it
	 * 
	 * @return
	 */
	private String escapePercentage(String locator) {
		if (locator.contains("%")) {
			return locator.replace("%", "%%");
		}
		return locator;

	}

	/**
	 * Highlight an element in the UI
	 *          
	 */
	private void highlight(){
		ParkarLogger.traceEnter();
		try {
			if(StringUtils.containsIgnoreCase(driver.getContext(), "WEBVIEW"))
				ParkarSeleniumUtil.executeScript(driver,"arguments[0].setAttribute('style', arguments[1]);", element, "border: 2px solid red;");
		} catch (Exception e) {
			String errorMsg = "Highlight Animation fail";
			logger.error(errorMsg, e);
		}
		ParkarLogger.traceLeave();
		
	}
}
