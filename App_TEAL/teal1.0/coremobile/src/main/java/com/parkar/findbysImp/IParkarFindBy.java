package com.parkar.findbysImp;

import org.openqa.selenium.By;

public interface IParkarFindBy {
	/**
	 * Constructs a By object via ParkarFindBy annotation
	 * 
	 * @param findBy
	 *            ParkarFindBy
	 * @return By
	 */
	public By buildFindBy(ParkarFindBy findBy);
}
