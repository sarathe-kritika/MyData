package com.parkar.element.interfaces;

import com.parkar.exception.ParkarCoreMobileException;

public interface IBaseDragAndDrop extends IBaseCommonElement {
	public void dragAndDrop(IBaseElement element) throws ParkarCoreMobileException;
}