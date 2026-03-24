package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemAttachment;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemParty;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class WarehouseStoreItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreItem.NODENAME, nodeInstId = WarehouseStoreItem.NODENAME)
	protected WarehouseStoreItemUIModel warehouseStoreItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreItemLog.NODENAME, nodeInstId = WarehouseStoreItemLog.NODENAME)
	protected List<WarehouseStoreItemLogServiceUIModel> warehouseStoreItemLogUIModelList = new ArrayList<>();
	
	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreItemAttachment.NODENAME, nodeInstId =
			WarehouseStoreItemAttachment.NODENAME)
	protected List<WarehouseStoreItemAttachmentUIModel> warehouseStoreItemAttachmentUIModelList =
			new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreItemParty.NODENAME, nodeInstId = WarehouseStoreItemParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected WarehouseStoreItemPartyUIModel corporateSupplierParty;

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreItemParty.NODENAME, nodeInstId = WarehouseStoreItemParty.PARTY_NODEINST_PUR_ORG)
	protected WarehouseStoreItemPartyUIModel purchaseOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreItemParty.NODENAME, nodeInstId =
			WarehouseStoreItemParty.PARTY_NODEINST_PROD_ORG)
	protected WarehouseStoreItemPartyUIModel productionOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreItemParty.NODENAME, nodeInstId =
			WarehouseStoreItemParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected WarehouseStoreItemPartyUIModel corporateCustomerParty;

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreItemParty.NODENAME, nodeInstId =
			WarehouseStoreItemParty.PARTY_NODEINST_SOLD_ORG)
	protected WarehouseStoreItemPartyUIModel salesOrganizationParty;

	public WarehouseStoreItemUIModel getWarehouseStoreItemUIModel() {
		return this.warehouseStoreItemUIModel;
	}

	public void setWarehouseStoreItemUIModel(
			WarehouseStoreItemUIModel warehouseStoreItemUIModel) {
		this.warehouseStoreItemUIModel = warehouseStoreItemUIModel;
	}

	public List<WarehouseStoreItemLogServiceUIModel> getWarehouseStoreItemLogUIModelList() {
		return this.warehouseStoreItemLogUIModelList;
	}

	public void setWarehouseStoreItemLogUIModelList(
			List<WarehouseStoreItemLogServiceUIModel> warehouseStoreItemLogUIModelList) {
		this.warehouseStoreItemLogUIModelList = warehouseStoreItemLogUIModelList;
	}

	public WarehouseStoreItemPartyUIModel getCorporateSupplierParty() {
		return corporateSupplierParty;
	}

	public void setCorporateSupplierParty(WarehouseStoreItemPartyUIModel corporateSupplierParty) {
		this.corporateSupplierParty = corporateSupplierParty;
	}

	public WarehouseStoreItemPartyUIModel getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(WarehouseStoreItemPartyUIModel purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public WarehouseStoreItemPartyUIModel getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(WarehouseStoreItemPartyUIModel productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public WarehouseStoreItemPartyUIModel getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(WarehouseStoreItemPartyUIModel corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public WarehouseStoreItemPartyUIModel getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(WarehouseStoreItemPartyUIModel salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}

	public List<WarehouseStoreItemAttachmentUIModel> getWarehouseStoreItemAttachmentUIModelList() {
		return warehouseStoreItemAttachmentUIModelList;
	}

	public void setWarehouseStoreItemAttachmentUIModelList(
			List<WarehouseStoreItemAttachmentUIModel> warehouseStoreItemAttachmentUIModelList) {
		this.warehouseStoreItemAttachmentUIModelList = warehouseStoreItemAttachmentUIModelList;
	}
}
