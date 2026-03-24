package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.ServiceDocInitInvolveParty;

public class ServiceDocInitInvolvePartyServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ServiceDocInitInvolveParty.NODENAME, nodeInstId = ServiceDocInitInvolveParty.NODENAME)
	protected ServiceDocInitInvolveParty serviceDocServiceDocInitInvolveParty;

	public ServiceDocInitInvolveParty getServiceDocInitInvolveParty() {
		return serviceDocServiceDocInitInvolveParty;
	}

	public void setServiceDocInitInvolveParty(ServiceDocInitInvolveParty serviceDocServiceDocInitInvolveParty) {
		this.serviceDocServiceDocInitInvolveParty = serviceDocServiceDocInitInvolveParty;
	}
}
