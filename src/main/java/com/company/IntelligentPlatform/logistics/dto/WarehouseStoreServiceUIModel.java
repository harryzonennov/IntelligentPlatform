package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class WarehouseStoreServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStore.NODENAME, nodeInstId = WarehouseStore.SENAME)
	protected WarehouseStoreUIModel warehouseStoreUIModel;

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreItem.NODENAME, nodeInstId = WarehouseStoreItem.NODENAME)
	protected List<WarehouseStoreItemServiceUIModel> warehouseStoreItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreAttachment.NODENAME, nodeInstId = WarehouseStoreAttachment.NODENAME)
	protected List<WarehouseStoreAttachmentUIModel> warehouseStoreAttachmentUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreActionNode.NODENAME, nodeInstId =
			WarehouseStoreActionNode.NODEINST_ACTION_INSTOCK)
	protected WarehouseStoreActionNodeUIModel instockBy;

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreActionNode.NODENAME, nodeInstId =
			WarehouseStoreActionNode.NODEINST_ACTION_ARCHIVE)
	protected WarehouseStoreActionNodeUIModel archiveBy;

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreParty.NODENAME, nodeInstId = WarehouseStoreParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected WarehouseStorePartyUIModel corporateSupplierParty;

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreParty.NODENAME, nodeInstId = WarehouseStoreParty.PARTY_NODEINST_PUR_ORG)
	protected WarehouseStorePartyUIModel purchaseOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreParty.NODENAME, nodeInstId =
			WarehouseStoreParty.PARTY_NODEINST_PROD_ORG)
	protected WarehouseStorePartyUIModel productionOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreParty.NODENAME, nodeInstId =
			WarehouseStoreParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected WarehouseStorePartyUIModel corporateCustomerParty;

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreParty.NODENAME, nodeInstId =
			WarehouseStoreParty.PARTY_NODEINST_SOLD_ORG)
	protected WarehouseStorePartyUIModel salesOrganizationParty;

	public WarehouseStoreUIModel getWarehouseStoreUIModel() {
		return warehouseStoreUIModel;
	}

	public void setWarehouseStoreUIModel(WarehouseStoreUIModel warehouseStoreUIModel) {
		this.warehouseStoreUIModel = warehouseStoreUIModel;
	}

	public List<WarehouseStoreItemServiceUIModel> getWarehouseStoreItemUIModelList() {
		return warehouseStoreItemUIModelList;
	}

	public void setWarehouseStoreItemUIModelList(List<WarehouseStoreItemServiceUIModel> warehouseStoreItemUIModelList) {
		this.warehouseStoreItemUIModelList = warehouseStoreItemUIModelList;
	}

	public List<WarehouseStoreAttachmentUIModel> getWarehouseStoreAttachmentUIModelList() {
		return warehouseStoreAttachmentUIModelList;
	}

	public void setWarehouseStoreAttachmentUIModelList(
			List<WarehouseStoreAttachmentUIModel> warehouseStoreAttachmentUIModelList) {
		this.warehouseStoreAttachmentUIModelList = warehouseStoreAttachmentUIModelList;
	}

	public WarehouseStoreActionNodeUIModel getInstockBy() {
		return instockBy;
	}

	public void setInstockBy(WarehouseStoreActionNodeUIModel instockBy) {
		this.instockBy = instockBy;
	}

	public WarehouseStoreActionNodeUIModel getArchiveBy() {
		return archiveBy;
	}

	public void setArchiveBy(WarehouseStoreActionNodeUIModel archiveBy) {
		this.archiveBy = archiveBy;
	}

	public WarehouseStorePartyUIModel getCorporateSupplierParty() {
		return corporateSupplierParty;
	}

	public void setCorporateSupplierParty(WarehouseStorePartyUIModel corporateSupplierParty) {
		this.corporateSupplierParty = corporateSupplierParty;
	}

	public WarehouseStorePartyUIModel getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(WarehouseStorePartyUIModel purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public WarehouseStorePartyUIModel getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(WarehouseStorePartyUIModel productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public WarehouseStorePartyUIModel getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(WarehouseStorePartyUIModel corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public WarehouseStorePartyUIModel getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(WarehouseStorePartyUIModel salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}
