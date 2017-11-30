
package com.parkar.element.interfaces;

import java.util.List;

import com.parkar.exception.ParkarCoreMobileException;

public interface IBaseSelect extends IBaseCommonElement {

	public void select(String value) throws ParkarCoreMobileException;

	public void select(int index) throws ParkarCoreMobileException;
	
	public void selectByVisibleText(String text) throws ParkarCoreMobileException;

	public void deselect(String value) throws ParkarCoreMobileException;

	public void deselect(int index) throws ParkarCoreMobileException;
	
	public void deselectByVisibleText(String text) throws ParkarCoreMobileException;
	
	public String getSelectedItemValue() throws ParkarCoreMobileException;

	public IBaseElement getSelectedItem() throws ParkarCoreMobileException;

	public List<?> getAllOptions() throws ParkarCoreMobileException;

	public int getSelectCount() throws ParkarCoreMobileException;
	
	public boolean isSelected() throws ParkarCoreMobileException;

}
