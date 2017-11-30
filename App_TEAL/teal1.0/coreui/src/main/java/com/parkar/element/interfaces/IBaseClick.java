package com.parkar.element.interfaces;

import com.parkar.exception.ParkarCoreUIException;

public interface IBaseClick extends IBaseCommonElement {

	public void rightClick() throws ParkarCoreUIException;

	public void doubleClick() throws ParkarCoreUIException;

	public void clickAsJScript() throws ParkarCoreUIException;

	public void click() throws ParkarCoreUIException;
	
	public void clickAndHold() throws ParkarCoreUIException;
	
	public void release() throws ParkarCoreUIException;

}
