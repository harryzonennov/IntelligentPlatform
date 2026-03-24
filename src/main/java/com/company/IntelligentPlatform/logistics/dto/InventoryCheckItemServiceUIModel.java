package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.InventoryCheckItem;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckItemAttachment;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class InventoryCheckItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = InventoryCheckItem.NODENAME, nodeInstId = InventoryCheckItem.NODENAME)
	protected InventoryCheckItemUIModel inventoryCheckItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InventoryCheckItemAttachment.NODENAME, nodeInstId =
			InventoryCheckItemAttachment.NODENAME)
	protected List<InventoryCheckItemAttachmentUIModel> inventoryCheckItemAttachmentUIModelList =
			new ArrayList<>();

	public InventoryCheckItemUIModel getInventoryCheckItemUIModel() {
		return inventoryCheckItemUIModel;
	}

	public void setInventoryCheckItemUIModel(InventoryCheckItemUIModel inventoryCheckItemUIModel) {
		this.inventoryCheckItemUIModel = inventoryCheckItemUIModel;
	}

	public List<InventoryCheckItemAttachmentUIModel> getInventoryCheckItemAttachmentUIModelList() {
		return inventoryCheckItemAttachmentUIModelList;
	}

	public void setInventoryCheckItemAttachmentUIModelList(
			List<InventoryCheckItemAttachmentUIModel> inventoryCheckItemAttachmentUIModelList) {
		this.inventoryCheckItemAttachmentUIModelList = inventoryCheckItemAttachmentUIModelList;
	}
}
