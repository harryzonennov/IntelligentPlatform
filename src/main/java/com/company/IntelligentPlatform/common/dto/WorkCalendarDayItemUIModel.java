package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class WorkCalendarDayItemUIModel extends SEUIComModel {

	@ISEDropDownResourceMapping(resouceMapping = "WorkCalendarDayItem_vocationType", valueFieldName = "vocationTypeValue")
	protected int vocationType;
	
	protected String vocationTypeValue;
	
	protected String refDate;
	
	@ISEDropDownResourceMapping(resouceMapping = "WorkCalendarDayItem_dayStatus", valueFieldName = "dayStatusValue")
	protected int dayStatus;
	
	protected String dayStatusValue;

	public int getVocationType() {
		return this.vocationType;
	}

	public void setVocationType(int vocationType) {
		this.vocationType = vocationType;
	}

	public String getRefDate() {
		return this.refDate;
	}

	public void setRefDate(String refDate) {
		this.refDate = refDate;
	}

	public int getDayStatus() {
		return this.dayStatus;
	}

	public String getVocationTypeValue() {
		return vocationTypeValue;
	}

	public void setVocationTypeValue(String vocationTypeValue) {
		this.vocationTypeValue = vocationTypeValue;
	}

	public String getDayStatusValue() {
		return dayStatusValue;
	}

	public void setDayStatusValue(String dayStatusValue) {
		this.dayStatusValue = dayStatusValue;
	}

	public void setDayStatus(int dayStatus) {
		this.dayStatus = dayStatus;
	}

}
