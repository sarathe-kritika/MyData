package com.parkar.element.interfaces;

import com.parkar.exception.ParkarCoreUIException;

public interface IBaseTextBox extends IBaseClick {
	
	public void setText(CharSequence... keysToSend) throws ParkarCoreUIException;

	public void appendText(CharSequence... keysToSend) throws ParkarCoreUIException;
	
	public void clear() throws ParkarCoreUIException;

}
