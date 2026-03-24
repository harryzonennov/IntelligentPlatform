package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class WasteProcessOrderServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = WasteProcessOrder.NODENAME, nodeInstId = WasteProcessOrder.SENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected WasteProcessOrder wasteProcessOrder;

	@IServiceModuleFieldConfig(nodeName = WasteProcessOrderActionNode.NODENAME, nodeInstId =
			WasteProcessOrderActionNode.NODEINST_ACTION_APPROVE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected WasteProcessOrderActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = WasteProcessOrderActionNode.NODENAME, nodeInstId =
			WasteProcessOrderActionNode.NODEINST_ACTION_COUNTAPPROVE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected WasteProcessOrderActionNode deliveryDoneBy;

	@IServiceModuleFieldConfig(nodeName = WasteProcessOrderActionNode.NODENAME, nodeInstId =
			WasteProcessOrderActionNode.NODEINST_ACTION_SUBMIT, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected WasteProcessOrderActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = WasteProcessOrderActionNode.NODENAME, nodeInstId =
			WasteProcessOrderActionNode.NODEINST_ACTION_PROCESS_DONE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected WasteProcessOrderActionNode processDone;

	@IServiceModuleFieldConfig(nodeName = WasteProcessOrderParty.NODENAME, nodeInstId = WasteProcessOrderParty.PARTY_NODEINST_SOLD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected WasteProcessOrderParty soldFromOrg;

	@IServiceModuleFieldConfig(nodeName = WasteProcessOrderParty.NODENAME, nodeInstId = WasteProcessOrderParty.PARTY_NODEINST_SOLD_CUSTOMER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected WasteProcessOrderParty soldToCustomer;

	@IServiceModuleFieldConfig(nodeName = WasteProcessMaterialItem.NODENAME, nodeInstId = WasteProcessMaterialItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<WasteProcessMaterialItemServiceModel> wasteProcessMaterialItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = WasteProcessOrderAttachment.NODENAME, nodeInstId = WasteProcessOrderAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> wasteProcessOrderAttachmentList = new ArrayList<>();

	public List<WasteProcessMaterialItemServiceModel> getWasteProcessMaterialItemList() {
		return this.wasteProcessMaterialItemList;
	}

	public void setWasteProcessMaterialItemList(
			List<WasteProcessMaterialItemServiceModel> wasteProcessMaterialItemList) {
		this.wasteProcessMaterialItemList = wasteProcessMaterialItemList;
	}

	public WasteProcessOrderActionNode getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(WasteProcessOrderActionNode deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public WasteProcessOrderParty getSoldFromOrg() {
		return soldFromOrg;
	}

	public void setSoldFromOrg(WasteProcessOrderParty soldFromOrg) {
		this.soldFromOrg = soldFromOrg;
	}

	public WasteProcessOrderParty getSoldToCustomer() {
		return soldToCustomer;
	}

	public void setSoldToCustomer(WasteProcessOrderParty soldToCustomer) {
		this.soldToCustomer = soldToCustomer;
	}

	public WasteProcessOrder getWasteProcessOrder() {
		return this.wasteProcessOrder;
	}

	public void setWasteProcessOrder(WasteProcessOrder wasteProcessOrder) {
		this.wasteProcessOrder = wasteProcessOrder;
	}

	public List<ServiceEntityNode> getWasteProcessOrderAttachmentList() {
		return this.wasteProcessOrderAttachmentList;
	}

	public void setWasteProcessOrderAttachmentList(
			List<ServiceEntityNode> wasteProcessOrderAttachmentList) {
		this.wasteProcessOrderAttachmentList = wasteProcessOrderAttachmentList;
	}

	public WasteProcessOrderActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(WasteProcessOrderActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public WasteProcessOrderActionNode getProcessDone() {
		return processDone;
	}

	public void setProcessDone(WasteProcessOrderActionNode processDone) {
		this.processDone = processDone;
	}

	public WasteProcessOrderActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(WasteProcessOrderActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

}
