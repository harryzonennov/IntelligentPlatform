package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class PurchaseContractServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = PurchaseContract.NODENAME, nodeInstId = PurchaseContract.SENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected PurchaseContract purchaseContract;

	@IServiceModuleFieldConfig(nodeName = PurchaseContractParty.NODENAME, nodeInstId = PurchaseContractParty.PARTY_NODEINST_PUR_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected PurchaseContractParty purchaseToOrg;

	@IServiceModuleFieldConfig(nodeName = PurchaseContractParty.NODENAME, nodeInstId = PurchaseContractParty.PARTY_NODEINST_PUR_SUPPLIER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected PurchaseContractParty purchaseFromSupplier;

	@IServiceModuleFieldConfig(nodeName = PurchaseContractActionNode.NODENAME,
			nodeInstId = PurchaseContractActionNode.NODEINST_ACTION_APPROVE, blockUpdate = true,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected PurchaseContractActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = PurchaseContractActionNode.NODENAME,
			nodeInstId = PurchaseContractActionNode.NODEINST_ACTION_DELIVERY_DONE, blockUpdate = true,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected PurchaseContractActionNode deliveryDoneBy;

	@IServiceModuleFieldConfig(nodeName = PurchaseContractActionNode.NODENAME, nodeInstId =
			PurchaseContractActionNode.NODEINST_ACTION_SUBMIT, blockUpdate = true,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected PurchaseContractActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = PurchaseContractActionNode.NODENAME, nodeInstId =
			PurchaseContractActionNode.NODEINST_ACTION_REJECT_APPROVE, blockUpdate = true,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected PurchaseContractActionNode rejectApprovedBy;

	@IServiceModuleFieldConfig(nodeName = PurchaseContractMaterialItem.NODENAME, nodeInstId = PurchaseContractMaterialItem.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<PurchaseContractMaterialItemServiceModel> purchaseContractMaterialItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = PurchaseContractAttachment.NODENAME, nodeInstId = PurchaseContractAttachment.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> purchaseContractAttachmentList = new ArrayList<>();

	public List<PurchaseContractMaterialItemServiceModel> getPurchaseContractMaterialItemList() {
		return this.purchaseContractMaterialItemList;
	}

	public void setPurchaseContractMaterialItemList(
			List<PurchaseContractMaterialItemServiceModel> purchaseContractMaterialItemList) {
		this.purchaseContractMaterialItemList = purchaseContractMaterialItemList;
	}

	public PurchaseContract getPurchaseContract() {
		return this.purchaseContract;
	}

	public void setPurchaseContract(PurchaseContract purchaseContract) {
		this.purchaseContract = purchaseContract;
	}

	public List<ServiceEntityNode> getPurchaseContractAttachmentList() {
		return this.purchaseContractAttachmentList;
	}

	public void setPurchaseContractAttachmentList(
			List<ServiceEntityNode> purchaseContractAttachmentList) {
		this.purchaseContractAttachmentList = purchaseContractAttachmentList;
	}

	public PurchaseContractParty getPurchaseToOrg() {
		return purchaseToOrg;
	}

	public void setPurchaseToOrg(PurchaseContractParty purchaseToOrg) {
		this.purchaseToOrg = purchaseToOrg;
	}

	public PurchaseContractParty getPurchaseFromSupplier() {
		return purchaseFromSupplier;
	}

	public void setPurchaseFromSupplier(PurchaseContractParty purchaseFromSupplier) {
		this.purchaseFromSupplier = purchaseFromSupplier;
	}

	public PurchaseContractActionNode getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(PurchaseContractActionNode rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public PurchaseContractActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(PurchaseContractActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public PurchaseContractActionNode getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(PurchaseContractActionNode deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public PurchaseContractActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(PurchaseContractActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

}
