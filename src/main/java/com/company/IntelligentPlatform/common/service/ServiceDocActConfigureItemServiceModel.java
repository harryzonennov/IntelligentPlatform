package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.ServiceDocActConfigureItem;

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
