package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.SerExtendPageSection;
import com.company.IntelligentPlatform.common.model.SerExtendUIControlSet;

@Component
public class SerExtendPageSectionServiceUIModel extends ServiceUIModule {
	
	@IServiceUIModuleFieldConfig(nodeName = SerExtendUIControlSet.NODENAME, nodeInstId = SerExtendUIControlSet.NODENAME)
	protected List<SerExtendUIControlSetUIModel> serExtendUIControlSetUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = SerExtendPageSection.NODENAME, nodeInstId = SerExtendPageSection.NODENAME)
	protected SerExtendPageSectionUIModel serExtendPageSectionUIModel;	

	public SerExtendPageSectionUIModel getSerExtendPageSectionUIModel() {
		return serExtendPageSectionUIModel;
	}

	public void setSerExtendPageSectionUIModel(
			SerExtendPageSectionUIModel serExtendPageSectionUIModel) {
		this.serExtendPageSectionUIModel = serExtendPageSectionUIModel;
	}

	public List<SerExtendUIControlSetUIModel> getSerExtendUIControlSetUIModelList() {
		return serExtendUIControlSetUIModelList;
	}

	public void setSerExtendUIControlSetUIModelList(
			List<SerExtendUIControlSetUIModel> serExtendUIControlSetUIModelList) {
		this.serExtendUIControlSetUIModelList = serExtendUIControlSetUIModelList;
	}
	
}
