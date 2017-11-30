package com.parkar.enums;

public enum ParkarGridType {
	JQGrid("BaseJQGrid"),
	UIGrid("BaseUIGrid"),
	Default("BaseJQGrid");
	
	String value;
	private ParkarGridType(String value) {
		this.value = value;
	}
	
	public String getTypeName(){
		return this.value;
	}
}
