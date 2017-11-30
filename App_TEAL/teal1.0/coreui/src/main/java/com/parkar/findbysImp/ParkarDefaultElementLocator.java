package com.parkar.findbysImp;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import com.parkar.enums.ParkarGridType;
import com.parkar.enums.ParkarLocateVia;
import com.parkar.helpers.BasicPageElementHelper;
import com.parkar.report.ParkarReporter;

public class ParkarDefaultElementLocator implements ElementLocator{
	final static Logger logger = Logger.getLogger(ParkarDefaultElementLocator.class);
	
	private final SearchContext searchContext;
	private final boolean shouldCache;
	private final By by;
	private WebElement cachedElement;
	private List<WebElement> cachedElementList;
	
	private final ParkarLocateVia viaMethod;
	private final ParkarGridType tableType;
	private int timeout;
	protected ParkarReporter reporter = ParkarReporter.getInstance();
	public ParkarDefaultElementLocator(SearchContext searchContext, Field field, int timeout) {
	    this(searchContext, new ParkarBaseAnnotations(field), timeout);	
	}
	/**
	 *  The ParkarDefaultElementLocator which find element based on the specified annotations in the search context within 
		the given timeout
	 * 
	 * @param searchContext SearchContext
	 * @param annotations AbstractAnnotations
	 * @param timeout int
	 */
	public ParkarDefaultElementLocator(SearchContext searchContext, AbstractAnnotations annotations, int timeout) {
	    this.searchContext = searchContext;
	    this.shouldCache = annotations.isLookupCached();
	    this.by = annotations.buildBy();
	    //get the via method name to call the proper method
	    if (annotations instanceof ParkarBaseAnnotations){
	    	this.viaMethod = ((ParkarBaseAnnotations)annotations).getParkarFindMethodName();
	    	this.tableType = ((ParkarBaseAnnotations)annotations).getParkarGridTableTypeName();
	    	this.timeout = ((ParkarBaseAnnotations)annotations).getParkarFindTimeout();
	    	if(this.timeout==-1){
			    this.timeout = timeout;
	    	}
	    }else{
	    	this.viaMethod = ParkarLocateVia.Default;
	    	this.tableType = ParkarGridType.Default;
		    this.timeout = timeout;
	    }
	    
	}
	/**
	 * Find element  
	 *  
	 *  @return WebElement
	 */  
	@Override
	public WebElement findElement() {
		if (cachedElement != null && shouldCache) {
	      return cachedElement;
	    }
	    WebElement element = null;
	    String methodName = viaMethod.getMethodName();
		Method method;
		try {
			method = BasicPageElementHelper.class.getDeclaredMethod(methodName, new Class[]{WebDriver.class, By.class, int.class});
			element = (WebElement) method.invoke(null, (WebDriver)searchContext,by,timeout);
		} catch (Exception e) {
			//We will not do anything here as, exception will be thrown from class ParkarLocatingBaseElementHandler, invoke method.
			//Logging and reporting will be done inside BasicPageElementHelper class.
		}
		
	    if (shouldCache) {
	    	if (element!=null) cachedElement = element;
	    }
	    return element;
	}
	
	
	/**
	 * Finds all elements on page for given locator
	 * 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WebElement> findElements() {
		if (cachedElementList != null && shouldCache) {
	      return cachedElementList;
	    }

	    List<WebElement> elements = null;
	    String methodName = viaMethod.getMethodName();
		Method method;
		try {
			method = BasicPageElementHelper.class.getDeclaredMethod(methodName, new Class[]{WebDriver.class, By.class, int.class});
			elements = (List<WebElement>) method.invoke(null, (WebDriver)searchContext,by,timeout);
		} catch (Exception e) {
			//We will not do anything here as, exception will be thrown from class ParkarLocatingBaseElementHandler, invoke method.
			//Logging and reporting will be done inside BasicPageElementHelper class.
		}
		
	    if (shouldCache) {
	      cachedElementList = elements;
	    }

	    return elements;
	}
	
	@Override
	public String toString(){
		return "" + by + "";
	}
	
	public ParkarGridType getTableType(){
		return tableType;
	}
	
	public ParkarLocateVia getVia(){
		return this.viaMethod;
	}
	
/*	public Class<?> getGridType(){
        WebElement grid = null;
        try {
        	grid= searchContext.findElement(by);
        } catch (Exception e) {
               //ignore
        }
        WebElement uiGrid = null;
        WebElement jqxGrid = null;
        if(grid!=null)
        try {
        	uiGrid = grid.findElement(By.xpath(".//*[contains(@class,'ui-grid')]"));
        } catch (Exception e) {
               //ignore
        }
        
        try {
        	jqxGrid = grid.findElement(By.xpath(".//*[contains(@class,'jqx-grid')]"));
        } catch (Exception e) {
               //ignore
        }
        if (uiGrid != null) {
               return BaseUIGrid.class;
        } else if (jqxGrid != null) {
               return BaseJQGrid.class;
        }else{
               return BaseJQGrid.class;
        }
  }
	*/
}
