package com.company.IntelligentPlatform.common.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class WorkCalendarDayItem extends ServiceEntityNode{

	public static final String SENAME = WorkCalendar.SENAME;

	public static final String NODENAME = IServiceModelConstants.WorkCalendarDayItem;

	public static final int DAYSTATUS_VOCATION = 1;

	public static final int DAYSTATUS_WORK = 2;

	public static final int VOCATIONTYPE_WEEKEND = 1;

	public static final int VOCATIONTYPE_PUBLICHOLIDAY = 2;

	public WorkCalendarDayItem(){
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.dayStatus = WorkCalendarDayItem.DAYSTATUS_VOCATION;
		this.vocationType = WorkCalendarDayItem.VOCATIONTYPE_WEEKEND;
	}

	protected Date refDate;

	protected int dayStatus;

	protected int vocationType;

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public int getDayStatus() {
		return dayStatus;
	}

	public void setDayStatus(int dayStatus) {
		this.dayStatus = dayStatus;
	}

	public int getVocationType() {
		return vocationType;
	}

	public void setVocationType(int vocationType) {
		this.vocationType = vocationType;
	}

}
