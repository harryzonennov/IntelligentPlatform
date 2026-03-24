package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class CalendarTemplateItemUIModel extends SEUIComModel {

	protected int startMonth;

	protected int lastDays;

	@ISEDropDownResourceMapping(resouceMapping = "CalendarTemplateItem_periodType", valueFieldName = "periodTypeValue")
	protected int periodType;
	
	protected String periodTypeValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "CalendarTemplateItem_vocationType", valueFieldName = "vocationTypeValue")
	protected int vocationType;
	
	protected String vocationTypeValue;

	protected int startDay;
	
	@ISEDropDownResourceMapping(resouceMapping = "CalendarTemplateItem_dayStatus", valueFieldName = "dayStatusValue")
	protected int dayStatus;
	
	protected String dayStatusValue;

	public int getStartMonth() {
		return this.startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public int getLastDays() {
		return this.lastDays;
	}

	public void setLastDays(int lastDays) {
		this.lastDays = lastDays;
	}

	public int getPeriodType() {
		return this.periodType;
	}

	public void setPeriodType(int periodType) {
		this.periodType = periodType;
	}

	public int getVocationType() {
		return this.vocationType;
	}

	public void setVocationType(int vocationType) {
		this.vocationType = vocationType;
	}

	public int getStartDay() {
		return this.startDay;
	}

	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}

	public int getDayStatus() {
		return this.dayStatus;
	}

	public void setDayStatus(int dayStatus) {
		this.dayStatus = dayStatus;
	}

	public String getPeriodTypeValue() {
		return periodTypeValue;
	}

	public void setPeriodTypeValue(String periodTypeValue) {
		this.periodTypeValue = periodTypeValue;
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

}
