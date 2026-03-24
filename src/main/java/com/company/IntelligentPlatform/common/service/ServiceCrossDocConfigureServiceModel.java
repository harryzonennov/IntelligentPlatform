package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.*;

import java.util.ArrayList;
import java.util.List;

public class ServiceCrossDocConfigureServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ServiceCrossDocConfigure.NODENAME, nodeInstId = ServiceCrossDocConfigure.NODENAME)
	protected ServiceCrossDocConfigure serviceCrossDocConfigure;

	@IServiceModuleFieldConfig(nodeName = ServiceCrossDocEventMonitor.NODENAME, nodeInstId =
			ServiceCrossDocEventMonitor.NODENAME)
	protected List<ServiceCrossDocEventMonitorServiceModel> serviceCrossDocEventMonitorList = new ArrayList<>();

	public ServiceCrossDocConfigure getServiceCrossDocConfigure() {
		return serviceCrossDocConfigure;
	}

	public void setServiceCrossDocConfigure(ServiceCrossDocConfigure serviceCrossDocConfigure) {
		this.serviceCrossDocConfigure = serviceCrossDocConfigure;
	}

	public List<ServiceCrossDocEventMonitorServiceModel> getServiceCrossDocEventMonitorList() {
		return serviceCrossDocEventMonitorList;
	}

	public void setServiceCrossDocEventMonitorList(List<ServiceCrossDocEventMonitorServiceModel> serviceCrossDocEventMonitorList) {
		this.serviceCrossDocEventMonitorList = serviceCrossDocEventMonitorList;
	}
}
