package com.company.IntelligentPlatform.production.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class ProdWorkCenterCalendarItem extends ReferenceNode {

	public final static String NODENAME = IServiceModelConstants.ProdWorkCenterCalendarItem;

	public final static String SENAME = ProdWorkCenter.SENAME;

	protected Date startDate;

	protected Date endDate;

	protected double occupyRate;

	protected int taskType;

	protected boolean seriesFlag;

	protected String occupyResources;

    protected Date seriesStartDate;

	protected Date seriesEndDate;

	public ProdWorkCenterCalendarItem(){
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		occupyRate = 100;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getOccupyRate() {
		return occupyRate;
	}

	public void setOccupyRate(double occupyRate) {
		this.occupyRate = occupyRate;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public boolean getSeriesFlag() {
		return seriesFlag;
	}

	public void setSeriesFlag(boolean seriesFlag) {
		this.seriesFlag = seriesFlag;
	}

	public String getOccupyResources() {
		return occupyResources;
	}

	public void setOccupyResources(String occupyResources) {
		this.occupyResources = occupyResources;
	}

	public Date getSeriesStartDate() {
		return seriesStartDate;
	}

	public void setSeriesStartDate(Date seriesStartDate) {
		this.seriesStartDate = seriesStartDate;
	}

	public Date getSeriesEndDate() {
		return seriesEndDate;
	}

	public void setSeriesEndDate(Date seriesEndDate) {
		this.seriesEndDate = seriesEndDate;
	}

}
