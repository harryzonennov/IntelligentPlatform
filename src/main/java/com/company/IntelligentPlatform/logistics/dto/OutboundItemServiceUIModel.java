package com.company.IntelligentPlatform.logistics.dto;


import com.company.IntelligentPlatform.logistics.model.OutboundItem;
import com.company.IntelligentPlatform.logistics.model.OutboundItemParty;
import com.company.IntelligentPlatform.logistics.model.OutboundItemAttachment;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class OutboundItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = OutboundItem.NODENAME, nodeInstId = OutboundItem.NODENAME)
	protected OutboundItemUIModel outboundItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = OutboundItemAttachment.NODENAME, nodeInstId =
			OutboundItemAttachment.NODENAME)
	protected List<OutboundItemAttachmentUIModel> outboundItemAttachmentUIModelList =
			new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = OutboundItemParty.NODENAME, nodeInstId = OutboundItemParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected OutboundItemPartyUIModel purchaseFromSupplierUIModel;

	@IServiceUIModuleFieldConfig(nodeName = OutboundItemParty.NODENAME, nodeInstId = OutboundItemParty.PARTY_NODEINST_PUR_ORG)
	protected OutboundItemPartyUIModel purchaseToOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = OutboundItemParty.NODENAME, nodeInstId =
			OutboundItemParty.PARTY_NODEINST_PROD_ORG)
	protected OutboundItemPartyUIModel productionOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = OutboundItemParty.NODENAME, nodeInstId =
			OutboundItemParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected OutboundItemPartyUIModel soldToCustomerUIModel;

	@IServiceUIModuleFieldConfig(nodeName = OutboundItemParty.NODENAME, nodeInstId =
			OutboundItemParty.PARTY_NODEINST_SOLD_ORG)
	protected OutboundItemPartyUIModel soldFromOrgUIModel;

	public OutboundItemUIModel getOutboundItemUIModel() {
		return outboundItemUIModel;
	}

	public void setOutboundItemUIModel(OutboundItemUIModel outboundItemUIModel) {
		this.outboundItemUIModel = outboundItemUIModel;
	}

	public List<OutboundItemAttachmentUIModel> getOutboundItemAttachmentUIModelList() {
		return outboundItemAttachmentUIModelList;
	}

	public void setOutboundItemAttachmentUIModelList(
			List<OutboundItemAttachmentUIModel> outboundItemAttachmentUIModelList) {
		this.outboundItemAttachmentUIModelList = outboundItemAttachmentUIModelList;
	}

	public OutboundItemPartyUIModel getPurchaseFromSupplierUIModel() {
		return purchaseFromSupplierUIModel;
	}

	public void setPurchaseFromSupplierUIModel(OutboundItemPartyUIModel purchaseFromSupplierUIModel) {
		this.purchaseFromSupplierUIModel = purchaseFromSupplierUIModel;
	}

	public OutboundItemPartyUIModel getPurchaseToOrgUIModel() {
		return purchaseToOrgUIModel;
	}

	public void setPurchaseToOrgUIModel(OutboundItemPartyUIModel purchaseToOrgUIModel) {
		this.purchaseToOrgUIModel = purchaseToOrgUIModel;
	}

	public OutboundItemPartyUIModel getProductionOrgUIModel() {
		return productionOrgUIModel;
	}

	public void setProductionOrgUIModel(OutboundItemPartyUIModel productionOrgUIModel) {
		this.productionOrgUIModel = productionOrgUIModel;
	}

	public OutboundItemPartyUIModel getSoldToCustomerUIModel() {
		return soldToCustomerUIModel;
	}

	public void setSoldToCustomerUIModel(OutboundItemPartyUIModel soldToCustomerUIModel) {
		this.soldToCustomerUIModel = soldToCustomerUIModel;
	}

	public OutboundItemPartyUIModel getSoldFromOrgUIModel() {
		return soldFromOrgUIModel;
	}

	public void setSoldFromOrgUIModel(OutboundItemPartyUIModel soldFromOrgUIModel) {
		this.soldFromOrgUIModel = soldFromOrgUIModel;
	}
}
