package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.PurchaseReturnMaterialItem;
import com.company.IntelligentPlatform.logistics.model.PurchaseReturnMaterialItemAttachment;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseReturnMaterialItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = PurchaseReturnMaterialItem.NODENAME, nodeInstId = PurchaseReturnMaterialItem.NODENAME)
	protected PurchaseReturnMaterialItemUIModel purchaseReturnMaterialItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseReturnMaterialItemAttachment.NODENAME, nodeInstId =
			PurchaseReturnMaterialItemAttachment.NODENAME)
	protected List<PurchaseReturnMatItemAttachmentUIModel> purchaseReturnMaterialItemAttachmentUIModelList =
			new ArrayList<>();

	public PurchaseReturnMaterialItemUIModel getPurchaseReturnMaterialItemUIModel() {
		return this.purchaseReturnMaterialItemUIModel;
	}

	public void setPurchaseReturnMaterialItemUIModel(
			PurchaseReturnMaterialItemUIModel purchaseReturnMaterialItemUIModel) {
		this.purchaseReturnMaterialItemUIModel = purchaseReturnMaterialItemUIModel;
	}

	public List<PurchaseReturnMatItemAttachmentUIModel> getPurchaseReturnMaterialItemAttachmentUIModelList() {
		return purchaseReturnMaterialItemAttachmentUIModelList;
	}

	public void setPurchaseReturnMaterialItemAttachmentUIModelList(List<PurchaseReturnMatItemAttachmentUIModel> purchaseReturnMaterialItemAttachmentUIModelList) {
		this.purchaseReturnMaterialItemAttachmentUIModelList = purchaseReturnMaterialItemAttachmentUIModelList;
	}
}
