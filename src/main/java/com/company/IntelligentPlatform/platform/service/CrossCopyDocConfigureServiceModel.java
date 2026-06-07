package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.CrossCopyDocConfigure;
import com.company.IntelligentPlatform.platform.model.CrossCopyInvolveParty;

import java.util.ArrayList;
import java.util.List;

public class CrossCopyDocConfigureServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = CrossCopyDocConfigure.NODENAME, nodeInstId = CrossCopyDocConfigure.NODENAME)
	protected CrossCopyDocConfigure crossCopyDocConfigure;

	@IServiceModuleFieldConfig(nodeName = CrossCopyInvolveParty.NODENAME, nodeInstId =
			CrossCopyInvolveParty.NODENAME)
	protected List<CrossCopyInvolvePartyServiceModel> crossCopyInvolvePartyList = new ArrayList<>();

	public CrossCopyDocConfigure getCrossCopyDocConfigure() {
		return crossCopyDocConfigure;
	}

	public void setCrossCopyDocConfigure(CrossCopyDocConfigure crossCopyDocConfigure) {
		this.crossCopyDocConfigure = crossCopyDocConfigure;
	}

	public List<CrossCopyInvolvePartyServiceModel> getCrossCopyInvolvePartyList() {
		return crossCopyInvolvePartyList;
	}

	public void setCrossCopyInvolvePartyList(List<CrossCopyInvolvePartyServiceModel> crossCopyInvolvePartyList) {
		this.crossCopyInvolvePartyList = crossCopyInvolvePartyList;
	}
}
