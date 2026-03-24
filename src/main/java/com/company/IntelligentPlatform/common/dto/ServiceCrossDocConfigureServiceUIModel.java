package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.*;

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
