package com.parkar.element.interfaces;

import java.util.List;

import com.parkar.exception.ParkarCoreUIException;

public interface IBaseListBox extends IBaseSelect {

	public void selectMultipelOptions(List<?> values) throws ParkarCoreUIException;

	public void selectRange(int startIndex, int endIndex) throws ParkarCoreUIException;

	public List<?> getAllSelectedOptions() throws ParkarCoreUIException;

	public boolean isMultiple() throws ParkarCoreUIException;

	public void deSelectAll() throws ParkarCoreUIException;
	
	public void selectAll() throws ParkarCoreUIException;
}
