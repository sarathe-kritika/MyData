package com.parkar.element.interfaces;

import com.parkar.exception.ParkarCoreUIException;

public interface IBaseCheckBox extends IBaseCommonElement{
	// for check box
	public void setCheck(boolean check) throws ParkarCoreUIException;
	
	public boolean isSelected() throws ParkarCoreUIException;
}
