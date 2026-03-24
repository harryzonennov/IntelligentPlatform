package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceDocConsumerUnionUIModel extends SEUIComModel {

	protected String i18nFullPath;
	
	protected String uiModelType;
	
	protected String uiModelTypeFullName;

	public String getUiModelType() {
		return uiModelType;
	}

	public void setUiModelType(String uiModelType) {
		this.uiModelType = uiModelType;
	}

	public String getUiModelTypeFullName() {
		return uiModelTypeFullName;
	}

	public void setUiModelTypeFullName(String uiModelTypeFullName) {
		this.uiModelTypeFullName = uiModelTypeFullName;
	}

	public String getI18nFullPath() {
		return i18nFullPath;
	}

	public void setI18nFullPath(String i18nFullPath) {
		this.i18nFullPath = i18nFullPath;
	}

}
