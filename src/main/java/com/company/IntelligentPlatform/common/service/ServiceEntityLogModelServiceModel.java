package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogItem;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ServiceEntityLogModelServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ServiceEntityLogModel.NODENAME, nodeInstId = ServiceEntityLogModel.SENAME)
	protected ServiceEntityLogModel serviceEntityLogModel;

	@IServiceModuleFieldConfig(nodeName = ServiceEntityLogItem.NODENAME, nodeInstId = ServiceEntityLogItem.NODENAME)
	protected List<ServiceEntityNode> serviceEntityLogItemList = new ArrayList<>();

	public List<ServiceEntityNode> getServiceEntityLogItemList() {
		return this.serviceEntityLogItemList;
	}

	public void setServiceEntityLogItemList(
			List<ServiceEntityNode> serviceEntityLogItemList) {
		this.serviceEntityLogItemList = serviceEntityLogItemList;
	}

	public ServiceEntityLogModel getServiceEntityLogModel() {
		return this.serviceEntityLogModel;
	}

	public void setServiceEntityLogModel(
			ServiceEntityLogModel serviceEntityLogModel) {
		this.serviceEntityLogModel = serviceEntityLogModel;
	}

}
