package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "CalendarSetting", schema = "platform")
public class CalendarSetting extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.CalendarSetting;
	
	protected boolean freeMondayFlag;
	
	protected boolean freeTuesdayFlag;
	
	protected boolean freeWednesdayFlag;
	
	protected boolean freeThursdayFlag;
	
	protected boolean freeFridayFlag;
	
	protected boolean freeSaturdayFlag;
	
	protected boolean freeSundayFlag;
	
	public CalendarSetting() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;	
		this.freeSaturdayFlag = true;
		this.freeMondayFlag = true;
	}

	public boolean getFreeMondayFlag() {
		return freeMondayFlag;
	}

	public void setFreeMondayFlag(boolean freeMondayFlag) {
		this.freeMondayFlag = freeMondayFlag;
	}

	public boolean getFreeTuesdayFlag() {
		return freeTuesdayFlag;
	}

	public void setFreeTuesdayFlag(boolean freeTuesdayFlag) {
		this.freeTuesdayFlag = freeTuesdayFlag;
	}

	public boolean getFreeWednesdayFlag() {
		return freeWednesdayFlag;
	}

	public void setFreeWednesdayFlag(boolean freeWednesdayFlag) {
		this.freeWednesdayFlag = freeWednesdayFlag;
	}

	public boolean getFreeThursdayFlag() {
		return freeThursdayFlag;
	}

	public void setFreeThursdayFlag(boolean freeThursdayFlag) {
		this.freeThursdayFlag = freeThursdayFlag;
	}

	public boolean getFreeFridayFlag() {
		return freeFridayFlag;
	}

	public void setFreeFridayFlag(boolean freeFridayFlag) {
		this.freeFridayFlag = freeFridayFlag;
	}

	public boolean getFreeSaturdayFlag() {
		return freeSaturdayFlag;
	}

	public void setFreeSaturdayFlag(boolean freeSaturdayFlag) {
		this.freeSaturdayFlag = freeSaturdayFlag;
	}

	public boolean getFreeSundayFlag() {
		return freeSundayFlag;
	}

	public void setFreeSundayFlag(boolean freeSundayFlag) {
		this.freeSundayFlag = freeSundayFlag;
	}
	
	
	

}
