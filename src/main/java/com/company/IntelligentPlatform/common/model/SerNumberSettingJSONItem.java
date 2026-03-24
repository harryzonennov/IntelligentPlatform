package com.company.IntelligentPlatform.common.model;


public class SerNumberSettingJSONItem {

	protected String fieldName;

	protected String modelName;

	protected int startPos;

	protected int endPos;

	protected String back;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getStartPos() {
		return startPos;
	}

	public void setStartPos(final int startPos) {
		this.startPos = startPos;
	}

	public int getEndPos() {
		return endPos;
	}

	public void setEndPos(final int endPos) {
		this.endPos = endPos;
	}

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}
}
