package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceCrossDocConfigureUIModel extends SEUIComModel {

	protected String targetDocType;

	protected int crossDocRelationType;

	protected String targetDocTypeValue;

	protected String crossDocRelationTypeValue;

	protected String homeDocType;

	protected String homeDocTypeValue;

	protected String parentDocId;

	protected String parentDocName;

	public String getTargetDocType() {
		return targetDocType;
	}

	public void setTargetDocType(String targetDocType) {
		this.targetDocType = targetDocType;
	}

	public int getCrossDocRelationType() {
		return crossDocRelationType;
	}

	public void setCrossDocRelationType(int crossDocRelationType) {
		this.crossDocRelationType = crossDocRelationType;
	}

	public String getTargetDocTypeValue() {
		return targetDocTypeValue;
	}

	public void setTargetDocTypeValue(String targetDocTypeValue) {
		this.targetDocTypeValue = targetDocTypeValue;
	}

	public String getCrossDocRelationTypeValue() {
		return crossDocRelationTypeValue;
	}

	public void setCrossDocRelationTypeValue(String crossDocRelationTypeValue) {
		this.crossDocRelationTypeValue = crossDocRelationTypeValue;
	}

	public String getHomeDocType() {
		return homeDocType;
	}

	public void setHomeDocType(String homeDocType) {
		this.homeDocType = homeDocType;
	}

	public String getHomeDocTypeValue() {
		return homeDocTypeValue;
	}

	public void setHomeDocTypeValue(String homeDocTypeValue) {
		this.homeDocTypeValue = homeDocTypeValue;
	}

	public String getParentDocId() {
		return parentDocId;
	}

	public void setParentDocId(String parentDocId) {
		this.parentDocId = parentDocId;
	}

	public String getParentDocName() {
		return parentDocName;
	}

	public void setParentDocName(String parentDocName) {
		this.parentDocName = parentDocName;
	}
}
