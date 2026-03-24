package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class SearchFieldConfigUIModel extends SEUIComModel {
	
	protected String fieldName;
	
	protected String refSelectURL;
	
	@ISEDropDownResourceMapping(resouceMapping = "SearchFieldConfig_refSelectType", valueFieldName = "")
	protected int refSelectType;
	
	protected String refSelectTypeValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "SearchFieldConfig_category", valueFieldName = "")
	protected int category;
	
	protected String categoryValue;

	protected int checkFieldStatus;

	protected String checkFieldStatusValue;
	
	protected String proxyId;

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getRefSelectURL() {
		return this.refSelectURL;
	}

	public void setRefSelectURL(String refSelectURL) {
		this.refSelectURL = refSelectURL;
	}

	public int getRefSelectType() {
		return this.refSelectType;
	}

	public void setRefSelectType(int refSelectType) {
		this.refSelectType = refSelectType;
	}

	public String getRefSelectTypeValue() {
		return refSelectTypeValue;
	}

	public void setRefSelectTypeValue(String refSelectTypeValue) {
		this.refSelectTypeValue = refSelectTypeValue;
	}

	public int getCategory() {
		return this.category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getProxyId() {
		return proxyId;
	}

	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public int getCheckFieldStatus() {
		return checkFieldStatus;
	}

	public void setCheckFieldStatus(int checkFieldStatus) {
		this.checkFieldStatus = checkFieldStatus;
	}

	public String getCheckFieldStatusValue() {
		return checkFieldStatusValue;
	}

	public void setCheckFieldStatusValue(String checkFieldStatusValue) {
		this.checkFieldStatusValue = checkFieldStatusValue;
	}
}
