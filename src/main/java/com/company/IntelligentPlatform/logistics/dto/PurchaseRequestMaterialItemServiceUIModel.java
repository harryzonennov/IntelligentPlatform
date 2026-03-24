package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.PurchaseRequestMaterialItem;
import com.company.IntelligentPlatform.logistics.model.PurchaseRequestMaterialItemAttachment;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseRequestMaterialItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = PurchaseRequestMaterialItem.NODENAME, nodeInstId = PurchaseRequestMaterialItem.NODENAME)
	protected PurchaseRequestMaterialItemUIModel purchaseRequestMaterialItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseRequestMaterialItemAttachment.NODENAME, nodeInstId = PurchaseRequestMaterialItemAttachment.NODENAME)
	protected List<PurchaseRequestMaterialItemAttachmentUIModel> purchaseRequestMatItemAttachmentUIModelList = new ArrayList<PurchaseRequestMaterialItemAttachmentUIModel>();

	public PurchaseRequestMaterialItemUIModel getPurchaseRequestMaterialItemUIModel() {
		return this.purchaseRequestMaterialItemUIModel;
	}

	public void setPurchaseRequestMaterialItemUIModel(
			PurchaseRequestMaterialItemUIModel purchaseRequestMaterialItemUIModel) {
		this.purchaseRequestMaterialItemUIModel = purchaseRequestMaterialItemUIModel;
	}

	public List<PurchaseRequestMaterialItemAttachmentUIModel> getPurchaseRequestMatItemAttachmentUIModelList() {
		return purchaseRequestMatItemAttachmentUIModelList;
	}

	public void setPurchaseRequestMatItemAttachmentUIModelList(List<PurchaseRequestMaterialItemAttachmentUIModel> purchaseRequestMatItemAttachmentUIModelList) {
		this.purchaseRequestMatItemAttachmentUIModelList = purchaseRequestMatItemAttachmentUIModelList;
	}
}
