package com.parkar.element.interfaces;

import com.parkar.exception.ParkarCoreUIException;

public interface IBaseDragAndDrop extends IBaseCommonElement {
	public void dragAndDrop(IBaseElement element) throws ParkarCoreUIException;
}