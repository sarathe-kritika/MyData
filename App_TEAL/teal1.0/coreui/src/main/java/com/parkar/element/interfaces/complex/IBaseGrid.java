package com.parkar.element.interfaces.complex;

import com.parkar.element.interfaces.IBaseCommonElement;
import com.parkar.element.interfaces.IBaseElement;
import com.parkar.exception.ParkarCoreUIException;

public interface IBaseGrid extends IBaseCommonElement{
	
	public IBaseElement getCell(int row, int column) throws ParkarCoreUIException;
    public IBaseElement getCell(int row, String columnName) throws ParkarCoreUIException;

    public IBaseElement getRow(int row) throws ParkarCoreUIException;
    public int getRowNumber(String valueTofind, int column) throws ParkarCoreUIException;
    public int getRowNumber(String valueTofind, String columnName) throws ParkarCoreUIException;

    @Deprecated
    public IBaseElement getFirstRow() throws ParkarCoreUIException;
    
    public int getColumnNumber(String columnName) throws ParkarCoreUIException;
    public String getColumnName(int column) throws ParkarCoreUIException;
    
    public boolean isColumnExist(String columnName) throws ParkarCoreUIException;
    public boolean isColumnExist(int column) throws ParkarCoreUIException;
	
 	
}
