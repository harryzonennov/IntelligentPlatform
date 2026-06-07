package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.ServiceDocInitConfigure;
import com.company.IntelligentPlatform.platform.model.ServiceDocInitInvolveParty;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceDocInitConfigureServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocInitConfigure.NODENAME, nodeInstId = ServiceDocInitConfigure.NODENAME)
	protected ServiceDocInitConfigureUIModel serviceDocInitConfigureUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocInitInvolveParty.NODENAME, nodeInstId =
			ServiceDocInitInvolveParty.NODENAME)
	protected List<ServiceDocInitInvolvePartyServiceUIModel> serviceDocServiceDocInitInvolvePartyUIModelList = new ArrayList<>();

	public ServiceDocInitConfigureUIModel getServiceDocInitConfigureUIModel() {
		return serviceDocInitConfigureUIModel;
	}

	public void setServiceDocInitConfigureUIModel(ServiceDocInitConfigureUIModel serviceDocInitConfigureUIModel) {
		this.serviceDocInitConfigureUIModel = serviceDocInitConfigureUIModel;
	}

	public List<ServiceDocInitInvolvePartyServiceUIModel> getServiceDocInitInvolvePartyUIModelList() {
		return serviceDocServiceDocInitInvolvePartyUIModelList;
	}

	public void setServiceDocInitInvolvePartyUIModelList(
			List<ServiceDocInitInvolvePartyServiceUIModel> serviceDocServiceDocInitInvolvePartyUIModelList) {
		this.serviceDocServiceDocInitInvolvePartyUIModelList = serviceDocServiceDocInitInvolvePartyUIModelList;
	}
}
