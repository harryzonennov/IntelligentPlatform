package com.company.IntelligentPlatform.sales.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.sales.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class SalesContractServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SalesContract.NODENAME, nodeInstId = SalesContract.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected SalesContract salesContract;

	@IServiceModuleFieldConfig(nodeName = SalesContractActionNode.NODENAME, nodeInstId =
			SalesContractActionNode.NODEINST_ACTION_APPROVE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected SalesContractActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = SalesContractActionNode.NODENAME, nodeInstId =
			SalesContractActionNode.NODEINST_ACTION_SUBMIT, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected SalesContractActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = SalesContractActionNode.NODENAME, nodeInstId =
			SalesContractActionNode.NODEINST_ACTION_INPLAN, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected SalesContractActionNode inplanBy;

	@IServiceModuleFieldConfig(nodeName = SalesContractActionNode.NODENAME, nodeInstId =
			SalesContractActionNode.NODEINST_ACTION_DELIVERY_DONE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected SalesContractActionNode deliveryDoneBy;

	@IServiceModuleFieldConfig(nodeName = SalesContractActionNode.NODENAME, nodeInstId =
			SalesContractActionNode.NODEINST_ACTION_PROCESS_DONE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected SalesContractActionNode processDoneBy;

	@IServiceModuleFieldConfig(nodeName = SalesContractParty.NODENAME, nodeInstId =
			SalesContractParty.PARTY_NODEINST_SOLD_CUSTOMER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected SalesContractParty soldToCustomer;

	@IServiceModuleFieldConfig(nodeName = SalesContractParty.NODENAME, nodeInstId = SalesContractParty.PARTY_NODEINST_SOLD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected SalesContractParty soldFromOrg;

	@IServiceModuleFieldConfig(nodeName = SalesContractMaterialItem.NODENAME, nodeInstId = SalesContractMaterialItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<SalesContractMaterialItemServiceModel> salesContractMaterialItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = SalesContractAttachment.NODENAME, nodeInstId = SalesContractAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> salesContractAttachmentList = new ArrayList<>();

	public List<SalesContractMaterialItemServiceModel> getSalesContractMaterialItemList() {
		return this.salesContractMaterialItemList;
	}

	public void setSalesContractMaterialItemList(
			List<SalesContractMaterialItemServiceModel> salesContractMaterialItemList) {
		this.salesContractMaterialItemList = salesContractMaterialItemList;
	}

	public SalesContractParty getSoldToCustomer() {
		return soldToCustomer;
	}

	public void setSoldToCustomer(SalesContractParty soldToCustomer) {
		this.soldToCustomer = soldToCustomer;
	}

	public SalesContractParty getSoldFromOrg() {
		return soldFromOrg;
	}

	public void setSoldFromOrg(SalesContractParty soldFromOrg) {
		this.soldFromOrg = soldFromOrg;
	}

	public SalesContract getSalesContract() {
		return this.salesContract;
	}

	public void setSalesContract(SalesContract salesContract) {
		this.salesContract = salesContract;
	}

	public List<ServiceEntityNode> getSalesContractAttachmentList() {
		return this.salesContractAttachmentList;
	}

	public void setSalesContractAttachmentList(
			List<ServiceEntityNode> salesContractAttachmentList) {
		this.salesContractAttachmentList = salesContractAttachmentList;
	}

	public SalesContractActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(SalesContractActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public SalesContractActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(SalesContractActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public SalesContractActionNode getInplanBy() {
		return inplanBy;
	}

	public void setInplanBy(SalesContractActionNode inplanBy) {
		this.inplanBy = inplanBy;
	}

	public SalesContractActionNode getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(SalesContractActionNode deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public SalesContractActionNode getProcessDoneBy() {
		return processDoneBy;
	}

	public void setProcessDoneBy(SalesContractActionNode processDoneBy) {
		this.processDoneBy = processDoneBy;
	}
}