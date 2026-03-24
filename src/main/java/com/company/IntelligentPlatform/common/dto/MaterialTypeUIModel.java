package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class MaterialTypeUIModel extends SEUIComModel {

	public static final String SEC_PARENTTYPE = "parentType";
	
	public static final String SEC_ROOTTYPE = "rootType";
	/**
	 * Section:[materialType]
	 **/
	
	protected int status;
	
	protected String statusValue;
	
	protected int systemStandardCategory;
	
	protected String systemStandardCategoryValue;

	protected String parentTypeUUID;
	
	protected String parentTypeId;
	
	protected String parentTypeName;

	protected String rootTypeUUID;
	
	protected String rootTypeId;
	
	protected String rootTypeName;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public int getSystemStandardCategory() {
		return systemStandardCategory;
	}

	public void setSystemStandardCategory(int systemStandardCategory) {
		this.systemStandardCategory = systemStandardCategory;
	}

	public String getSystemStandardCategoryValue() {
		return systemStandardCategoryValue;
	}

	public void setSystemStandardCategoryValue(String systemStandardCategoryValue) {
		this.systemStandardCategoryValue = systemStandardCategoryValue;
	}

	public String getParentTypeUUID() {
		return parentTypeUUID;
	}

	public void setParentTypeUUID(String parentTypeUUID) {
		this.parentTypeUUID = parentTypeUUID;
	}

	public String getParentTypeName() {
		return parentTypeName;
	}

	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}

	public String getRootTypeUUID() {
		return rootTypeUUID;
	}

	public void setRootTypeUUID(String rootTypeUUID) {
		this.rootTypeUUID = rootTypeUUID;
	}

	public String getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(String parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	public String getRootTypeId() {
		return rootTypeId;
	}

	public void setRootTypeId(String rootTypeId) {
		this.rootTypeId = rootTypeId;
	}

	public String getRootTypeName() {
		return rootTypeName;
	}

	public void setRootTypeName(String rootTypeName) {
		this.rootTypeName = rootTypeName;
	}

}
