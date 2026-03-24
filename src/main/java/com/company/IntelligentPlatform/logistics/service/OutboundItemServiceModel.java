package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.OutboundItem;
import com.company.IntelligentPlatform.logistics.model.OutboundItemParty;
import com.company.IntelligentPlatform.logistics.model.OutboundItemAttachment;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class OutboundItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = OutboundItem.NODENAME, nodeInstId = OutboundItem.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected OutboundItem outboundItem;

	@IServiceModuleFieldConfig(nodeName = OutboundItemAttachment.NODENAME, nodeInstId =
			OutboundItemAttachment.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> outboundItemAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = OutboundItemParty.NODENAME, nodeInstId = OutboundItemParty.PARTY_NODEINST_PUR_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected OutboundItemParty purchaseToOrg;

	@IServiceModuleFieldConfig(nodeName = OutboundItemParty.NODENAME, nodeInstId = OutboundItemParty.PARTY_NODEINST_PUR_SUPPLIER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected OutboundItemParty purchaseFromSupplier;

	@IServiceModuleFieldConfig(nodeName = OutboundItemParty.NODENAME, nodeInstId = OutboundItemParty.PARTY_NODEINST_SOLD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected OutboundItemParty soldFromOrg;

	@IServiceModuleFieldConfig(nodeName = OutboundItemParty.NODENAME, nodeInstId = OutboundItemParty.PARTY_NODEINST_PROD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected OutboundItemParty productionOrg;

	@IServiceModuleFieldConfig(nodeName = OutboundItemParty.NODENAME, nodeInstId = OutboundItemParty.PARTY_NODEINST_SOLD_CUSTOMER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected OutboundItemParty soldToCustomer;

	public OutboundItem getOutboundItem() {
		return outboundItem;
	}

	public void setOutboundItem(OutboundItem outboundItem) {
		this.outboundItem = outboundItem;
	}

	public List<ServiceEntityNode> getOutboundItemAttachmentList() {
		return outboundItemAttachmentList;
	}

	public void setOutboundItemAttachmentList(List<ServiceEntityNode> outboundItemAttachmentList) {
		this.outboundItemAttachmentList = outboundItemAttachmentList;
	}

	public OutboundItemParty getPurchaseToOrg() {
		return purchaseToOrg;
	}

	public void setPurchaseToOrg(OutboundItemParty purchaseToOrg) {
		this.purchaseToOrg = purchaseToOrg;
	}

	public OutboundItemParty getPurchaseFromSupplier() {
		return purchaseFromSupplier;
	}

	public void setPurchaseFromSupplier(OutboundItemParty purchaseFromSupplier) {
		this.purchaseFromSupplier = purchaseFromSupplier;
	}

	public OutboundItemParty getSoldFromOrg() {
		return soldFromOrg;
	}

	public void setSoldFromOrg(OutboundItemParty soldFromOrg) {
		this.soldFromOrg = soldFromOrg;
	}

	public OutboundItemParty getProductionOrg() {
		return productionOrg;
	}

	public void setProductionOrg(OutboundItemParty productionOrg) {
		this.productionOrg = productionOrg;
	}

	public OutboundItemParty getSoldToCustomer() {
		return soldToCustomer;
	}

	public void setSoldToCustomer(OutboundItemParty soldToCustomer) {
		this.soldToCustomer = soldToCustomer;
	}
}
