package com.company.IntelligentPlatform.common.model;

import java.util.Date;

public class ServiceJSONRequest extends SimpleSEJSONRequest{

	public static final String MODELID_REQUEST = "ServiceJSONRequest";
	
	protected Date executeDate;
	
	protected String dateMinString;

	protected int targetActionCode;

	public ServiceJSONRequest() {
		this.targetActionCode = 0;
	}

	public ServiceJSONRequest(String uuid, String baseUUID) {
		super(uuid, baseUUID);
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public String getDateMinString() {
		return dateMinString;
	}

	public void setDateMinString(String dateMinString) {
		this.dateMinString = dateMinString;
	}

	public int getTargetActionCode() {
		return targetActionCode;
	}

	public void setTargetActionCode(int targetActionCode) {
		this.targetActionCode = targetActionCode;
	}
}
