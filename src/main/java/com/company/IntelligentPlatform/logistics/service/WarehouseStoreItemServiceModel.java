package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemParty;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class WarehouseStoreItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = WarehouseStoreItem.NODENAME, nodeInstId = WarehouseStoreItem.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected WarehouseStoreItem warehouseStoreItem;

	@IServiceModuleFieldConfig(nodeName = WarehouseStoreItemLog.NODENAME, nodeInstId = WarehouseStoreItemLog.NODENAME)
	protected List<WarehouseStoreItemLogServiceModel> warehouseStoreItemLogList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = WarehouseStoreItemParty.NODENAME, nodeInstId = WarehouseStoreItemParty.PARTY_NODEINST_PUR_SUPPLIER,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected WarehouseStoreItemParty corporateSupplierParty;

	@IServiceModuleFieldConfig(nodeName = WarehouseStoreItemParty.NODENAME, nodeInstId = WarehouseStoreItemParty.PARTY_NODEINST_PUR_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected WarehouseStoreItemParty purchaseOrgParty;

	@IServiceModuleFieldConfig(nodeName = WarehouseStoreItemParty.NODENAME, nodeInstId =
			WarehouseStoreItemParty.PARTY_NODEINST_PROD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected WarehouseStoreItemParty productionOrgParty;

	@IServiceModuleFieldConfig(nodeName = WarehouseStoreItemParty.NODENAME, nodeInstId =
			WarehouseStoreItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected WarehouseStoreItemParty corporateCustomerParty;

	@IServiceModuleFieldConfig(nodeName = WarehouseStoreItemParty.NODENAME, nodeInstId =
			WarehouseStoreItemParty.PARTY_NODEINST_SOLD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected WarehouseStoreItemParty salesOrganizationParty;

	public WarehouseStoreItem getWarehouseStoreItem() {
		return this.warehouseStoreItem;
	}

	public void setWarehouseStoreItem(WarehouseStoreItem warehouseStoreItem) {
		this.warehouseStoreItem = warehouseStoreItem;
	}

	public List<WarehouseStoreItemLogServiceModel> getWarehouseStoreItemLogList() {
		return this.warehouseStoreItemLogList;
	}

	public void setWarehouseStoreItemLogList(
			List<WarehouseStoreItemLogServiceModel> warehouseStoreItemLogList) {
		this.warehouseStoreItemLogList = warehouseStoreItemLogList;
	}

	public WarehouseStoreItemParty getCorporateSupplierParty() {
		return corporateSupplierParty;
	}

	public void setCorporateSupplierParty(WarehouseStoreItemParty corporateSupplierParty) {
		this.corporateSupplierParty = corporateSupplierParty;
	}

	public WarehouseStoreItemParty getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(WarehouseStoreItemParty purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public WarehouseStoreItemParty getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(WarehouseStoreItemParty productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public WarehouseStoreItemParty getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(WarehouseStoreItemParty corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public WarehouseStoreItemParty getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(WarehouseStoreItemParty salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}
