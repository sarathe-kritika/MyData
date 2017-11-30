package com.parkar.element.interfaces;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;

import com.parkar.exception.ParkarCoreMobileException;

public interface IBaseCommonElement {

	public Object executeScript(String javascriptToExecute, Object... obj) throws ParkarCoreMobileException;

	public String getText() throws ParkarCoreMobileException;

	public void hover() throws ParkarCoreMobileException;
	
	public void hover(int xOffset, int yOffset) throws ParkarCoreMobileException;
	
	public List<IBaseElement> findElements(By by) throws ParkarCoreMobileException;

	public IBaseElement findElement(By by) throws ParkarCoreMobileException;

	public boolean isDisplayed() throws ParkarCoreMobileException;

	public Point getLocation() throws ParkarCoreMobileException;

	public Dimension getSize() throws ParkarCoreMobileException;

	public Rectangle getRect()throws ParkarCoreMobileException;

	public String getCssValue(String propertyName) throws ParkarCoreMobileException;
	
	public boolean isEnabled() throws ParkarCoreMobileException;

	public String getAttribute(String name)throws ParkarCoreMobileException;
	
	public String getTagName();
	
	public boolean isPresent();
	
	public void setAttribute(String attributeName, String value) throws ParkarCoreMobileException;

	public void waitForEnable() throws ParkarCoreMobileException;
	
	public void waitForDisable() throws ParkarCoreMobileException;
	
	public void waitForDisplay() throws ParkarCoreMobileException;

	public void waitForDisappear() throws ParkarCoreMobileException;
}
