package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceCrossDocConfigureServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ServiceCrossDocConfigure.NODENAME, nodeInstId = ServiceCrossDocConfigure.NODENAME)
	protected ServiceCrossDocConfigureUIModel serviceCrossDocConfigureUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ServiceCrossDocEventMonitor.NODENAME, nodeInstId =
			ServiceCrossDocEventMonitor.NODENAME)
	protected List<ServiceCrossDocEventMonitorServiceUIModel> serviceCrossDocEventMonitorUIModelList = new ArrayList<>();

	public ServiceCrossDocConfigureUIModel getServiceCrossDocConfigureUIModel() {
		return serviceCrossDocConfigureUIModel;
	}

	public void setServiceCrossDocConfigureUIModel(ServiceCrossDocConfigureUIModel serviceCrossDocConfigureUIModel) {
		this.serviceCrossDocConfigureUIModel = serviceCrossDocConfigureUIModel;
	}

	public List<ServiceCrossDocEventMonitorServiceUIModel> getServiceCrossDocEventMonitorUIModelList() {
		return serviceCrossDocEventMonitorUIModelList;
	}

	public void setServiceCrossDocEventMonitorUIModelList(
			List<ServiceCrossDocEventMonitorServiceUIModel> serviceCrossDocEventMonitorUIModelList) {
		this.serviceCrossDocEventMonitorUIModelList = serviceCrossDocEventMonitorUIModelList;
	}
}
