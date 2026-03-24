package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigurePara;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigureParaGroup;

public class ServiceDocConfigureServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ServiceDocConfigure.NODENAME, nodeInstId = ServiceDocConfigure.SENAME)
	protected ServiceDocConfigure serviceDocConfigure;

	@IServiceModuleFieldConfig(nodeName = ServiceDocConfigurePara.NODENAME, nodeInstId = ServiceDocConfigurePara.NODENAME)
	protected List<ServiceEntityNode> serviceDocConfigureParaList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ServiceDocConfigureParaGroup.NODENAME, nodeInstId = ServiceDocConfigureParaGroup.NODENAME)
	protected List<ServiceEntityNode> serviceDocConfigureParaGroupList = new ArrayList<>();

	public ServiceDocConfigure getServiceDocConfigure() {
		return this.serviceDocConfigure;
	}

	public void setServiceDocConfigure(ServiceDocConfigure serviceDocConfigure) {
		this.serviceDocConfigure = serviceDocConfigure;
	}

	public List<ServiceEntityNode> getServiceDocConfigureParaList() {
		return this.serviceDocConfigureParaList;
	}

	public void setServiceDocConfigureParaList(
			List<ServiceEntityNode> serviceDocConfigureParaList) {
		this.serviceDocConfigureParaList = serviceDocConfigureParaList;
	}

	public List<ServiceEntityNode> getServiceDocConfigureParaGroupList() {
		return this.serviceDocConfigureParaGroupList;
	}

	public void setServiceDocConfigureParaGroupList(
			List<ServiceEntityNode> serviceDocConfigureParaGroupList) {
		this.serviceDocConfigureParaGroupList = serviceDocConfigureParaGroupList;
	}

}
