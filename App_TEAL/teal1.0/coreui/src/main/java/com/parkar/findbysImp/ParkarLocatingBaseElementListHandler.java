package com.parkar.findbysImp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.BaseElement;
import com.parkar.element.interfaces.IBaseCommonElement;

public class ParkarLocatingBaseElementListHandler implements InvocationHandler{
	
	private final ElementLocator locator;
    private final Class<?> classType;
    
    //for logging purpose
    private BaseUIPage page;
    private String locatorKey;

	public <T> ParkarLocatingBaseElementListHandler(Class<?> interfaceType, Class<?> classType, String locatorKey, ElementLocator locator, BaseUIPage page) {
	    this.locator = locator;
	    if (!IBaseCommonElement.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }
        this.classType = classType;
        this.page=page;
        this.locatorKey=locatorKey;
	}
	
	@Override
	public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
	    List<WebElement> elements;
	    try {
		    elements = locator.findElements();
	    } catch (NoSuchElementException e) {
	      if ("toString".equals(method.getName())) {
	        return "Proxy element for: " + locator.toString();
	      }
	      else throw e;
	    }
	
	    if ("getWrappedElement".equals(method.getName())) {
	      return elements;
	    }
	   
	    Constructor<?> cons = classType.getConstructor(WebDriver.class,WebElement.class, String.class, String.class, String.class);
	    try {
	    	List<BaseElement> results = new ArrayList<BaseElement>();
	    	for(WebElement e: elements){
	    		results.add((BaseElement) cons.newInstance(page.getDriver(),e,locatorKey,locator.toString(),page.getNavigation()));
		    }
	    	return method.invoke(results,objects);
	    } catch (InvocationTargetException e) {
	      // Unwrap the underlying exception
	    	e.printStackTrace();
	      throw e.getCause();
	    }
	 }
}
