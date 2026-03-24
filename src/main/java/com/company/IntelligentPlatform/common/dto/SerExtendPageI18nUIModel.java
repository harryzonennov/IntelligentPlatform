package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class SerExtendPageI18nUIModel extends SEUIComModel {

	protected String lanCode;

	protected String propertyContent;

	public String getLanCode() {
		return lanCode;
	}

	public void setLanCode(String lanCode) {
		this.lanCode = lanCode;
	}

	public String getPropertyContent() {
		return propertyContent;
	}

	public void setPropertyContent(String propertyContent) {
		this.propertyContent = propertyContent;
	}
}
