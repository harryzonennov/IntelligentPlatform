package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.ServiceDocActConfigureItem;

@Component
public class ServiceDocActConfigureItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocActConfigureItem.NODENAME, nodeInstId = ServiceDocActConfigureItem.NODENAME)
	protected ServiceDocActConfigureItemUIModel serviceDocActConfigureItemUIModel;

	public ServiceDocActConfigureItemUIModel getServiceDocActConfigureItemUIModel() {
		return serviceDocActConfigureItemUIModel;
	}

	public void setServiceDocActConfigureItemUIModel(ServiceDocActConfigureItemUIModel serviceDocActConfigureItemUIModel) {
		this.serviceDocActConfigureItemUIModel = serviceDocActConfigureItemUIModel;
	}
}
