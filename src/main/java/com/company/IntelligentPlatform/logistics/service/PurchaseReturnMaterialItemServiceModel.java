package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.PurchaseReturnMaterialItem;
import com.company.IntelligentPlatform.logistics.model.PurchaseReturnMaterialItemAttachment;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class PurchaseReturnMaterialItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = PurchaseReturnMaterialItem.NODENAME, nodeInstId = PurchaseReturnMaterialItem.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected PurchaseReturnMaterialItem purchaseReturnMaterialItem;

	@IServiceModuleFieldConfig(nodeName = PurchaseReturnMaterialItemAttachment.NODENAME, nodeInstId =
			PurchaseReturnMaterialItemAttachment.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> purchaseReturnMatItemAttachmentList = new ArrayList<>();

	public PurchaseReturnMaterialItem getPurchaseReturnMaterialItem() {
		return purchaseReturnMaterialItem;
	}

	public void setPurchaseReturnMaterialItem(final PurchaseReturnMaterialItem purchaseReturnMaterialItem) {
		this.purchaseReturnMaterialItem = purchaseReturnMaterialItem;
	}

	public List<ServiceEntityNode> getPurchaseReturnMatItemAttachmentList() {
		return this.purchaseReturnMatItemAttachmentList;
	}

	public void setPurchaseReturnMatItemAttachmentList(
			List<ServiceEntityNode> purchaseReturnMatItemAttachmentList) {
		this.purchaseReturnMatItemAttachmentList = purchaseReturnMatItemAttachmentList;
	}

}
