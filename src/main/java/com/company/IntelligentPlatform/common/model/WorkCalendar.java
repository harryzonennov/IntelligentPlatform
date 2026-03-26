package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.service.StandardSystemDefaultProxy;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
@Entity
@Table(name = "WorkCalendar", schema = "platform")
public class WorkCalendar extends ServiceEntityNode{

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.WorkCalendar;

	public static final int STATUS_INIT = 1;

	public static final int STATUS_ACTIVE = 2;

	public static final int STATUS_ARCHIVE = 3;

	protected int year;

	protected int defaultFlag;

	protected String refTemplateUUID;

	protected int status;

	public WorkCalendar(){
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.defaultFlag = StandardSystemDefaultProxy.DEFAULT_OFF;
		this.status = STATUS_INIT;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(int defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getRefTemplateUUID() {
		return refTemplateUUID;
	}

	public void setRefTemplateUUID(String refTemplateUUID) {
		this.refTemplateUUID = refTemplateUUID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
