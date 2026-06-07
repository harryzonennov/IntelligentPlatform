package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.ServiceCrossDocEventMonitor;

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
