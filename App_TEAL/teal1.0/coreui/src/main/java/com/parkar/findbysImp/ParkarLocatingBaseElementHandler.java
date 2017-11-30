package com.parkar.findbysImp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.interfaces.IBaseCommonElement;
import com.parkar.enums.ParkarLocateVia;

public class ParkarLocatingBaseElementHandler implements InvocationHandler{
	
	private final ElementLocator locator;
    private final Class<?> classType;
    
    //for logging purpose
    private BaseUIPage page;
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
	public <T> ParkarLocatingBaseElementHandler(Class<?> interfaceType, Class<?> classType, String locatorKey, ElementLocator locator, BaseUIPage page) {
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
/*	    if(classType.equals(BaseJQGrid.class)){
	        Class<?> newClassType = ((ParkarDefaultElementLocator)locator).getGridType();
	    	System.out.println("change Grid class type to =>" + newClassType);
		    cons = newClassType.getConstructor(WebDriver.class,WebElement.class, String.class, String.class, String.class);
	    }else{*/
		    cons = classType.getConstructor(WebDriver.class,WebElement.class, String.class, String.class, String.class);
	   // }
	    try {
	      return method.invoke(cons.newInstance(page.getDriver(),element,locatorKey,locator.toString(),page.getNavigation()), objects);
	    } catch (InvocationTargetException e) {
	      // Unwrap the underlying exception
	      throw e.getCause();
	    }
	 }
}
