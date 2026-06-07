package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.controller.SEUIComModel;

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
