package com.parkar.enums;

public enum ParkarLocateVia {
	ByElementClickable("findElementByClickable"),
	ByElementVisible("findElementByVisible"), 
	ByElementInvisible("findElementByInvisible"),
	ByElementPresent("findElementByPresence"),
	ByElementNotPresent("findElementByNotPresence"),
	ByElementsVisible("findElementsByPresence"),
	ByElementsPresent("findElementsByPresence"),
	Default("findElementByPresence");
	String value;
	
	private ParkarLocateVia(String value) {
		this.value = value;
	}
	
	public String getMethodName(){
		return this.value;
	}
}
