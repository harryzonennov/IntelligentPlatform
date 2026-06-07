package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Entity
@Table(name = "CalendarTemplateItem", catalog = "platform")
public class CalendarTemplateItem extends ServiceEntityNode{

	public static final String SENAME = CalendarTemplate.SENAME;

	public static final String NODENAME = IServiceModelConstants.CalendarTemplateItem;

	public static final int PERIODTYPE_WEEK = 1;

	public static final int PERIODTYPE_MONTH = 2;

	public static final int PERIODTYPE_QUARTER = 3;

	public static final int PERIODTYPE_YEAR = 4;

	public CalendarTemplateItem(){
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.periodType = PERIODTYPE_WEEK;
		this.dayStatus = WorkCalendarDayItem.DAYSTATUS_VOCATION;
		this.vocationType = WorkCalendarDayItem.VOCATIONTYPE_WEEKEND;
	}

	protected int periodType;

	protected int startDay;

	protected int startMonth;

	protected int lastDays;

	protected int dayStatus;

	protected int vocationType;

	public int getPeriodType() {
		return periodType;
	}

	public void setPeriodType(int periodType) {
		this.periodType = periodType;
	}

	public int getStartDay() {
		return startDay;
	}

	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public int getLastDays() {
		return lastDays;
	}

	public void setLastDays(int lastDays) {
		this.lastDays = lastDays;
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
