package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.InventoryCheckAttachment;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckItem;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckOrder;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckOrderActionNode;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class InventoryCheckOrderServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = InventoryCheckOrder.NODENAME, nodeInstId = InventoryCheckOrder.SENAME)
	protected InventoryCheckOrderUIModel inventoryCheckOrderUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InventoryCheckOrderActionNode.NODENAME, nodeInstId =
			InventoryCheckOrderActionNode.NODEINST_ACTION_SUBMIT)
	protected InventoryCheckOrderActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = InventoryCheckOrderActionNode.NODENAME, nodeInstId =
			InventoryCheckOrderActionNode.NODEINST_ACTION_REVOKE_SUBMIT)
	protected InventoryCheckOrderActionNodeUIModel revokeSubmittedBy;

	@IServiceUIModuleFieldConfig(nodeName = InventoryCheckOrderActionNode.NODENAME, nodeInstId =
			InventoryCheckOrderActionNode.NODEINST_ACTION_APPROVE)
	protected InventoryCheckOrderActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = InventoryCheckOrderActionNode.NODENAME, nodeInstId =
			InventoryCheckOrderActionNode.NODEINST_ACTION_REJECT_APPROVE)
	protected InventoryCheckOrderActionNodeUIModel rejectApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = InventoryCheckOrderActionNode.NODENAME, nodeInstId = InventoryCheckOrderActionNode.NODEINST_ACTION_COUNTAPPROVE)
	protected InventoryCheckOrderActionNodeUIModel countApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = InventoryCheckOrderActionNode.NODENAME, nodeInstId =
			InventoryCheckOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected InventoryCheckOrderActionNodeUIModel deliveryDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = InventoryCheckItem.NODENAME, nodeInstId = InventoryCheckItem.NODENAME)
	protected List<InventoryCheckItemServiceUIModel> inventoryCheckItemUIModelList = new ArrayList<>();
	
	@IServiceUIModuleFieldConfig(nodeName = InventoryCheckAttachment.NODENAME, nodeInstId = InventoryCheckAttachment.NODENAME)
	protected List<InventoryCheckAttachmentUIModel> inventoryCheckAttachmentUIModelList = new ArrayList<>();

	public InventoryCheckOrderUIModel getInventoryCheckOrderUIModel() {
		return this.inventoryCheckOrderUIModel;
	}

	public void setInventoryCheckOrderUIModel(
			InventoryCheckOrderUIModel inventoryCheckOrderUIModel) {
		this.inventoryCheckOrderUIModel = inventoryCheckOrderUIModel;
	}


	public List<InventoryCheckAttachmentUIModel> getInventoryCheckAttachmentUIModelList() {
		return inventoryCheckAttachmentUIModelList;
	}

	public void setInventoryCheckAttachmentUIModelList(
			List<InventoryCheckAttachmentUIModel> inventoryCheckAttachmentUIModelList) {
		this.inventoryCheckAttachmentUIModelList = inventoryCheckAttachmentUIModelList;
	}

	public InventoryCheckOrderActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(InventoryCheckOrderActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public InventoryCheckOrderActionNodeUIModel getRevokeSubmittedBy() {
		return revokeSubmittedBy;
	}

	public void setRevokeSubmittedBy(InventoryCheckOrderActionNodeUIModel revokeSubmittedBy) {
		this.revokeSubmittedBy = revokeSubmittedBy;
	}

	public InventoryCheckOrderActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(InventoryCheckOrderActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public InventoryCheckOrderActionNodeUIModel getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(InventoryCheckOrderActionNodeUIModel rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public InventoryCheckOrderActionNodeUIModel getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(InventoryCheckOrderActionNodeUIModel countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public InventoryCheckOrderActionNodeUIModel getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(InventoryCheckOrderActionNodeUIModel deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public List<InventoryCheckItemServiceUIModel> getInventoryCheckItemUIModelList() {
		return inventoryCheckItemUIModelList;
	}

	public void setInventoryCheckItemUIModelList(List<InventoryCheckItemServiceUIModel> inventoryCheckItemUIModelList) {
		this.inventoryCheckItemUIModelList = inventoryCheckItemUIModelList;
	}
}
