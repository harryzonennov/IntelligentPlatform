package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.*;

public class SerExtendPageSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SerExtendPageSetting.NODENAME, nodeInstId = SerExtendPageSetting.NODENAME)
	protected SerExtendPageSetting serExtendPageSetting;

	@IServiceModuleFieldConfig(nodeName = SerExtendPageSection.NODENAME, nodeInstId = SerExtendPageSection.NODENAME)
	protected List<SerExtendPageSectionServiceModel> serExtendPageSectionList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = SerExtendPageMetadata.NODENAME, nodeInstId = SerExtendPageMetadata.NODENAME)
	protected List<SerExtendPageMetadataServiceModel> serExtendPageMetadataList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = SerExtendUIControlSet.NODENAME, nodeInstId = SerExtendUIControlSet.NODENAME)
	protected List<SerExtendUIControlSetServiceModel> serExtendUIControlSetList = new ArrayList<>();
	
	public SerExtendPageSetting getSerExtendPageSetting() {
		return this.serExtendPageSetting;
	}

	public void setSerExtendPageSetting(
			SerExtendPageSetting serExtendPageSetting) {
		this.serExtendPageSetting = serExtendPageSetting;
	}

	public List<SerExtendPageSectionServiceModel> getSerExtendPageSectionList() {
		return serExtendPageSectionList;
	}

	public void setSerExtendPageSectionList(
			List<SerExtendPageSectionServiceModel> serExtendPageSectionList) {
		this.serExtendPageSectionList = serExtendPageSectionList;
	}

	public List<SerExtendPageMetadataServiceModel> getSerExtendPageMetadataList() {
		return serExtendPageMetadataList;
	}

	public void setSerExtendPageMetadataList(List<SerExtendPageMetadataServiceModel> serExtendPageMetadataList) {
		this.serExtendPageMetadataList = serExtendPageMetadataList;
	}

	public List<SerExtendUIControlSetServiceModel> getSerExtendUIControlSetList() {
		return serExtendUIControlSetList;
	}

	public void setSerExtendUIControlSetList(List<SerExtendUIControlSetServiceModel> serExtendUIControlSetList) {
		this.serExtendUIControlSetList = serExtendUIControlSetList;
	}
}
