package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.platform.controller.SEUIComModel;
import com.company.IntelligentPlatform.platform.model.ServiceDocumentSetting;

public class ServiceDocumentSettingUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "documentType", seName = ServiceDocumentSetting.SENAME, nodeName = ServiceDocumentSetting.NODENAME, nodeInstID = ServiceDocumentSetting.SENAME, tabId = TABID_BASIC)
	protected String documentType;
	
	protected String documentTypeValue;
	
	protected String validToDate;

	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentTypeValue() {
		return documentTypeValue;
	}

	public void setDocumentTypeValue(String documentTypeValue) {
		this.documentTypeValue = documentTypeValue;
	}

	public String getValidToDate() {
		return validToDate;
	}

	public void setValidToDate(String validToDate) {
		this.validToDate = validToDate;
	}

}
