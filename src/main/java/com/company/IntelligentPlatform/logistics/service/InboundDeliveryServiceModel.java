package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class InboundDeliveryServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = InboundDelivery.NODENAME, nodeInstId = InboundDelivery.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected InboundDelivery inboundDelivery;

	@IServiceModuleFieldConfig(nodeName = InboundDeliveryActionNode.NODENAME, nodeInstId =
			InboundDeliveryActionNode.NODEINST_ACTION_APPROVE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InboundDeliveryActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = InboundDeliveryActionNode.NODENAME, nodeInstId =
			InboundDeliveryActionNode.NODEINST_ACTION_REJECT_APPROVE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InboundDeliveryActionNode rejectApprovedBy;

	@IServiceModuleFieldConfig(nodeName = InboundDeliveryActionNode.NODENAME, nodeInstId =
			InboundDeliveryActionNode.NODEINST_ACTION_DELIVERY_DONE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InboundDeliveryActionNode deliveryDoneBy;

	@IServiceModuleFieldConfig(nodeName = InboundItem.NODENAME, nodeInstId = InboundItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<InboundItemServiceModel> inboundItemList = new ArrayList<>();
	
	@IServiceModuleFieldConfig(nodeName = InboundDeliveryAttachment.NODENAME, nodeInstId = InboundDeliveryAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> inboundDeliveryAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = InboundDeliveryParty.NODENAME, nodeInstId = InboundDeliveryParty.PARTY_NODEINST_PUR_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InboundDeliveryParty purchaseToOrg;

	@IServiceModuleFieldConfig(nodeName = InboundDeliveryParty.NODENAME, nodeInstId = InboundDeliveryParty.PARTY_NODEINST_PUR_SUPPLIER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InboundDeliveryParty purchaseFromSupplier;

	@IServiceModuleFieldConfig(nodeName = InboundDeliveryParty.NODENAME, nodeInstId = InboundDeliveryParty.PARTY_NODEINST_SOLD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InboundDeliveryParty soldFromOrg;

	@IServiceModuleFieldConfig(nodeName = InboundDeliveryParty.NODENAME, nodeInstId = InboundDeliveryParty.PARTY_NODEINST_PROD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InboundDeliveryParty productionOrg;

	@IServiceModuleFieldConfig(nodeName = InboundDeliveryParty.NODENAME, nodeInstId = InboundDeliveryParty.PARTY_NODEINST_SOLD_CUSTOMER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InboundDeliveryParty soldToCustomer;

	public InboundDelivery getInboundDelivery() {
		return this.inboundDelivery;
	}

	public void setInboundDelivery(InboundDelivery inboundDelivery) {
		this.inboundDelivery = inboundDelivery;
	}

	public List<InboundItemServiceModel> getInboundItemList() {
		return inboundItemList;
	}

	public void setInboundItemList(List<InboundItemServiceModel> inboundItemList) {
		this.inboundItemList = inboundItemList;
	}

	public List<ServiceEntityNode> getInboundDeliveryAttachmentList() {
		return inboundDeliveryAttachmentList;
	}

	public void setInboundDeliveryAttachmentList(
			List<ServiceEntityNode> inboundDeliveryAttachmentList) {
		this.inboundDeliveryAttachmentList = inboundDeliveryAttachmentList;
	}

	public InboundDeliveryActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(InboundDeliveryActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public InboundDeliveryActionNode getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(InboundDeliveryActionNode rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public InboundDeliveryActionNode getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(InboundDeliveryActionNode deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public InboundDeliveryParty getPurchaseToOrg() {
		return purchaseToOrg;
	}

	public void setPurchaseToOrg(InboundDeliveryParty purchaseToOrg) {
		this.purchaseToOrg = purchaseToOrg;
	}

	public InboundDeliveryParty getPurchaseFromSupplier() {
		return purchaseFromSupplier;
	}

	public void setPurchaseFromSupplier(InboundDeliveryParty purchaseFromSupplier) {
		this.purchaseFromSupplier = purchaseFromSupplier;
	}

	public InboundDeliveryParty getSoldFromOrg() {
		return soldFromOrg;
	}

	public void setSoldFromOrg(InboundDeliveryParty soldFromOrg) {
		this.soldFromOrg = soldFromOrg;
	}

	public InboundDeliveryParty getProductionOrg() {
		return productionOrg;
	}

	public void setProductionOrg(InboundDeliveryParty productionOrg) {
		this.productionOrg = productionOrg;
	}

	public InboundDeliveryParty getSoldToCustomer() {
		return soldToCustomer;
	}

	public void setSoldToCustomer(InboundDeliveryParty soldToCustomer) {
		this.soldToCustomer = soldToCustomer;
	}
}
