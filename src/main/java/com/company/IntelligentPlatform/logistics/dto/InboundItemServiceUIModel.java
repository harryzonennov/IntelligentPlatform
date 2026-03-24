package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.InboundItem;
import com.company.IntelligentPlatform.logistics.model.InboundItemParty;
import com.company.IntelligentPlatform.logistics.model.InboundItemAttachment;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class InboundItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = InboundItem.NODENAME, nodeInstId = InboundItem.NODENAME)
	protected InboundItemUIModel inboundItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InboundItemAttachment.NODENAME, nodeInstId =
			InboundItemAttachment.NODENAME)
	protected List<InboundItemAttachmentUIModel> inboundItemAttachmentUIModelList =
			new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = InboundItemParty.NODENAME, nodeInstId = InboundItemParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected InboundItemPartyUIModel purchaseFromSupplierUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InboundItemParty.NODENAME, nodeInstId = InboundItemParty.PARTY_NODEINST_PUR_ORG)
	protected InboundItemPartyUIModel purchaseToOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InboundItemParty.NODENAME, nodeInstId =
			InboundItemParty.PARTY_NODEINST_PROD_ORG)
	protected InboundItemPartyUIModel productionOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InboundItemParty.NODENAME, nodeInstId =
			InboundItemParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected InboundItemPartyUIModel soldToCustomerUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InboundItemParty.NODENAME, nodeInstId =
			InboundItemParty.PARTY_NODEINST_SOLD_ORG)
	protected InboundItemPartyUIModel soldFromOrgUIModel;

	public InboundItemUIModel getInboundItemUIModel() {
		return inboundItemUIModel;
	}

	public void setInboundItemUIModel(InboundItemUIModel inboundItemUIModel) {
		this.inboundItemUIModel = inboundItemUIModel;
	}

	public List<InboundItemAttachmentUIModel> getInboundItemAttachmentUIModelList() {
		return inboundItemAttachmentUIModelList;
	}

	public void setInboundItemAttachmentUIModelList(
			List<InboundItemAttachmentUIModel> inboundItemAttachmentUIModelList) {
		this.inboundItemAttachmentUIModelList = inboundItemAttachmentUIModelList;
	}

	public InboundItemPartyUIModel getPurchaseFromSupplierUIModel() {
		return purchaseFromSupplierUIModel;
	}

	public void setPurchaseFromSupplierUIModel(InboundItemPartyUIModel purchaseFromSupplierUIModel) {
		this.purchaseFromSupplierUIModel = purchaseFromSupplierUIModel;
	}

	public InboundItemPartyUIModel getPurchaseToOrgUIModel() {
		return purchaseToOrgUIModel;
	}

	public void setPurchaseToOrgUIModel(InboundItemPartyUIModel purchaseToOrgUIModel) {
		this.purchaseToOrgUIModel = purchaseToOrgUIModel;
	}

	public InboundItemPartyUIModel getProductionOrgUIModel() {
		return productionOrgUIModel;
	}

	public void setProductionOrgUIModel(InboundItemPartyUIModel productionOrgUIModel) {
		this.productionOrgUIModel = productionOrgUIModel;
	}

	public InboundItemPartyUIModel getSoldToCustomerUIModel() {
		return soldToCustomerUIModel;
	}

	public void setSoldToCustomerUIModel(InboundItemPartyUIModel soldToCustomerUIModel) {
		this.soldToCustomerUIModel = soldToCustomerUIModel;
	}

	public InboundItemPartyUIModel getSoldFromOrgUIModel() {
		return soldFromOrgUIModel;
	}

	public void setSoldFromOrgUIModel(InboundItemPartyUIModel soldFromOrgUIModel) {
		this.soldFromOrgUIModel = soldFromOrgUIModel;
	}
}
