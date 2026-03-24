package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class OutboundDeliveryServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = OutboundDelivery.NODENAME, nodeInstId = OutboundDelivery.SENAME,
			docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected OutboundDelivery outboundDelivery;

	@IServiceModuleFieldConfig(nodeName = OutboundDeliveryActionNode.NODENAME, nodeInstId =
			OutboundDeliveryActionNode.NODEINST_ACTION_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected OutboundDeliveryActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = OutboundDeliveryActionNode.NODENAME, nodeInstId =
			OutboundDeliveryActionNode.NODEINST_ACTION_REJECT_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected OutboundDeliveryActionNode rejectApprovedBy;

	@IServiceModuleFieldConfig(nodeName = OutboundDeliveryActionNode.NODENAME, nodeInstId =
			OutboundDeliveryActionNode.NODEINST_ACTION_DELIVERY_DONE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected OutboundDeliveryActionNode deliveryDoneBy;

	@IServiceModuleFieldConfig(nodeName = OutboundItem.NODENAME, nodeInstId = OutboundItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<OutboundItemServiceModel> outboundItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = OutboundDeliveryAttachment.NODENAME, nodeInstId = OutboundDeliveryAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> outboundDeliveryAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = OutboundDeliveryParty.NODENAME, nodeInstId = OutboundDeliveryParty.PARTY_NODEINST_PUR_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected OutboundDeliveryParty purchaseToOrg;

	@IServiceModuleFieldConfig(nodeName = OutboundDeliveryParty.NODENAME, nodeInstId = OutboundDeliveryParty.PARTY_NODEINST_PUR_SUPPLIER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected OutboundDeliveryParty purchaseFromSupplier;

	@IServiceModuleFieldConfig(nodeName = OutboundDeliveryParty.NODENAME, nodeInstId = OutboundDeliveryParty.PARTY_NODEINST_SOLD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected OutboundDeliveryParty soldFromOrg;

	@IServiceModuleFieldConfig(nodeName = OutboundDeliveryParty.NODENAME, nodeInstId = OutboundDeliveryParty.PARTY_NODEINST_PROD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected OutboundDeliveryParty productionOrg;

	@IServiceModuleFieldConfig(nodeName = OutboundDeliveryParty.NODENAME, nodeInstId = OutboundDeliveryParty.PARTY_NODEINST_SOLD_CUSTOMER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected OutboundDeliveryParty soldToCustomer;

	public OutboundDelivery getOutboundDelivery() {
		return this.outboundDelivery;
	}

	public void setOutboundDelivery(OutboundDelivery outboundDelivery) {
		this.outboundDelivery = outboundDelivery;
	}

	public OutboundDeliveryActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(OutboundDeliveryActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public OutboundDeliveryActionNode getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(OutboundDeliveryActionNode rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public OutboundDeliveryActionNode getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(OutboundDeliveryActionNode deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public List<OutboundItemServiceModel> getOutboundItemList() {
		return outboundItemList;
	}

	public void setOutboundItemList(List<OutboundItemServiceModel> outboundItemList) {
		this.outboundItemList = outboundItemList;
	}

	public List<ServiceEntityNode> getOutboundDeliveryAttachmentList() {
		return outboundDeliveryAttachmentList;
	}

	public void setOutboundDeliveryAttachmentList(
			List<ServiceEntityNode> outboundDeliveryAttachmentList) {
		this.outboundDeliveryAttachmentList = outboundDeliveryAttachmentList;
	}

	public OutboundDeliveryParty getPurchaseToOrg() {
		return purchaseToOrg;
	}

	public void setPurchaseToOrg(OutboundDeliveryParty purchaseToOrg) {
		this.purchaseToOrg = purchaseToOrg;
	}

	public OutboundDeliveryParty getPurchaseFromSupplier() {
		return purchaseFromSupplier;
	}

	public void setPurchaseFromSupplier(OutboundDeliveryParty purchaseFromSupplier) {
		this.purchaseFromSupplier = purchaseFromSupplier;
	}

	public OutboundDeliveryParty getSoldFromOrg() {
		return soldFromOrg;
	}

	public void setSoldFromOrg(OutboundDeliveryParty soldFromOrg) {
		this.soldFromOrg = soldFromOrg;
	}

	public OutboundDeliveryParty getProductionOrg() {
		return productionOrg;
	}

	public void setProductionOrg(OutboundDeliveryParty productionOrg) {
		this.productionOrg = productionOrg;
	}

	public OutboundDeliveryParty getSoldToCustomer() {
		return soldToCustomer;
	}

	public void setSoldToCustomer(OutboundDeliveryParty soldToCustomer) {
		this.soldToCustomer = soldToCustomer;
	}
}
