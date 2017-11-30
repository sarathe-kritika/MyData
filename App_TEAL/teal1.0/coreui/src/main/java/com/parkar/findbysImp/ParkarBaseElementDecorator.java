package com.parkar.findbysImp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import com.parkar.basepageobject.BaseUIPage;
import com.parkar.element.BaseElement;
import com.parkar.element.interfaces.IBaseCommonElement;

public class ParkarBaseElementDecorator implements FieldDecorator { 

	protected ElementLocatorFactory factory;
	protected BaseUIPage page;

	public ParkarBaseElementDecorator(ElementLocatorFactory factory, BaseUIPage page) {
	    this.factory = factory;
	    this.page = page;
	}
	  
	@Override
	public Object decorate(ClassLoader loader, Field field) {
		if (!(IBaseCommonElement.class.isAssignableFrom(field.getType()) || WebElement.class.isAssignableFrom(field.getType()) || isDecoratableList(field))) {
	      return null;
		}

	    ElementLocator locator = factory.createLocator(field);
	    if (locator == null) {
	    	return null;
	    }
	    
	    Class<?> fieldType = field.getType();
	    Class<?> interfaceType = field.getType();
	    //if the declare type is WebElement, we will use our defined IBaseElement interface instead
	    if (IBaseCommonElement.class.equals(fieldType) || WebElement.class.equals(fieldType) || BaseElement.class.equals(fieldType) ) {
	    	interfaceType = IBaseCommonElement.class;
        }
	    
	    if (IBaseCommonElement.class.isAssignableFrom(fieldType) || WebElement.class.isAssignableFrom(fieldType)) {
	    	return proxyForLocator(loader, interfaceType, field.getName(),locator);
	    } else if (List.class.isAssignableFrom(fieldType)) {
	    	//if it is a list of elements, we assign the interfaceType to default type
	    	interfaceType = IBaseCommonElement.class;
	    	return proxyForListLocator(loader, interfaceType, field.getName(), locator);
	    } else {
	    	return null;
	    }
	 }
	
	protected boolean isDecoratableList(Field field) {
	    if (!List.class.isAssignableFrom(field.getType())) {
	      return false;
	    }
	
	    // Type erasure in Java isn't complete. Attempt to discover the generic type of the list.
	    Type genericType = field.getGenericType();
	    if (!(genericType instanceof ParameterizedType)) {
	      return false;
	    }
	
//	    Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
//	    if (!WebElement.class.equals(listType)) { return false; }
	
	    if (field.getAnnotation(ParkarFindBy.class) == null &&
	    	field.getAnnotation(FindBy.class) == null &&
	        field.getAnnotation(FindBys.class) == null &&
	        field.getAnnotation(FindAll.class) == null) {
	      return false;
	    }

	    return true;
	}
	
	protected <T> T proxyForLocator(ClassLoader loader, Class<T> baseInterface, String locatorKey, ElementLocator locator) {
		
	    InvocationHandler handler = null;
	    /*if(IBaseGrid.class.equals(baseInterface)){
			handler = new ParkarLocatingBaseElementHandler(baseInterface,BaseJQGrid.class, locatorKey, locator, page);
			} else{*/
		    handler = new ParkarLocatingBaseElementHandler(baseInterface,BaseElement.class, locatorKey, locator, page);
	//	}
		return baseInterface.cast(Proxy.newProxyInstance(loader, new Class[]{baseInterface, WrapsElement.class, Locatable.class}, handler));
	    
	}

	@SuppressWarnings("unchecked")
	protected <T> List<IBaseCommonElement> proxyForListLocator(ClassLoader loader, Class<T> baseInterface, String locatorKey, ElementLocator locator) {
	    InvocationHandler handler = new ParkarLocatingBaseElementListHandler(baseInterface,BaseElement.class, locatorKey, locator ,page);
	
	    List<IBaseCommonElement> proxy = (List<IBaseCommonElement>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
        return proxy;
	 }
}
