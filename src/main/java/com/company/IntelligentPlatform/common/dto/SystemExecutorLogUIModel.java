package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class SystemExecutorLogUIModel extends SEUIComModel {

	@ISEDropDownResourceMapping(resouceMapping = "SystemExecutorLog_result", valueFieldName = "resultValue")
	protected int result;
	
	protected String resultValue;
	
	protected String createdBy;
	
	protected String refLogonUserId;
	
	protected String refLogonUserName;
	
	protected String executedDate;

	public int getResult() {
		return this.result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public String getRefLogonUserId() {
		return refLogonUserId;
	}

	public void setRefLogonUserId(String refLogonUserId) {
		this.refLogonUserId = refLogonUserId;
	}

	public String getRefLogonUserName() {
		return refLogonUserName;
	}

	public void setRefLogonUserName(String refLogonUserName) {
		this.refLogonUserName = refLogonUserName;
	}

	public String getExecutedDate() {
		return executedDate;
	}

	public void setExecutedDate(String executedDate) {
		this.executedDate = executedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
