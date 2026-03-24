package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.ServiceDocActionConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocActConfigureItem;

import java.util.ArrayList;
import java.util.List;

public class ServiceDocActionConfigureServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ServiceDocActionConfigure.NODENAME, nodeInstId = ServiceDocActionConfigure.NODENAME)
	protected ServiceDocActionConfigure serviceDocActionConfigure;

	@IServiceModuleFieldConfig(nodeName = ServiceDocActConfigureItem.NODENAME, nodeInstId =
			ServiceDocActConfigureItem.NODENAME)
	protected List<ServiceDocActConfigureItemServiceModel> serviceDocActConfigureItemList = new ArrayList<>();

	public ServiceDocActionConfigure getServiceDocActionConfigure() {
		return serviceDocActionConfigure;
	}

	public void setServiceDocActionConfigure(ServiceDocActionConfigure serviceDocActionConfigure) {
		this.serviceDocActionConfigure = serviceDocActionConfigure;
	}

	public List<ServiceDocActConfigureItemServiceModel> getServiceDocActConfigureItemList() {
		return serviceDocActConfigureItemList;
	}

	public void setServiceDocActConfigureItemList(List<ServiceDocActConfigureItemServiceModel> serviceDocActConfigureItemList) {
		this.serviceDocActConfigureItemList = serviceDocActConfigureItemList;
	}

}
