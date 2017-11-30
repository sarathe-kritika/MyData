package com.parkar.element.interfaces;

import java.util.List;

import com.parkar.exception.ParkarCoreMobileException;

public interface IBaseListBox extends IBaseSelect {

	public void selectMultipelOptions(List<?> values) throws ParkarCoreMobileException;

	public void selectRange(int startIndex, int endIndex) throws ParkarCoreMobileException;

	public List<?> getAllSelectedOptions() throws ParkarCoreMobileException;

	public boolean isMultiple() throws ParkarCoreMobileException;

	public void deSelectAll() throws ParkarCoreMobileException;
	
	public void selectAll() throws ParkarCoreMobileException;
}
