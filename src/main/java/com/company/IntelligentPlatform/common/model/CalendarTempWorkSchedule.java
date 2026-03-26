package com.company.IntelligentPlatform.common.model;

import java.sql.Time;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class CalendarTempWorkSchedule extends ServiceEntityNode{

	public static final String SENAME = CalendarTemplate.SENAME;

	public static final String NODENAME = IServiceModelConstants.CalendarTempWorkSchedule;

	public CalendarTempWorkSchedule(){
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	protected Time startTime;

	protected Time endTime;

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

}
