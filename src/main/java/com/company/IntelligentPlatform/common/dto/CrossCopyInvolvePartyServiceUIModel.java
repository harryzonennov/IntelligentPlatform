package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.CrossCopyDocConfigure;
import com.company.IntelligentPlatform.common.model.CrossCopyInvolveParty;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrossCopyInvolvePartyServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = CrossCopyInvolveParty.NODENAME, nodeInstId = CrossCopyInvolveParty.NODENAME)
	protected CrossCopyInvolvePartyUIModel crossCopyInvolvePartyUIModel;

	public CrossCopyInvolvePartyUIModel getCrossCopyInvolvePartyUIModel() {
		return crossCopyInvolvePartyUIModel;
	}

	public void setCrossCopyInvolvePartyUIModel(CrossCopyInvolvePartyUIModel crossCopyInvolvePartyUIModel) {
		this.crossCopyInvolvePartyUIModel = crossCopyInvolvePartyUIModel;
	}
}
