package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.ServiceDocActConfigureItem;

public class ServiceDocActConfigureItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ServiceDocActConfigureItem.NODENAME, nodeInstId = ServiceDocActConfigureItem.NODENAME)
	protected ServiceDocActConfigureItem serviceDocActConfigureItem;

	public ServiceDocActConfigureItem getServiceDocActConfigureItem() {
		return serviceDocActConfigureItem;
	}

	public void setServiceDocActConfigureItem(ServiceDocActConfigureItem serviceDocActConfigureItem) {
		this.serviceDocActConfigureItem = serviceDocActConfigureItem;
	}
}
