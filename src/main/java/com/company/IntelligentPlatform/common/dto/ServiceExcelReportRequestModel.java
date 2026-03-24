package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceExcelReportRequestModel extends SEUIComModel{
	
	protected String modelName;
	
	protected String modelLabel;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelLabel() {
		return modelLabel;
	}

	public void setModelLabel(String modelLabel) {
		this.modelLabel = modelLabel;
	}

}
