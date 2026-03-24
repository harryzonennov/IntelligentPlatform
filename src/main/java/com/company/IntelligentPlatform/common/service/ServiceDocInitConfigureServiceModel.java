package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.ServiceDocInitConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocInitInvolveParty;

import java.util.ArrayList;
import java.util.List;

public class ServiceDocInitConfigureServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ServiceDocInitConfigure.NODENAME, nodeInstId = ServiceDocInitConfigure.NODENAME)
	protected ServiceDocInitConfigure serviceDocInitConfigure;

	@IServiceModuleFieldConfig(nodeName = ServiceDocInitInvolveParty.NODENAME, nodeInstId =
			ServiceDocInitInvolveParty.NODENAME)
	protected List<ServiceDocInitInvolvePartyServiceModel> serviceDocServiceDocInitInvolvePartyList = new ArrayList<>();

	public ServiceDocInitConfigure getServiceDocInitConfigure() {
		return serviceDocInitConfigure;
	}

	public void setServiceDocInitConfigure(ServiceDocInitConfigure serviceDocInitConfigure) {
		this.serviceDocInitConfigure = serviceDocInitConfigure;
	}

	public List<ServiceDocInitInvolvePartyServiceModel> getServiceDocInitInvolvePartyList() {
		return serviceDocServiceDocInitInvolvePartyList;
	}

	public void setServiceDocInitInvolvePartyList(List<ServiceDocInitInvolvePartyServiceModel> serviceDocServiceDocInitInvolvePartyList) {
		this.serviceDocServiceDocInitInvolvePartyList = serviceDocServiceDocInitInvolvePartyList;
	}
}
