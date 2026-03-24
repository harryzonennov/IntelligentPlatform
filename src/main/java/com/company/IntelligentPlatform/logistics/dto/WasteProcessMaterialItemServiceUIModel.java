package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.WasteProcessMaterialItem;
import com.company.IntelligentPlatform.logistics.model.WasteProcessMaterialItemAttachment;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class WasteProcessMaterialItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = WasteProcessMaterialItem.NODENAME,  nodeInstId = WasteProcessMaterialItem.NODENAME)
	protected WasteProcessMaterialItemUIModel wasteProcessMaterialItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = WasteProcessMaterialItemAttachment.NODENAME, nodeInstId = WasteProcessMaterialItemAttachment.NODENAME)
	protected List<WasteProcessMaterialItemAttachmentUIModel> wasteProcessMaterialItemAttachmentUIModelList = new ArrayList<WasteProcessMaterialItemAttachmentUIModel>();

	public WasteProcessMaterialItemUIModel getWasteProcessMaterialItemUIModel() {
		return this.wasteProcessMaterialItemUIModel;
	}

	public void setWasteProcessMaterialItemUIModel(
			WasteProcessMaterialItemUIModel wasteProcessMaterialItemUIModel) {
		this.wasteProcessMaterialItemUIModel = wasteProcessMaterialItemUIModel;
	}

	public List<WasteProcessMaterialItemAttachmentUIModel> getWasteProcessMaterialItemAttachmentUIModelList() {
		return this.wasteProcessMaterialItemAttachmentUIModelList;
	}

	public void setWasteProcessMaterialItemAttachmentUIModelList(
			List<WasteProcessMaterialItemAttachmentUIModel> wasteProcessMaterialItemAttachmentUIModelList) {
		this.wasteProcessMaterialItemAttachmentUIModelList = wasteProcessMaterialItemAttachmentUIModelList;
	}
}
