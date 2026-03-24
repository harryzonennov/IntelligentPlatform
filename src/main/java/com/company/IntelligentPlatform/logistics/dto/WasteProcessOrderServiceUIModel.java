package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class WasteProcessOrderServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = WasteProcessOrder.NODENAME, nodeInstId = WasteProcessOrder.SENAME)
	protected WasteProcessOrderUIModel wasteProcessOrderUIModel;

	@IServiceUIModuleFieldConfig(nodeName = WasteProcessOrderParty.NODENAME, nodeInstId = WasteProcessOrderParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected WasteProcessOrderPartyUIModel soldToCustomerUIModel;

	@IServiceUIModuleFieldConfig(nodeName = WasteProcessOrderParty.NODENAME, nodeInstId = WasteProcessOrderParty.PARTY_NODEINST_SOLD_ORG)
	protected WasteProcessOrderPartyUIModel soldFromOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = WasteProcessOrderActionNode.NODENAME, nodeInstId =
			WasteProcessOrderActionNode.NODEINST_ACTION_APPROVE)
	protected WasteProcessOrderActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = WasteProcessOrderActionNode.NODENAME, nodeInstId =
			WasteProcessOrderActionNode.NODEINST_ACTION_SUBMIT)
	protected WasteProcessOrderActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = WasteProcessOrderActionNode.NODENAME, nodeInstId = WasteProcessOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected WasteProcessOrderActionNodeUIModel deliveryDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = WasteProcessOrderActionNode.NODENAME, nodeInstId =
			WasteProcessOrderActionNode.NODEINST_ACTION_PROCESS_DONE)
	protected WasteProcessOrderActionNodeUIModel processDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = WasteProcessMaterialItem.NODENAME, nodeInstId = WasteProcessMaterialItem.NODENAME)
	protected List<WasteProcessMaterialItemServiceUIModel> wasteProcessMaterialItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = WasteProcessOrderAttachment.NODENAME, nodeInstId = WasteProcessOrderAttachment.NODENAME)
	protected List<WasteProcessOrderAttachmentUIModel> wasteProcessOrderAttachmentUIModelList = new ArrayList<>();

	public WasteProcessOrderUIModel getWasteProcessOrderUIModel() {
		return this.wasteProcessOrderUIModel;
	}

	public void setWasteProcessOrderUIModel(
			WasteProcessOrderUIModel wasteProcessOrderUIModel) {
		this.wasteProcessOrderUIModel = wasteProcessOrderUIModel;
	}

	public WasteProcessOrderActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(WasteProcessOrderActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public WasteProcessOrderActionNodeUIModel getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(WasteProcessOrderActionNodeUIModel deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public WasteProcessOrderActionNodeUIModel getProcessDoneBy() {
		return processDoneBy;
	}

	public void setProcessDoneBy(WasteProcessOrderActionNodeUIModel processDoneBy) {
		this.processDoneBy = processDoneBy;
	}

	public List<WasteProcessMaterialItemServiceUIModel> getWasteProcessMaterialItemUIModelList() {
		return this.wasteProcessMaterialItemUIModelList;
	}

	public void setWasteProcessMaterialItemUIModelList(
			List<WasteProcessMaterialItemServiceUIModel> wasteProcessMaterialItemUIModelList) {
		this.wasteProcessMaterialItemUIModelList = wasteProcessMaterialItemUIModelList;
	}

	public List<WasteProcessOrderAttachmentUIModel> getWasteProcessOrderAttachmentUIModelList() {
		return this.wasteProcessOrderAttachmentUIModelList;
	}

	public void setWasteProcessOrderAttachmentUIModelList(
			List<WasteProcessOrderAttachmentUIModel> wasteProcessOrderAttachmentUIModelList) {
		this.wasteProcessOrderAttachmentUIModelList = wasteProcessOrderAttachmentUIModelList;
	}

	public WasteProcessOrderPartyUIModel getSoldToCustomerUIModel() {
		return soldToCustomerUIModel;
	}

	public void setSoldToCustomerUIModel(WasteProcessOrderPartyUIModel soldToCustomerUIModel) {
		this.soldToCustomerUIModel = soldToCustomerUIModel;
	}

	public WasteProcessOrderPartyUIModel getSoldFromOrgUIModel() {
		return soldFromOrgUIModel;
	}

	public void setSoldFromOrgUIModel(WasteProcessOrderPartyUIModel soldFromOrgUIModel) {
		this.soldFromOrgUIModel = soldFromOrgUIModel;
	}

	public WasteProcessOrderActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(WasteProcessOrderActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}
}
