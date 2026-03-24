package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.ServiceCrossDocEventMonitor;

@Component
public class ServiceCrossDocEventMonitorServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ServiceCrossDocEventMonitor.NODENAME, nodeInstId = ServiceCrossDocEventMonitor.NODENAME)
	protected ServiceCrossDocEventMonitorUIModel serviceCrossDocEventMonitorUIModel;

	public ServiceCrossDocEventMonitorUIModel getServiceCrossDocEventMonitorUIModel() {
		return serviceCrossDocEventMonitorUIModel;
	}

	public void setServiceCrossDocEventMonitorUIModel(ServiceCrossDocEventMonitorUIModel serviceCrossDocEventMonitorUIModel) {
		this.serviceCrossDocEventMonitorUIModel = serviceCrossDocEventMonitorUIModel;
	}
}
