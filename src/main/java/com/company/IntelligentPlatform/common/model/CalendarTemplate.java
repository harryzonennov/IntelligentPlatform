package com.company.IntelligentPlatform.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Entity
@Table(name = "CalendarTemplate", catalog = "platform")
public class CalendarTemplate extends ServiceEntityNode{

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.CalendarTemplate;

	public static final int STATUS_INIT = 1;

	public static final int STATUS_ACTIVE = 2;

	public static final int STATUS_ARCHIVE = 3;

	protected int year;

	protected int status;

	public CalendarTemplate(){
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.status = STATUS_INIT;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
