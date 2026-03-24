package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.WasteProcessMaterialItem;
import com.company.IntelligentPlatform.logistics.model.WasteProcessMaterialItemAttachment;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class WasteProcessMaterialItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = WasteProcessMaterialItem.NODENAME, nodeInstId = WasteProcessMaterialItem.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected WasteProcessMaterialItem wasteProcessMaterialItem;

	@IServiceModuleFieldConfig(nodeName = WasteProcessMaterialItemAttachment.NODENAME, nodeInstId = WasteProcessMaterialItemAttachment.NODENAME,
			blockUpdate = true,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> wasteProcessMaterialItemAttachmentList = new ArrayList<>();

	public List<ServiceEntityNode> getWasteProcessMaterialItemAttachmentList() {
		return this.wasteProcessMaterialItemAttachmentList;
	}

	public void setWasteProcessMaterialItemAttachmentList(
			List<ServiceEntityNode> wasteProcessMaterialItemAttachmentList) {
		this.wasteProcessMaterialItemAttachmentList = wasteProcessMaterialItemAttachmentList;
	}

	public WasteProcessMaterialItem getWasteProcessMaterialItem() {
		return this.wasteProcessMaterialItem;
	}

	public void setWasteProcessMaterialItem(
			WasteProcessMaterialItem wasteProcessMaterialItem) {
		this.wasteProcessMaterialItem = wasteProcessMaterialItem;
	}

}
