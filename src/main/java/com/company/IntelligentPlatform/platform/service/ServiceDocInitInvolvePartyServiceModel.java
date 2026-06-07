package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.ServiceDocInitInvolveParty;

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
