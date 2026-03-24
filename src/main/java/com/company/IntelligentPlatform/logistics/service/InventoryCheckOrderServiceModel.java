package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.InventoryCheckAttachment;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckItem;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckOrder;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckOrderActionNode;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class InventoryCheckOrderServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = InventoryCheckOrder.NODENAME, nodeInstId = InventoryCheckOrder.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected InventoryCheckOrder inventoryCheckOrder;

	@IServiceModuleFieldConfig(nodeName = InventoryCheckOrderActionNode.NODENAME, nodeInstId = InventoryCheckOrderActionNode.NODEINST_ACTION_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InventoryCheckOrderActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = InventoryCheckOrderActionNode.NODENAME, nodeInstId = InventoryCheckOrderActionNode.NODEINST_ACTION_COUNTAPPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InventoryCheckOrderActionNode countApprovedBy;

	@IServiceModuleFieldConfig(nodeName = InventoryCheckOrderActionNode.NODENAME, nodeInstId =
			InventoryCheckOrderActionNode.NODEINST_ACTION_REJECT_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InventoryCheckOrderActionNode rejectApprovedBy;

	@IServiceModuleFieldConfig(nodeName = InventoryCheckOrderActionNode.NODENAME, nodeInstId =
			InventoryCheckOrderActionNode.NODEINST_ACTION_DELIVERY_DONE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InventoryCheckOrderActionNode deliveryDoneBy;

	@IServiceModuleFieldConfig(nodeName = InventoryCheckItem.NODENAME, nodeInstId = InventoryCheckItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<InventoryCheckItemServiceModel> inventoryCheckItemList = new ArrayList<>();
	
	@IServiceModuleFieldConfig(nodeName = InventoryCheckAttachment.NODENAME, nodeInstId = InventoryCheckAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> inventoryAttachmentList = new ArrayList<>();

	public InventoryCheckOrder getInventoryCheckOrder() {
		return this.inventoryCheckOrder;
	}

	public void setInventoryCheckOrder(InventoryCheckOrder inventoryCheckOrder) {
		this.inventoryCheckOrder = inventoryCheckOrder;
	}

	public InventoryCheckOrderActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(InventoryCheckOrderActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public InventoryCheckOrderActionNode getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(InventoryCheckOrderActionNode countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public InventoryCheckOrderActionNode getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(InventoryCheckOrderActionNode rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public InventoryCheckOrderActionNode getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(InventoryCheckOrderActionNode deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public List<InventoryCheckItemServiceModel> getInventoryCheckItemList() {
		return inventoryCheckItemList;
	}

	public void setInventoryCheckItemList(List<InventoryCheckItemServiceModel> inventoryCheckItemList) {
		this.inventoryCheckItemList = inventoryCheckItemList;
	}

	public List<ServiceEntityNode> getInventoryAttachmentList() {
		return inventoryAttachmentList;
	}

	public void setInventoryAttachmentList(
			List<ServiceEntityNode> inventoryAttachmentList) {
		this.inventoryAttachmentList = inventoryAttachmentList;
	}

}
