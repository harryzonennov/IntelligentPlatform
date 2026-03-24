package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.PurchaseContractMaterialItem;
import com.company.IntelligentPlatform.logistics.model.PurchaseContractMaterialItemAttachment;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class PurchaseContractMaterialItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = PurchaseContractMaterialItem.NODENAME, nodeInstId = PurchaseContractMaterialItem.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected PurchaseContractMaterialItem purchaseContractMaterialItem;

	@IServiceModuleFieldConfig(nodeName = PurchaseContractMaterialItemAttachment.NODENAME, nodeInstId = PurchaseContractMaterialItemAttachment.NODENAME, 
			blockUpdate = true , docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> purchaseContractMaterialItemAttachmentList = new ArrayList<>();

	public List<ServiceEntityNode> getPurchaseContractMaterialItemAttachmentList() {
		return this.purchaseContractMaterialItemAttachmentList;
	}

	public void setPurchaseContractMaterialItemAttachmentList(
			List<ServiceEntityNode> purchaseContractMaterialItemAttachmentList) {
		this.purchaseContractMaterialItemAttachmentList = purchaseContractMaterialItemAttachmentList;
	}

	public PurchaseContractMaterialItem getPurchaseContractMaterialItem() {
		return this.purchaseContractMaterialItem;
	}

	public void setPurchaseContractMaterialItem(
			PurchaseContractMaterialItem purchaseContractMaterialItem) {
		this.purchaseContractMaterialItem = purchaseContractMaterialItem;
	}

}
