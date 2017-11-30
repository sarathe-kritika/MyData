package com.parkar.element.interfaces;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;

import com.parkar.exception.ParkarCoreUIException;

public interface IBaseCommonElement {

	public Object executeScript(String javascriptToExecute, Object... obj) throws ParkarCoreUIException;

	public String getText() throws ParkarCoreUIException;

	public void hover() throws ParkarCoreUIException;
	
	public void hover(int xOffset, int yOffset) throws ParkarCoreUIException;
	
	public List<IBaseElement> findElements(By by) throws ParkarCoreUIException;

	public IBaseElement findElement(By by) throws ParkarCoreUIException;

	public boolean isDisplayed() throws ParkarCoreUIException;

	public Point getLocation() throws ParkarCoreUIException;

	public Dimension getSize() throws ParkarCoreUIException;

	public Rectangle getRect()throws ParkarCoreUIException;

	public String getCssValue(String propertyName) throws ParkarCoreUIException;
	
	public boolean isEnabled() throws ParkarCoreUIException;

	public String getAttribute(String name)throws ParkarCoreUIException;
	
	public String getTagName();
	
	public boolean isPresent();
	
	public void setAttribute(String attributeName, String value) throws ParkarCoreUIException;

	public void waitForEnable() throws ParkarCoreUIException;
	
	public void waitForDisable() throws ParkarCoreUIException;
	
	public void waitForDisplay() throws ParkarCoreUIException;

	public void waitForDisappear() throws ParkarCoreUIException;
}
