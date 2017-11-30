package com.parkar.element.interfaces;

import com.parkar.exception.ParkarCoreMobileException;

public interface IBaseClick extends IBaseCommonElement {

	public void rightClick() throws ParkarCoreMobileException;

	public void doubleClick() throws ParkarCoreMobileException;

	public void clickAsJScript() throws ParkarCoreMobileException;

	public void click() throws ParkarCoreMobileException;
	
	public void clickAndHold() throws ParkarCoreMobileException;
	
	public void release() throws ParkarCoreMobileException;

}
