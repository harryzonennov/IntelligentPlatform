package com.company.IntelligentPlatform.common.dto;

import java.util.Date;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.CalendarWorkTimeSetting;

public class CalendarWorkTimeSettingUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "dailyShift", seName = CalendarWorkTimeSetting.SENAME, nodeName = CalendarWorkTimeSetting.NODENAME, nodeInstID = CalendarWorkTimeSetting.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "CalendarWorkTimeSetting_dailyShift", valueFieldName = "dailyShiftValue")
	protected int dailyShift;
	
	protected String dailyShiftValue;
	
	@ISEUIModelMapping(fieldName = "startDate1", seName = CalendarWorkTimeSetting.SENAME, nodeName = CalendarWorkTimeSetting.NODENAME, nodeInstID = CalendarWorkTimeSetting.NODENAME)
	protected Date startDate1;
	
	@ISEUIModelMapping(fieldName = "endDate1", seName = CalendarWorkTimeSetting.SENAME, nodeName = CalendarWorkTimeSetting.NODENAME, nodeInstID = CalendarWorkTimeSetting.NODENAME)
	protected Date endDate1;
	
	@ISEUIModelMapping(fieldName = "startDate2", seName = CalendarWorkTimeSetting.SENAME, nodeName = CalendarWorkTimeSetting.NODENAME, nodeInstID = CalendarWorkTimeSetting.NODENAME)
	protected Date startDate2;

	@ISEUIModelMapping(fieldName = "endDate2", seName = CalendarWorkTimeSetting.SENAME, nodeName = CalendarWorkTimeSetting.NODENAME, nodeInstID = CalendarWorkTimeSetting.NODENAME)
	protected Date endDate2;

	@ISEUIModelMapping(fieldName = "startDate3", seName = CalendarWorkTimeSetting.SENAME, nodeName = CalendarWorkTimeSetting.NODENAME, nodeInstID = CalendarWorkTimeSetting.NODENAME)
	protected Date startDate3;

	@ISEUIModelMapping(fieldName = "endDate3", seName = CalendarWorkTimeSetting.SENAME, nodeName = CalendarWorkTimeSetting.NODENAME, nodeInstID = CalendarWorkTimeSetting.NODENAME)
	protected Date endDate3;

	@ISEUIModelMapping(fieldName = "startDate4", seName = CalendarWorkTimeSetting.SENAME, nodeName = CalendarWorkTimeSetting.NODENAME, nodeInstID = CalendarWorkTimeSetting.NODENAME)
	protected Date startDate4;

	@ISEUIModelMapping(fieldName = "endDate4", seName = CalendarWorkTimeSetting.SENAME, nodeName = CalendarWorkTimeSetting.NODENAME, nodeInstID = CalendarWorkTimeSetting.NODENAME)
	protected Date endDate4;
	
	protected String workTimePeriod;

	public int getDailyShift() {
		return dailyShift;
	}

	public void setDailyShift(int dailyShift) {
		this.dailyShift = dailyShift;
	}

	public String getDailyShiftValue() {
		return dailyShiftValue;
	}

	public void setDailyShiftValue(String dailyShiftValue) {
		this.dailyShiftValue = dailyShiftValue;
	}

	public Date getStartDate1() {
		return startDate1;
	}

	public void setStartDate1(Date startDate1) {
		this.startDate1 = startDate1;
	}

	public Date getEndDate1() {
		return endDate1;
	}

	public void setEndDate1(Date endDate1) {
		this.endDate1 = endDate1;
	}

	public Date getStartDate2() {
		return startDate2;
	}

	public void setStartDate2(Date startDate2) {
		this.startDate2 = startDate2;
	}

	public Date getEndDate2() {
		return endDate2;
	}

	public void setEndDate2(Date endDate2) {
		this.endDate2 = endDate2;
	}

	public Date getStartDate3() {
		return startDate3;
	}

	public void setStartDate3(Date startDate3) {
		this.startDate3 = startDate3;
	}

	public Date getEndDate3() {
		return endDate3;
	}

	public void setEndDate3(Date endDate3) {
		this.endDate3 = endDate3;
	}

	public Date getStartDate4() {
		return startDate4;
	}

	public void setStartDate4(Date startDate4) {
		this.startDate4 = startDate4;
	}

	public Date getEndDate4() {
		return endDate4;
	}

	public void setEndDate4(Date endDate4) {
		this.endDate4 = endDate4;
	}

	public String getWorkTimePeriod() {
		return workTimePeriod;
	}

	public void setWorkTimePeriod(String workTimePeriod) {
		this.workTimePeriod = workTimePeriod;
	}

}
