package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.CrossCopyDocConfigure;
import com.company.IntelligentPlatform.common.model.CrossCopyInvolveParty;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrossCopyDocConfigureServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = CrossCopyDocConfigure.NODENAME, nodeInstId = CrossCopyDocConfigure.NODENAME)
	protected CrossCopyDocConfigureUIModel crossCopyDocConfigureUIModel;

	@IServiceUIModuleFieldConfig(nodeName = CrossCopyInvolveParty.NODENAME, nodeInstId =
			CrossCopyInvolveParty.NODENAME)
	protected List<CrossCopyInvolvePartyServiceUIModel> crossCopyInvolvePartyUIModelList = new ArrayList<>();

	public CrossCopyDocConfigureUIModel getCrossCopyDocConfigureUIModel() {
		return crossCopyDocConfigureUIModel;
	}

	public void setCrossCopyDocConfigureUIModel(CrossCopyDocConfigureUIModel crossCopyDocConfigureUIModel) {
		this.crossCopyDocConfigureUIModel = crossCopyDocConfigureUIModel;
	}

	public List<CrossCopyInvolvePartyServiceUIModel> getCrossCopyInvolvePartyUIModelList() {
		return crossCopyInvolvePartyUIModelList;
	}

	public void setCrossCopyInvolvePartyUIModelList(
			List<CrossCopyInvolvePartyServiceUIModel> crossCopyInvolvePartyUIModelList) {
		this.crossCopyInvolvePartyUIModelList = crossCopyInvolvePartyUIModelList;
	}
}
