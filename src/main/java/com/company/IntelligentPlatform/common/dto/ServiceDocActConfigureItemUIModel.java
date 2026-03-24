package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceDocActConfigureItemUIModel extends SEUIComModel {

	protected String[] preStatus;

	protected String[] preStatusValue;

	protected int targetStatus;

	protected String targetStatusValue;

	protected String authorAction;

	protected int actionCode;

	protected String actionCodeValue;

	protected String documentType;

	protected String documentTypeValue;

	public String[] getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String[] preStatus) {
		this.preStatus = preStatus;
	}

	public String[] getPreStatusValue() {
		return preStatusValue;
	}

	public void setPreStatusValue(String[] preStatusValue) {
		this.preStatusValue = preStatusValue;
	}

	public int getTargetStatus() {
		return targetStatus;
	}

	public void setTargetStatus(int targetStatus) {
		this.targetStatus = targetStatus;
	}

	public String getTargetStatusValue() {
		return targetStatusValue;
	}

	public void setTargetStatusValue(String targetStatusValue) {
		this.targetStatusValue = targetStatusValue;
	}

	public String getAuthorAction() {
		return authorAction;
	}

	public void setAuthorAction(String authorAction) {
		this.authorAction = authorAction;
	}

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	public String getActionCodeValue() {
		return actionCodeValue;
	}

	public void setActionCodeValue(String actionCodeValue) {
		this.actionCodeValue = actionCodeValue;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentTypeValue() {
		return documentTypeValue;
	}

	public void setDocumentTypeValue(String documentTypeValue) {
		this.documentTypeValue = documentTypeValue;
	}
}
