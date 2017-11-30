package com.parkar.findbysImp;

import io.appium.java_client.AppiumDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import com.parkar.basepageobject.BaseMobilePage;
import com.parkar.element.interfaces.IBaseCommonElement;
import com.parkar.enums.ParkarLocateVia;

public class ParkarLocatingBaseElementHandler implements InvocationHandler{
	
	private final ElementLocator locator;
    private final Class<?> classType;
    
    //for logging purpose
    private BaseMobilePage page;
    private String locatorKey;
    /**
     * Constructs a ParkarLocatingBaseElementHandler Object
     * 
     * @param interfaceType Class
     * @param classType Class
     * @param locatorKey String
     * @param locator ElementLocator
     * @param page Basepage
     * @param <T> Type for the BaseElement output
     */
	public <T> ParkarLocatingBaseElementHandler(Class<?> interfaceType, Class<?> classType, String locatorKey, ElementLocator locator, BaseMobilePage page) {
	    this.locator = locator;
	    if (!IBaseCommonElement.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }
        this.classType = classType;
        this.page=page;
        this.locatorKey=locatorKey;
	}
	/**
	 * Execute a method and return Object
	 * 
	 * @param object object
	 * @param method Method
	 * @param objects Object[]
	 * @return Object
	 * @throws Throwable throws throwable
	 */
	@Override
	public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
	    WebElement element;
	    try {
	    	element = locator.findElement();
	    } catch (NoSuchElementException e) {
	      if ("toString".equals(method.getName())) {
	        return "Proxy element for: " + locator.toString();
	      }
	      else throw e;
	    }
	
	    if ("getWrappedElement".equals(method.getName())) {
	      return element;
	    }
	    
	    if (element == null){
			if (((ParkarDefaultElementLocator) locator).getVia() != ParkarLocateVia.ByElementNotPresent 
					&& ((ParkarDefaultElementLocator) locator).getVia() != ParkarLocateVia.ByElementInvisible ) {
				throw new RuntimeException("Cannot find element with key:["+ locatorKey + "] locator:[" + locator.toString() + "]");
	    	}
	    }
	   
	    /**
		 * find the grid type before invocation
		 */
	    Constructor<?> cons = null;
	    cons = classType.getConstructor(AppiumDriver.class,WebElement.class, String.class, String.class, String.class);
	    try {
	      return method.invoke(cons.newInstance(page.getDriver(),element,locatorKey,locator.toString(),page.getNavigation()), objects);
	    } catch (InvocationTargetException e) {
	      // Unwrap the underlying exception
	      throw e.getCause();
	    }
	 }
}
