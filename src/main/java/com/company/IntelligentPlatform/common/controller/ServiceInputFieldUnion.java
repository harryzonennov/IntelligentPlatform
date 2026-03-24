package com.company.IntelligentPlatform.common.controller;

public class ServiceInputFieldUnion {
	
	public static final String INPUTTYPE_INPUT = "input";
	
	public static final String INPUTTYPE_SELECT = "select";
	
	protected String path;
	
	protected String elementId;
	
	protected String elementClass;
	
	protected String inputType;
	
	protected int size;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getElementClass() {
		return elementClass;
	}

	public void setElementClass(String elementClass) {
		this.elementClass = elementClass;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
