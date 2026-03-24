package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.InventoryTransferItemParty;
import com.company.IntelligentPlatform.logistics.model.InventoryTransferItemAttachment;
import com.company.IntelligentPlatform.logistics.model.InventoryTransferItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class InventoryTransferItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferItem.NODENAME, nodeInstId = InventoryTransferItem.NODENAME)
	protected InventoryTransferItemUIModel inventoryTransferItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferItemAttachment.NODENAME, nodeInstId =
			InventoryTransferItemAttachment.NODENAME)
	protected List<InventoryTransferItemAttachmentUIModel> inventoryTransferItemAttachmentUIModelList =
			new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferItemParty.NODENAME, nodeInstId = InventoryTransferItemParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected InventoryTransferItemPartyUIModel corporateSupplierParty;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferItemParty.NODENAME, nodeInstId = InventoryTransferItemParty.PARTY_NODEINST_PUR_ORG)
	protected InventoryTransferItemPartyUIModel purchaseOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferItemParty.NODENAME, nodeInstId =
			InventoryTransferItemParty.PARTY_NODEINST_PROD_ORG)
	protected InventoryTransferItemPartyUIModel productionOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferItemParty.NODENAME, nodeInstId =
			InventoryTransferItemParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected InventoryTransferItemPartyUIModel corporateCustomerParty;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferItemParty.NODENAME, nodeInstId =
			InventoryTransferItemParty.PARTY_NODEINST_SOLD_ORG)
	protected InventoryTransferItemPartyUIModel salesOrganizationParty;

	public InventoryTransferItemUIModel getInventoryTransferItemUIModel() {
		return inventoryTransferItemUIModel;
	}

	public void setInventoryTransferItemUIModel(InventoryTransferItemUIModel inventoryTransferItemUIModel) {
		this.inventoryTransferItemUIModel = inventoryTransferItemUIModel;
	}

	public List<InventoryTransferItemAttachmentUIModel> getInventoryTransferItemAttachmentUIModelList() {
		return inventoryTransferItemAttachmentUIModelList;
	}

	public void setInventoryTransferItemAttachmentUIModelList(
			List<InventoryTransferItemAttachmentUIModel> inventoryTransferItemAttachmentUIModelList) {
		this.inventoryTransferItemAttachmentUIModelList = inventoryTransferItemAttachmentUIModelList;
	}

	public InventoryTransferItemPartyUIModel getCorporateSupplierParty() {
		return corporateSupplierParty;
	}

	public void setCorporateSupplierParty(InventoryTransferItemPartyUIModel corporateSupplierParty) {
		this.corporateSupplierParty = corporateSupplierParty;
	}

	public InventoryTransferItemPartyUIModel getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(InventoryTransferItemPartyUIModel purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public InventoryTransferItemPartyUIModel getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(InventoryTransferItemPartyUIModel productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public InventoryTransferItemPartyUIModel getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(InventoryTransferItemPartyUIModel corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public InventoryTransferItemPartyUIModel getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(InventoryTransferItemPartyUIModel salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}
