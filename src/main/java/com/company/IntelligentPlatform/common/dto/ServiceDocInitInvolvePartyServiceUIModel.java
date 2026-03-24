package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.ServiceDocInitInvolveParty;

@Component
public class ServiceDocInitInvolvePartyServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocInitInvolveParty.NODENAME, nodeInstId = ServiceDocInitInvolveParty.NODENAME)
	protected ServiceDocInitInvolvePartyUIModel serviceDocServiceDocInitInvolvePartyUIModel;

	public ServiceDocInitInvolvePartyUIModel getServiceDocInitInvolvePartyUIModel() {
		return serviceDocServiceDocInitInvolvePartyUIModel;
	}

	public void setServiceDocInitInvolvePartyUIModel(ServiceDocInitInvolvePartyUIModel serviceDocServiceDocInitInvolvePartyUIModel) {
		this.serviceDocServiceDocInitInvolvePartyUIModel = serviceDocServiceDocInitInvolvePartyUIModel;
	}
}
