package com.parkar.findbysImp;

import io.appium.java_client.AppiumDriver;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;

import com.parkar.enums.ParkarLocateVia;

public class ParkarBaseAnnotations extends AbstractAnnotations{
  private Field field;
  private AppiumDriver<?> driver;
  final static Logger logger = Logger.getLogger(ParkarBaseAnnotations.class);
  /**
   * @param field expected to be an element in a Page Object
   * @param driver AppiumDriver
   */
  public ParkarBaseAnnotations(Field field, AppiumDriver<?> driver) {
		this.driver = driver;
		this.field = field;
  }

  /**
   * {@inheritDoc}
   *
   * @return true if @CacheLookup annotation exists on a field
   */
  public boolean isLookupCached() {
    return (field.getAnnotation(CacheLookup.class) != null);
  }

  /**
   * {@inheritDoc}
   *
   * Looks for one of field annotations. In case
   * no annotaions provided for field, uses field name as 'id' or 'name'.
   * @throws IllegalArgumentException when more than one annotation on a field provided
   */
	public By buildBy() {
		ParkarFindBy parkarFindBy = field.getAnnotation(ParkarFindBy.class);
		return parkarFindBy!=null?ParkarFindByFactory.getByType(driver, parkarFindBy): buildByFromDefault();
	}
	
	/**
   * Get the name of via mentioned in @ParkarFindBy else Default value
   * 
   * @return ParkarLocateVia
   */
  public ParkarLocateVia getParkarFindMethodName(){
	  ParkarFindBy parkarFindBy = field.getAnnotation(ParkarFindBy.class);
	  return parkarFindBy.via(); 
  }
 
  /**
   * Get the name of timeout mentioned in @ParkarFindBy else Default value
   * 
   * @return int
   */
	public int getParkarFindTimeout(){
		ParkarFindBy parkarFindBy = field.getAnnotation(ParkarFindBy.class);
		return parkarFindBy.timeout();
	}
 
	/**
	 * Get Field
	 * 
	 * @return field:Field
	 */
	protected Field getField() {
		return field;
	}
  /**
   * Constructs a By object via fieldName
   * 
   * @return By
   */
  protected By buildByFromDefault() {
	  return new ByIdOrName(field.getName());
  }    
}
