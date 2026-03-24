package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.dto.AccountSearchSubModel;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.SearchDocConfigHelper;
import com.company.IntelligentPlatform.common.dto.WarehouseSubSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.DocActionNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.DocEmbedMaterialSKUSearchModel;
import com.company.IntelligentPlatform.common.dto.DocFlowNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;

/**
 * OutboundDelivery UI Model
 ** 
 * @author
 * @date Sat Dec 28 23:34:17 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class OutboundItemSearchModel extends SEUIComModel {

	@BSearchGroupConfig(groupInstId = OutboundDelivery.SENAME)
	protected ServiceDocSearchHeaderModel parentDocHeaderModel;

	@BSearchGroupConfig(groupInstId = OutboundItem.NODENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
	protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

	@BSearchGroupConfig(groupInstId = OutboundDeliveryActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel approvedBy;

	@BSearchGroupConfig(groupInstId = OutboundDeliveryActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected DocActionNodeSearchModel deliveryDoneBy;

	@BSearchGroupConfig(groupInstId = OutboundDeliveryParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected AccountSearchSubModel purchaseFromSupplier;

	@BSearchGroupConfig(groupInstId = OutboundDeliveryParty.PARTY_NODEINST_PROD_ORG)
	protected AccountSearchSubModel productionOrg;

	@BSearchGroupConfig(groupInstId = OutboundDeliveryParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected AccountSearchSubModel soldToCustomer;

	@BSearchGroupConfig(groupInstId = OutboundDeliveryParty.PARTY_NODEINST_SOLD_ORG)
	protected AccountSearchSubModel soldFromOrg;

	@BSearchGroupConfig(groupInstId = OutboundDeliveryParty.PARTY_NODEINST_PUR_ORG)
	protected AccountSearchSubModel purchaseToOrg;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_PREV_DOC)
	protected DocFlowNodeSearchModel prevDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_NEXT_PROF_DOC)
	protected DocFlowNodeSearchModel nextProfDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_NEXT_DOC)
	protected DocFlowNodeSearchModel nextDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_BY_DOC)
	protected DocFlowNodeSearchModel reservedByDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_TARGET_DOC)
	protected DocFlowNodeSearchModel reservedTargetDoc;

	@BSearchGroupConfig(groupInstId = Warehouse.SENAME)
	protected WarehouseSubSearchModel refWarehouse;

	public ServiceDocSearchHeaderModel getParentDocHeaderModel() {
		return parentDocHeaderModel;
	}

	public void setParentDocHeaderModel(ServiceDocSearchHeaderModel parentDocHeaderModel) {
		this.parentDocHeaderModel = parentDocHeaderModel;
	}

	public WarehouseSubSearchModel getRefWarehouse() {
		return refWarehouse;
	}

	public void setRefWarehouse(WarehouseSubSearchModel refWarehouse) {
		this.refWarehouse = refWarehouse;
	}

	public ServiceEntityCreateUpdateSearchModel getCreatedUpdateModel() {
		return createdUpdateModel;
	}

	public void setCreatedUpdateModel(ServiceEntityCreateUpdateSearchModel createdUpdateModel) {
		this.createdUpdateModel = createdUpdateModel;
	}

	public DocEmbedMaterialSKUSearchModel getItemMaterialSKU() {
		return itemMaterialSKU;
	}

	public void setItemMaterialSKU(DocEmbedMaterialSKUSearchModel itemMaterialSKU) {
		this.itemMaterialSKU = itemMaterialSKU;
	}

	public DocActionNodeSearchModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(DocActionNodeSearchModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public DocActionNodeSearchModel getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(DocActionNodeSearchModel deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
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

	public AccountSearchSubModel getPurchaseToOrg() {
		return purchaseToOrg;
	}

	public void setPurchaseToOrg(AccountSearchSubModel purchaseToOrg) {
		this.purchaseToOrg = purchaseToOrg;
	}

	public DocFlowNodeSearchModel getPrevDoc() {
		return prevDoc;
	}

	public void setPrevDoc(DocFlowNodeSearchModel prevDoc) {
		this.prevDoc = prevDoc;
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
