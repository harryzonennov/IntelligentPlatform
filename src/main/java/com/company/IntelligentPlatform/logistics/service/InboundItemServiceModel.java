package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class InboundItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = InboundItem.NODENAME, nodeInstId = InboundItem.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected InboundItem inboundItem;

	@IServiceModuleFieldConfig(nodeName = InboundItemAttachment.NODENAME, nodeInstId =
			InboundItemAttachment.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> inboundItemAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = InboundItemParty.NODENAME, nodeInstId = InboundItemParty.PARTY_NODEINST_PUR_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InboundItemParty purchaseToOrg;

	@IServiceModuleFieldConfig(nodeName = InboundItemParty.NODENAME, nodeInstId = InboundItemParty.PARTY_NODEINST_PUR_SUPPLIER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InboundItemParty purchaseFromSupplier;

	@IServiceModuleFieldConfig(nodeName = InboundItemParty.NODENAME, nodeInstId = InboundItemParty.PARTY_NODEINST_SOLD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InboundItemParty soldFromOrg;

	@IServiceModuleFieldConfig(nodeName = InboundItemParty.NODENAME, nodeInstId = InboundItemParty.PARTY_NODEINST_PROD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InboundItemParty productionOrg;

	@IServiceModuleFieldConfig(nodeName = InboundItemParty.NODENAME, nodeInstId = InboundItemParty.PARTY_NODEINST_SOLD_CUSTOMER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InboundItemParty soldToCustomer;

	public InboundItem getInboundItem() {
		return inboundItem;
	}

	public void setInboundItem(InboundItem inboundItem) {
		this.inboundItem = inboundItem;
	}

	public List<ServiceEntityNode> getInboundItemAttachmentList() {
		return inboundItemAttachmentList;
	}

	public void setInboundItemAttachmentList(List<ServiceEntityNode> inboundItemAttachmentList) {
		this.inboundItemAttachmentList = inboundItemAttachmentList;
	}

	public InboundItemParty getPurchaseToOrg() {
		return purchaseToOrg;
	}

	public void setPurchaseToOrg(InboundItemParty purchaseToOrg) {
		this.purchaseToOrg = purchaseToOrg;
	}

	public InboundItemParty getPurchaseFromSupplier() {
		return purchaseFromSupplier;
	}

	public void setPurchaseFromSupplier(InboundItemParty purchaseFromSupplier) {
		this.purchaseFromSupplier = purchaseFromSupplier;
	}

	public InboundItemParty getSoldFromOrg() {
		return soldFromOrg;
	}

	public void setSoldFromOrg(InboundItemParty soldFromOrg) {
		this.soldFromOrg = soldFromOrg;
	}

	public InboundItemParty getProductionOrg() {
		return productionOrg;
	}

	public void setProductionOrg(InboundItemParty productionOrg) {
		this.productionOrg = productionOrg;
	}

	public InboundItemParty getSoldToCustomer() {
		return soldToCustomer;
	}

	public void setSoldToCustomer(InboundItemParty soldToCustomer) {
		this.soldToCustomer = soldToCustomer;
	}
}
