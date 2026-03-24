package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;
import com.company.IntelligentPlatform.common.model.SystemCodeValueUnion;

public class SystemCodeValueCollectionServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SystemCodeValueCollection.NODENAME, nodeInstId = SystemCodeValueCollection.SENAME)
	protected SystemCodeValueCollection systemCodeValueCollection;

	@IServiceModuleFieldConfig(nodeName = SystemCodeValueUnion.NODENAME, nodeInstId = SystemCodeValueUnion.NODENAME)
	protected List<SystemCodeValueUnionServiceModel> systemCodeValueUnionList = new ArrayList<>();

	public SystemCodeValueCollection getSystemCodeValueCollection() {
		return this.systemCodeValueCollection;
	}

	public void setSystemCodeValueCollection(
			SystemCodeValueCollection systemCodeValueCollection) {
		this.systemCodeValueCollection = systemCodeValueCollection;
	}

	public List<SystemCodeValueUnionServiceModel> getSystemCodeValueUnionList() {
		return this.systemCodeValueUnionList;
	}

	public void setSystemCodeValueUnionList(
			List<SystemCodeValueUnionServiceModel> systemCodeValueUnionList) {
		this.systemCodeValueUnionList = systemCodeValueUnionList;
	}

}
