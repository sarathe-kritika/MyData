package com.parkar.element.interfaces;

import com.parkar.exception.ParkarCoreMobileException;

public interface IBaseRadioButton extends IBaseClick {
	// for check box
	public void setCheck(boolean check) throws ParkarCoreMobileException;
	
	public boolean isSelected() throws ParkarCoreMobileException;
}
