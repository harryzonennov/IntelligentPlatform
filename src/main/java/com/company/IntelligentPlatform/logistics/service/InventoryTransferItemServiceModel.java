package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.InventoryTransferItemParty;
import com.company.IntelligentPlatform.logistics.model.InventoryTransferItemAttachment;
import com.company.IntelligentPlatform.logistics.model.InventoryTransferItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class InventoryTransferItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = InventoryTransferItem.NODENAME, nodeInstId =
			InventoryTransferItem.NODENAME, docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected InventoryTransferItem inventoryTransferItem;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferItemAttachment.NODENAME, nodeInstId =
			InventoryTransferItemAttachment.NODENAME, docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> inventoryTransferItemAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = InventoryTransferItemParty.NODENAME, nodeInstId = InventoryTransferItemParty.PARTY_NODEINST_PUR_SUPPLIER,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InventoryTransferItemParty corporateSupplierParty;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferItemParty.NODENAME, nodeInstId =
			InventoryTransferItemParty.PARTY_NODEINST_PUR_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InventoryTransferItemParty purchaseOrgParty;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferItemParty.NODENAME, nodeInstId =
			InventoryTransferItemParty.PARTY_NODEINST_PROD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InventoryTransferItemParty productionOrgParty;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferItemParty.NODENAME, nodeInstId =
			InventoryTransferItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InventoryTransferItemParty corporateCustomerParty;

	@IServiceModuleFieldConfig(nodeName = InventoryTransferItemParty.NODENAME, nodeInstId =
			InventoryTransferItemParty.PARTY_NODEINST_SOLD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InventoryTransferItemParty salesOrganizationParty;

	public InventoryTransferItem getInventoryTransferItem() {
		return inventoryTransferItem;
	}

	public void setInventoryTransferItem(InventoryTransferItem inventoryTransferItem) {
		this.inventoryTransferItem = inventoryTransferItem;
	}

	public List<ServiceEntityNode> getInventoryTransferItemAttachmentList() {
		return inventoryTransferItemAttachmentList;
	}

	public void setInventoryTransferItemAttachmentList(List<ServiceEntityNode> inventoryTransferItemAttachmentList) {
		this.inventoryTransferItemAttachmentList = inventoryTransferItemAttachmentList;
	}

	public InventoryTransferItemParty getCorporateSupplierParty() {
		return corporateSupplierParty;
	}

	public void setCorporateSupplierParty(InventoryTransferItemParty corporateSupplierParty) {
		this.corporateSupplierParty = corporateSupplierParty;
	}

	public InventoryTransferItemParty getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(InventoryTransferItemParty purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public InventoryTransferItemParty getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(InventoryTransferItemParty productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public InventoryTransferItemParty getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(InventoryTransferItemParty corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public InventoryTransferItemParty getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(InventoryTransferItemParty salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}
