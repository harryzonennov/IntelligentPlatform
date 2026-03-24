package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.PurchaseContractMaterialItem;
import com.company.IntelligentPlatform.logistics.model.PurchaseContractMaterialItemAttachment;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class PurchaseContractMaterialItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = PurchaseContractMaterialItem.NODENAME, nodeInstId = PurchaseContractMaterialItem.NODENAME)
	protected PurchaseContractMaterialItemUIModel purchaseContractMaterialItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseContractMaterialItemAttachment.NODENAME, nodeInstId = PurchaseContractMaterialItemAttachment.NODENAME)
	protected List<PurchaseContractMaterialItemAttachmentUIModel> purchaseContractMaterialItemAttachmentUIModelList = new ArrayList<PurchaseContractMaterialItemAttachmentUIModel>();

	public PurchaseContractMaterialItemUIModel getPurchaseContractMaterialItemUIModel() {
		return this.purchaseContractMaterialItemUIModel;
	}

	public void setPurchaseContractMaterialItemUIModel(
			PurchaseContractMaterialItemUIModel purchaseContractMaterialItemUIModel) {
		this.purchaseContractMaterialItemUIModel = purchaseContractMaterialItemUIModel;
	}

	public List<PurchaseContractMaterialItemAttachmentUIModel> getPurchaseContractMaterialItemAttachmentUIModelList() {
		return this.purchaseContractMaterialItemAttachmentUIModelList;
	}

	public void setPurchaseContractMaterialItemAttachmentUIModelList(
			List<PurchaseContractMaterialItemAttachmentUIModel> purchaseContractMaterialItemAttachmentUIModelList) {
		this.purchaseContractMaterialItemAttachmentUIModelList = purchaseContractMaterialItemAttachmentUIModelList;
	}

}
