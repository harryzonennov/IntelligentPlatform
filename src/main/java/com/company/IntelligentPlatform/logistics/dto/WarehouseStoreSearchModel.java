package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.QualityInspectOrder;
import com.company.IntelligentPlatform.logistics.model.WarehouseStore;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemParty;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.dto.AccountSearchSubModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.service.SearchDocConfigHelper;
import com.company.IntelligentPlatform.common.dto.WarehouseSubSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.DocEmbedMaterialSKUSearchModel;
import com.company.IntelligentPlatform.common.dto.DocFlowNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;

/**
 * WarehouseStoreItem UI Model
 ** 
 * @author
 * @date Mon Sep 14 11:02:28 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class WarehouseStoreSearchModel extends SEUIComModel {

	@BSearchGroupConfig(groupInstId = QualityInspectOrder.SENAME)
	protected ServiceDocSearchHeaderModel headerModel;

	@BSearchGroupConfig(groupInstId = QualityInspectOrder.SENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_PREV_DOC)
	protected DocFlowNodeSearchModel prevDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_PREV_PROF_DOC)
	protected DocFlowNodeSearchModel prevProfDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_NEXT_PROF_DOC)
	protected DocFlowNodeSearchModel nextProfDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_NEXT_DOC)
	protected DocFlowNodeSearchModel nextDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_BY_DOC)
	protected DocFlowNodeSearchModel reservedByDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_TARGET_DOC)
	protected DocFlowNodeSearchModel reservedTargetDoc;

	@BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
	protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

	@BSearchFieldConfig(fieldName = "refMaterialTemplateUUID", nodeName = WarehouseStoreItem.NODENAME, seName = WarehouseStore.SENAME, nodeInstID = WarehouseStoreItem.NODENAME)
	protected String refMaterialTemplateUUID;

	@BSearchFieldConfig(fieldName = "materialStatus", nodeName = WarehouseStoreItem.NODENAME, seName = WarehouseStore.SENAME, nodeInstID = WarehouseStoreItem.NODENAME)
	protected int materialStatus;

	@BSearchGroupConfig(groupInstId = Warehouse.SENAME)
	protected WarehouseSubSearchModel refWarehouse;

	@BSearchFieldConfig(fieldName = "refShelfNumberId", nodeName = WarehouseStoreItem.NODENAME, seName = WarehouseStore.SENAME, nodeInstID = WarehouseStoreItem.NODENAME)
	protected String refShelfNumberId;

	@BSearchGroupConfig(groupInstId = WarehouseStoreItemParty.PARTY_NODEINST_PUR_ORG)
	protected AccountSearchSubModel purchaseToOrg;

	@BSearchGroupConfig(groupInstId = WarehouseStoreItemParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected AccountSearchSubModel purchaseFromSupplier;

	@BSearchGroupConfig(groupInstId = WarehouseStoreItemParty.PARTY_NODEINST_PROD_ORG)
	protected AccountSearchSubModel productionOrg;

	@BSearchGroupConfig(groupInstId = WarehouseStoreItemParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected AccountSearchSubModel soldToCustomer;

	@BSearchGroupConfig(groupInstId = WarehouseStoreItemParty.PARTY_NODEINST_SOLD_ORG)
	protected AccountSearchSubModel soldFromOrg;

	public WarehouseStoreSearchModel() {		
		super();
	}

	public String getRefMaterialTemplateUUID() {
		return refMaterialTemplateUUID;
	}

	public void setRefMaterialTemplateUUID(String refMaterialTemplateUUID) {
		this.refMaterialTemplateUUID = refMaterialTemplateUUID;
	}

	public DocEmbedMaterialSKUSearchModel getItemMaterialSKU() {
		return itemMaterialSKU;
	}

	public void setItemMaterialSKU(DocEmbedMaterialSKUSearchModel itemMaterialSKU) {
		this.itemMaterialSKU = itemMaterialSKU;
	}

	public WarehouseSubSearchModel getRefWarehouse() {
		return refWarehouse;
	}

	public void setRefWarehouse(WarehouseSubSearchModel refWarehouse) {
		this.refWarehouse = refWarehouse;
	}

	public String getRefShelfNumberId() {
		return refShelfNumberId;
	}

	public void setRefShelfNumberId(String refShelfNumberId) {
		this.refShelfNumberId = refShelfNumberId;
	}

	public ServiceDocSearchHeaderModel getHeaderModel() {
		return headerModel;
	}

	public void setHeaderModel(ServiceDocSearchHeaderModel headerModel) {
		this.headerModel = headerModel;
	}

	public ServiceEntityCreateUpdateSearchModel getCreatedUpdateModel() {
		return createdUpdateModel;
	}

	public void setCreatedUpdateModel(ServiceEntityCreateUpdateSearchModel createdUpdateModel) {
		this.createdUpdateModel = createdUpdateModel;
	}

	public DocFlowNodeSearchModel getPrevDoc() {
		return prevDoc;
	}

	public void setPrevDoc(DocFlowNodeSearchModel prevDoc) {
		this.prevDoc = prevDoc;
	}

	public DocFlowNodeSearchModel getPrevProfDoc() {
		return prevProfDoc;
	}

	public void setPrevProfDoc(DocFlowNodeSearchModel prevProfDoc) {
		this.prevProfDoc = prevProfDoc;
	}

	public DocFlowNodeSearchModel getNextProfDoc() {
		return nextProfDoc;
	}

	public void setNextProfDoc(DocFlowNodeSearchModel nextProfDoc) {
		this.nextProfDoc = nextProfDoc;
	}

	public DocFlowNodeSearchModel getNextDoc() {
		return nextDoc;
	}

	public void setNextDoc(DocFlowNodeSearchModel nextDoc) {
		this.nextDoc = nextDoc;
	}

	public int getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(int materialStatus) {
		this.materialStatus = materialStatus;
	}

	public AccountSearchSubModel getPurchaseToOrg() {
		return purchaseToOrg;
	}

	public void setPurchaseToOrg(AccountSearchSubModel purchaseToOrg) {
		this.purchaseToOrg = purchaseToOrg;
	}

	public AccountSearchSubModel getPurchaseFromSupplier() {
		return purchaseFromSupplier;
	}

	public void setPurchaseFromSupplier(AccountSearchSubModel purchaseFromSupplier) {
		this.purchaseFromSupplier = purchaseFromSupplier;
	}

	public AccountSearchSubModel getProductionOrg() {
		return productionOrg;
	}

	public void setProductionOrg(AccountSearchSubModel productionOrg) {
		this.productionOrg = productionOrg;
	}

	public AccountSearchSubModel getSoldToCustomer() {
		return soldToCustomer;
	}

	public void setSoldToCustomer(AccountSearchSubModel soldToCustomer) {
		this.soldToCustomer = soldToCustomer;
	}

	public AccountSearchSubModel getSoldFromOrg() {
		return soldFromOrg;
	}

	public void setSoldFromOrg(AccountSearchSubModel soldFromOrg) {
		this.soldFromOrg = soldFromOrg;
	}

	public DocFlowNodeSearchModel getReservedByDoc() {
		return reservedByDoc;
	}

	public void setReservedByDoc(DocFlowNodeSearchModel reservedByDoc) {
		this.reservedByDoc = reservedByDoc;
	}

	public DocFlowNodeSearchModel getReservedTargetDoc() {
		return reservedTargetDoc;
	}

	public void setReservedTargetDoc(DocFlowNodeSearchModel reservedTargetDoc) {
		this.reservedTargetDoc = reservedTargetDoc;
	}
}
