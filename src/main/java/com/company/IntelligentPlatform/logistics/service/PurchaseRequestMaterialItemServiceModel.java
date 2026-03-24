package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.PurchaseRequestMaterialItem;
import com.company.IntelligentPlatform.logistics.model.PurchaseRequestMaterialItemAttachment;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class PurchaseRequestMaterialItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = PurchaseRequestMaterialItem.NODENAME, nodeInstId = PurchaseRequestMaterialItem.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected PurchaseRequestMaterialItem purchaseRequestMaterialItem;

	@IServiceModuleFieldConfig(nodeName = PurchaseRequestMaterialItemAttachment.NODENAME, nodeInstId =
			PurchaseRequestMaterialItemAttachment.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> purchaseRequestMaterialItemAttachmentList = new ArrayList<>();

	public PurchaseRequestMaterialItem getPurchaseRequestMaterialItem() {
		return purchaseRequestMaterialItem;
	}

	public void setPurchaseRequestMaterialItem(final PurchaseRequestMaterialItem purchaseRequestMaterialItem) {
		this.purchaseRequestMaterialItem = purchaseRequestMaterialItem;
	}

	public List<ServiceEntityNode> getPurchaseRequestMaterialItemAttachmentList() {
		return purchaseRequestMaterialItemAttachmentList;
	}

	public void setPurchaseRequestMaterialItemAttachmentList(List<ServiceEntityNode> purchaseRequestMaterialItemAttachmentList) {
		this.purchaseRequestMaterialItemAttachmentList = purchaseRequestMaterialItemAttachmentList;
	}
}
