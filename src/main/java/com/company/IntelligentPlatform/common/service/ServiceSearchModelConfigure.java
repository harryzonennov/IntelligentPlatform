package com.company.IntelligentPlatform.common.service;

public class ServiceSearchModelConfigure {
	
	protected String searchModelName;
	
	protected String serviceManagerName;
	
	protected String searchModel;

	public ServiceSearchModelConfigure(String searchModelName,
			String serviceManagerName, String searchModel) {
		super();
		this.searchModelName = searchModelName;
		this.serviceManagerName = serviceManagerName;
		this.searchModel = searchModel;
	}

	public String getSearchModelName() {
		return searchModelName;
	}

	public void setSearchModelName(String searchModelName) {
		this.searchModelName = searchModelName;
	}

	public String getServiceManagerName() {
		return serviceManagerName;
	}

	public void setServiceManagerName(String serviceManagerName) {
		this.serviceManagerName = serviceManagerName;
	}

	public String getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(String searchModel) {
		this.searchModel = searchModel;
	}

}
