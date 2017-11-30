package com.parkar.caseusage.tracker;

public class UsageTracker {
	private String locatorName;
	private String locatorValue;
	private String navigation;
	private String action;

	/**
	 * Default constructor
	 */
	public UsageTracker(){
		
	}
	
	/**
	 * Constructor with four parameters
	 * @param locatorName: String
	 * @param locatorValue: String
	 * @param navigation: String
	 * @param action: String
	 */
	public UsageTracker(String locatorName, String locatorValue, String navigation,String action) {
		this.locatorName = locatorName;
		this.locatorValue = locatorValue;
		this.navigation = navigation;
		this.action = action;
		
		
	}
	
	/**
	 * Get locator name
	 * @return locatorName: String
	 */
	public String getLocatorName() {
		return locatorName;
	}
	
	/**
	 * Set locator name
	 * @param locatorName: String
	 */
	public void setLocatorName(String locatorName) {
		this.locatorName = locatorName;
	}
	
	/**
	 * Get locator value
	 * @return locatorValue: String
	 */
	public String getLocatorValue() {
		return locatorValue;
	}
	
	/**
	 * Set locator value
	 * @param locatorValue: String
	 */
	public void setLocatorValue(String locatorValue) {
		this.locatorValue = locatorValue;
	}
	
	/**
	 * Get navigation
	 * @return navigation: String
	 */
	public String getNavigation() {
		return navigation;
	}
	
	/**
	 * Set navigation
	 * @param navigation: String
	 */
	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	/**
	 * Get action
	 * @return action: String
	 */
	public String getAction() {
		return action;
	}

	
	/**
	 * Set action
	 * @param action: String
	 */
	public void setAction(String action) {
		this.action = action;
	}

}
