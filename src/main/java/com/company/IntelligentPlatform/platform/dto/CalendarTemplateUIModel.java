package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.platform.controller.SEUIComModel;

public class CalendarTemplateUIModel extends SEUIComModel {

	protected int year;
	
	@ISEDropDownResourceMapping(resouceMapping = "CalendarTemplate_status", valueFieldName = "")
	protected int status;
	
	protected String statusValue;

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
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
