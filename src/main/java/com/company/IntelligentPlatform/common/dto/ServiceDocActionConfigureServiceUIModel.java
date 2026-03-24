package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.ServiceDocActionConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocActConfigureItem;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceDocActionConfigureServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocActionConfigure.NODENAME, nodeInstId = ServiceDocActionConfigure.NODENAME)
	protected ServiceDocActionConfigureUIModel serviceDocActionConfigureUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocActConfigureItem.NODENAME, nodeInstId =
			ServiceDocActConfigureItem.NODENAME)
	protected List<ServiceDocActConfigureItemServiceUIModel> serviceDocActConfigureItemUIModelList = new ArrayList<>();

	public ServiceDocActionConfigureUIModel getServiceDocActionConfigureUIModel() {
		return serviceDocActionConfigureUIModel;
	}

	public void setServiceDocActionConfigureUIModel(ServiceDocActionConfigureUIModel serviceDocActionConfigureUIModel) {
		this.serviceDocActionConfigureUIModel = serviceDocActionConfigureUIModel;
	}

	public List<ServiceDocActConfigureItemServiceUIModel> getServiceDocActConfigureItemUIModelList() {
		return serviceDocActConfigureItemUIModelList;
	}

	public void setServiceDocActConfigureItemUIModelList(
			List<ServiceDocActConfigureItemServiceUIModel> serviceDocActConfigureItemUIModelList) {
		this.serviceDocActConfigureItemUIModelList = serviceDocActConfigureItemUIModelList;
	}
}
