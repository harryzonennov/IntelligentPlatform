package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class InventoryTransferOrderServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = InventoryTransferOrder.NODENAME, nodeInstId = InventoryTransferOrder.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected InventoryTransferOrder inventoryTransferOrder;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferOrderActionNode.NODENAME, nodeInstId = InventoryTransferOrderActionNode.NODEINST_ACTION_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InventoryTransferOrderActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferOrderActionNode.NODENAME, nodeInstId = InventoryTransferOrderActionNode.NODEINST_ACTION_COUNTAPPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InventoryTransferOrderActionNode countApprovedBy;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferOrderActionNode.NODENAME, nodeInstId =
			InventoryTransferOrderActionNode.NODEINST_ACTION_REJECT_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InventoryTransferOrderActionNode rejectApprovedBy;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferOrderActionNode.NODENAME, nodeInstId =
			InventoryTransferOrderActionNode.NODEINST_ACTION_DELIVERY_DONE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InventoryTransferOrderActionNode deliveryDoneBy;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferItem.NODENAME, nodeInstId = InventoryTransferItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<InventoryTransferItemServiceModel> inventoryTransferItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = InventoryTransferOrderAttachment.NODENAME, nodeInstId = InventoryTransferOrderAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> inventoryTransferOrderAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = InventoryTransferOrderParty.NODENAME, nodeInstId = InventoryTransferOrderParty.PARTY_NODEINST_PUR_SUPPLIER,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InventoryTransferOrderParty corporateSupplierParty;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferOrderParty.NODENAME, nodeInstId = InventoryTransferOrderParty.PARTY_NODEINST_PUR_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InventoryTransferOrderParty purchaseOrgParty;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferOrderParty.NODENAME, nodeInstId =
			InventoryTransferOrderParty.PARTY_NODEINST_PROD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InventoryTransferOrderParty productionOrgParty;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferOrderParty.NODENAME, nodeInstId =
			InventoryTransferOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InventoryTransferOrderParty corporateCustomerParty;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferOrderParty.NODENAME, nodeInstId =
			InventoryTransferOrderParty.PARTY_NODEINST_SOLD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InventoryTransferOrderParty salesOrganizationParty;

	public InventoryTransferOrder getInventoryTransferOrder() {
		return this.inventoryTransferOrder;
	}

	public void setInventoryTransferOrder(
			InventoryTransferOrder inventoryTransferOrder) {
		this.inventoryTransferOrder = inventoryTransferOrder;
	}

	public InventoryTransferOrderActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(InventoryTransferOrderActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public InventoryTransferOrderActionNode getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(InventoryTransferOrderActionNode countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public InventoryTransferOrderActionNode getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(InventoryTransferOrderActionNode rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public InventoryTransferOrderActionNode getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(InventoryTransferOrderActionNode deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public List<InventoryTransferItemServiceModel> getInventoryTransferItemList() {
		return inventoryTransferItemList;
	}

	public void setInventoryTransferItemList(List<InventoryTransferItemServiceModel> inventoryTransferItemList) {
		this.inventoryTransferItemList = inventoryTransferItemList;
	}

	public List<ServiceEntityNode> getInventoryTransferOrderAttachmentList() {
		return inventoryTransferOrderAttachmentList;
	}

	public void setInventoryTransferOrderAttachmentList(
			List<ServiceEntityNode> inventoryTransferOrderAttachmentList) {
		this.inventoryTransferOrderAttachmentList = inventoryTransferOrderAttachmentList;
	}

	public InventoryTransferOrderParty getCorporateSupplierParty() {
		return corporateSupplierParty;
	}

	public void setCorporateSupplierParty(InventoryTransferOrderParty corporateSupplierParty) {
		this.corporateSupplierParty = corporateSupplierParty;
	}

	public InventoryTransferOrderParty getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(InventoryTransferOrderParty purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public InventoryTransferOrderParty getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(InventoryTransferOrderParty productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public InventoryTransferOrderParty getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(InventoryTransferOrderParty corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public InventoryTransferOrderParty getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(InventoryTransferOrderParty salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}
