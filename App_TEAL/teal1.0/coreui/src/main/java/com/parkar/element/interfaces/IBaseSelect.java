
package com.parkar.element.interfaces;

import java.util.List;

import com.parkar.exception.ParkarCoreUIException;

public interface IBaseSelect extends IBaseCommonElement {

	public void select(String value) throws ParkarCoreUIException;

	public void select(int index) throws ParkarCoreUIException;
	
	public void selectByVisibleText(String text) throws ParkarCoreUIException;

	public void deselect(String value) throws ParkarCoreUIException;

	public void deselect(int index) throws ParkarCoreUIException;
	
	public void deselectByVisibleText(String text) throws ParkarCoreUIException;
	
	public String getSelectedItemValue() throws ParkarCoreUIException;

	public IBaseElement getSelectedItem() throws ParkarCoreUIException;

	public List<?> getAllOptions() throws ParkarCoreUIException;

	public int getSelectCount() throws ParkarCoreUIException;
	
	public boolean isSelected() throws ParkarCoreUIException;

}
