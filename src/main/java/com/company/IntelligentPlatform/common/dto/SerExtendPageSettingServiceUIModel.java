package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.*;

@Component
public class SerExtendPageSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SerExtendPageSection.NODENAME, nodeInstId = SerExtendPageSection.NODENAME)
	protected List<SerExtendPageSectionServiceUIModel> serExtendPageSectionUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = SerExtendPageMetadata.NODENAME, nodeInstId = SerExtendPageMetadata.NODENAME)
	protected List<SerExtendPageMetadataServiceUIModel> serExtendPageMetadataUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = SerExtendUIControlSet.NODENAME, nodeInstId = SerExtendUIControlSet.NODENAME)
	protected List<SerExtendUIControlSetServiceUIModel> serExtendUIControlSetUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = SerExtendPageSetting.NODENAME, nodeInstId = SerExtendPageSetting.NODENAME)
	protected SerExtendPageSettingUIModel serExtendPageSettingUIModel;

	public SerExtendPageSettingUIModel getSerExtendPageSettingUIModel() {
		return serExtendPageSettingUIModel;
	}

	public List<SerExtendUIControlSetServiceUIModel> getSerExtendUIControlSetUIModelList() {
		return serExtendUIControlSetUIModelList;
	}

	public void setSerExtendUIControlSetUIModelList(
			List<SerExtendUIControlSetServiceUIModel> serExtendUIControlSetUIModelList) {
		this.serExtendUIControlSetUIModelList = serExtendUIControlSetUIModelList;
	}

	public void setSerExtendPageSettingUIModel(
			SerExtendPageSettingUIModel serExtendPageSettingUIModel) {
		this.serExtendPageSettingUIModel = serExtendPageSettingUIModel;
	}

	public List<SerExtendPageSectionServiceUIModel> getSerExtendPageSectionUIModelList() {
		return serExtendPageSectionUIModelList;
	}

	public void setSerExtendPageSectionUIModelList(
			List<SerExtendPageSectionServiceUIModel> serExtendPageSectionUIModelList) {
		this.serExtendPageSectionUIModelList = serExtendPageSectionUIModelList;
	}

	public List<SerExtendPageMetadataServiceUIModel> getSerExtendPageMetadataUIModelList() {
		return serExtendPageMetadataUIModelList;
	}

	public void setSerExtendPageMetadataUIModelList(
			List<SerExtendPageMetadataServiceUIModel> serExtendPageMetadataUIModelList) {
		this.serExtendPageMetadataUIModelList = serExtendPageMetadataUIModelList;
	}
}
