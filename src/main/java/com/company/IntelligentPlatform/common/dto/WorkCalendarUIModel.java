package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class WorkCalendarUIModel extends SEUIComModel {

	
	protected int defaultFlag;
	
	protected String defaultFlagValue;

	protected int year;
	
	protected String refTemplateUUID;

	protected String templateName;
	
	protected String templateId;

	@ISEDropDownResourceMapping(resouceMapping = "WorkCalendar_status", valueFieldName = "")
	protected int status;

	protected String statusValue;

	public int getDefaultFlag() {
		return this.defaultFlag;
	}

	public void setDefaultFlag(int defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getDefaultFlagValue() {
		return defaultFlagValue;
	}

	public void setDefaultFlagValue(String defaultFlagValue) {
		this.defaultFlagValue = defaultFlagValue;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getRefTemplateUUID() {
		return this.refTemplateUUID;
	}

	public void setRefTemplateUUID(String refTemplateUUID) {
		this.refTemplateUUID = refTemplateUUID;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

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

}
