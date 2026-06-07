package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.CrossCopyInvolveParty;

public class CrossCopyInvolvePartyServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = CrossCopyInvolveParty.NODENAME, nodeInstId = CrossCopyInvolveParty.NODENAME)
	protected CrossCopyInvolveParty crossCopyInvolveParty;

	public CrossCopyInvolveParty getCrossCopyInvolveParty() {
		return crossCopyInvolveParty;
	}

	public void setCrossCopyInvolveParty(CrossCopyInvolveParty crossCopyInvolveParty) {
		this.crossCopyInvolveParty = crossCopyInvolveParty;
	}
}
