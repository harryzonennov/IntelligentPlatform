package com.company.IntelligentPlatform.common.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class CalendarWorkTimeSetting extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.CalendarWorkTimeSetting;

	public static final String SENAME = CalendarSetting.SENAME;
	
	public static final int DAILYSHIFT1 = 1;
	
	public static final int DAILYSHIFT2 = 2;
	
	public static final int DAILYSHIFT3 = 3;
	
	public static final int DAILYSHIFT4 = 4;

	protected int dailyShift;

	protected Date startDate1;

	protected Date endDate1;

	protected Date startDate2;

	protected Date endDate2;

	protected Date startDate3;

	protected Date endDate3;

	protected Date startDate4;

	protected Date endDate4;

	public CalendarWorkTimeSetting() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.dailyShift = DAILYSHIFT1;
	}

	public int getDailyShift() {
		return dailyShift;
	}

	public void setDailyShift(int dailyShift) {
		this.dailyShift = dailyShift;
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
	

}
