package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.ServiceCrossDocEventMonitor;

public class ServiceCrossDocEventMonitorServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ServiceCrossDocEventMonitor.NODENAME, nodeInstId = ServiceCrossDocEventMonitor.NODENAME)
	protected ServiceCrossDocEventMonitor serviceCrossDocEventMonitor;

	public ServiceCrossDocEventMonitor getServiceCrossDocEventMonitor() {
		return serviceCrossDocEventMonitor;
	}

	public void setServiceCrossDocEventMonitor(ServiceCrossDocEventMonitor serviceCrossDocEventMonitor) {
		this.serviceCrossDocEventMonitor = serviceCrossDocEventMonitor;
	}
}
