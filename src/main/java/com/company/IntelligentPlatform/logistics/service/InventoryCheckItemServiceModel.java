package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.InventoryCheckItemAttachment;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class InventoryCheckItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = InventoryCheckItem.NODENAME, nodeInstId = InventoryCheckItem.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected InventoryCheckItem inventoryCheckItem;

	@IServiceModuleFieldConfig(nodeName = InventoryCheckItemAttachment.NODENAME, nodeInstId =
			InventoryCheckItemAttachment.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> inventoryCheckItemAttachmentList = new ArrayList<>();

	public InventoryCheckItem getInventoryCheckItem() {
		return inventoryCheckItem;
	}

	public void setInventoryCheckItem(InventoryCheckItem inventoryCheckItem) {
		this.inventoryCheckItem = inventoryCheckItem;
	}

	public List<ServiceEntityNode> getInventoryCheckItemAttachmentList() {
		return inventoryCheckItemAttachmentList;
	}

	public void setInventoryCheckItemAttachmentList(List<ServiceEntityNode> inventoryCheckItemAttachmentList) {
		this.inventoryCheckItemAttachmentList = inventoryCheckItemAttachmentList;
	}
}
