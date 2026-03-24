package com.company.IntelligentPlatform.common.dto;

public class HostCompanyUIModel extends OrganizationUIModel {
	
	protected String fullName;
	
	protected String comReportTitle;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getComReportTitle() {
		return comReportTitle;
	}

	public void setComReportTitle(String comReportTitle) {
		this.comReportTitle = comReportTitle;
	}

}
