package com.company.IntelligentPlatform.sales.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.sales.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class SalesReturnOrderServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SalesReturnOrder.NODENAME, nodeInstId = SalesReturnOrder.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected SalesReturnOrder salesReturnOrder;
	
	@IServiceModuleFieldConfig(nodeName = SalesReturnOrderParty.NODENAME, nodeInstId = SalesReturnOrderParty.PARTY_NODEINST_SOLD_CUSTOMER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected SalesReturnOrderParty soldToCustomer;
	
	@IServiceModuleFieldConfig(nodeName = SalesReturnOrderParty.NODENAME, nodeInstId = SalesReturnOrderParty.PARTY_NODEINST_SOLD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected SalesReturnOrderParty soldFromOrg;

	@IServiceModuleFieldConfig(nodeName = SalesReturnOrderActionNode.NODENAME,
			nodeInstId = SalesReturnOrderActionNode.NODEINST_ACTION_APPROVE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected SalesReturnOrderActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = SalesReturnOrderActionNode.NODENAME,
			nodeInstId = SalesReturnOrderActionNode.NODEINST_ACTION_DELIVERY_DONE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected SalesReturnOrderActionNode deliveryDoneBy;


	@IServiceModuleFieldConfig(nodeName = SalesReturnOrderActionNode.NODENAME, nodeInstId =
			SalesReturnOrderActionNode.NODEINST_ACTION_SUBMIT, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected SalesReturnOrderActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = SalesReturnMaterialItem.NODENAME, nodeInstId = SalesReturnMaterialItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<SalesReturnMaterialItemServiceModel> salesReturnMaterialItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = SalesReturnOrderAttachment.NODENAME, nodeInstId = SalesReturnOrderAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> salesReturnOrderAttachmentList = new ArrayList<>();

	public List<SalesReturnMaterialItemServiceModel> getSalesReturnMaterialItemList() {
		return salesReturnMaterialItemList;
	}

	public void setSalesReturnMaterialItemList(final List<SalesReturnMaterialItemServiceModel> salesReturnMaterialItemList) {
		this.salesReturnMaterialItemList = salesReturnMaterialItemList;
	}

	public SalesReturnOrder getSalesReturnOrder() {
		return salesReturnOrder;
	}

	public void setSalesReturnOrder(SalesReturnOrder salesReturnOrder) {
		this.salesReturnOrder = salesReturnOrder;
	}

	public SalesReturnOrderParty getSoldToCustomer() {
		return soldToCustomer;
	}

	public void setSoldToCustomer(SalesReturnOrderParty soldToCustomer) {
		this.soldToCustomer = soldToCustomer;
	}

	public SalesReturnOrderParty getSoldFromOrg() {
		return soldFromOrg;
	}

	public void setSoldFromOrg(SalesReturnOrderParty soldFromOrg) {
		this.soldFromOrg = soldFromOrg;
	}

	public List<ServiceEntityNode> getSalesReturnOrderAttachmentList() {
		return salesReturnOrderAttachmentList;
	}

	public void setSalesReturnOrderAttachmentList(List<ServiceEntityNode> salesReturnOrderAttachmentList) {
		this.salesReturnOrderAttachmentList = salesReturnOrderAttachmentList;
	}

	public SalesReturnOrderActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(SalesReturnOrderActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public SalesReturnOrderActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(SalesReturnOrderActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public SalesReturnOrderActionNode getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(SalesReturnOrderActionNode deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}
}