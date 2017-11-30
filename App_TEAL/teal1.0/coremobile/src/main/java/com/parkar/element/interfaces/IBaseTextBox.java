package com.parkar.element.interfaces;

import com.parkar.exception.ParkarCoreMobileException;

public interface IBaseTextBox extends IBaseClick {
	
	public void setText(CharSequence... keysToSend) throws ParkarCoreMobileException;

	public void appendText(CharSequence... keysToSend) throws ParkarCoreMobileException;
	
	public void clear() throws ParkarCoreMobileException;

}
