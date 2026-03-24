package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.CrossCopyInvolveParty;

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
