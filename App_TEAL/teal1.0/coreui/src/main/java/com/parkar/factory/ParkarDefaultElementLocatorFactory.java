package com.parkar.factory;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import com.parkar.findbysImp.ParkarDefaultElementLocator;
import com.parkar.logging.ParkarLogger;

public class ParkarDefaultElementLocatorFactory implements ElementLocatorFactory{
	private final SearchContext searchContext;
	private int timeout;
	final static Logger logger = Logger.getLogger(ParkarDefaultElementLocatorFactory.class);
	/**
	 * Constructs a ParkarDefaultElementLocatorFactory Object via searchContext and timeout 
	 * with the searchContext and timeout.
	 * 
	 * @param searchContext:SearchContext
	 * @param timeout:int
	 */
	public ParkarDefaultElementLocatorFactory(SearchContext searchContext, int timeout) {
	    this.searchContext = searchContext;
	    this.timeout = timeout;
	}
	/**
	 * Create locator with field
	 * 
	 * @param field:Field
	 * @return ElementLocator
	 */
    public ElementLocator createLocator(Field field) {
		ParkarLogger.traceEnter();
		ElementLocator elementLocator = new ParkarDefaultElementLocator(searchContext, field, timeout);
		ParkarLogger.traceLeave();
	    return elementLocator;
	}
}
