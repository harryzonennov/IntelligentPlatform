package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.dto.AccountSearchSubModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.service.SearchDocConfigHelper;
import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.DocActionNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.DocEmbedMaterialSKUSearchModel;
import com.company.IntelligentPlatform.common.dto.DocFlowNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;

@Component
public class PurchaseContractMaterialItemSearchModel extends SEUIComModel {

	@BSearchGroupConfig(groupInstId = PurchaseContract.SENAME)
	protected ServiceDocSearchHeaderModel parentDocHeaderModel;

	@BSearchGroupConfig(groupInstId = PurchaseContractMaterialItem.NODENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchGroupConfig(groupInstId = PurchaseContractParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected AccountSearchSubModel purchaseFromSupplier;

	@BSearchGroupConfig(groupInstId = PurchaseContractParty.PARTY_NODEINST_PUR_ORG)
	protected AccountSearchSubModel purchaseToOrg;

	@BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
	protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

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

	@BSearchGroupConfig(groupInstId = PurchaseContractActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel submittedBy;

	@BSearchGroupConfig(groupInstId = PurchaseContractActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel approvedBy;

	@BSearchGroupConfig(groupInstId = PurchaseContractActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected DocActionNodeSearchModel deliveryDoneBy;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = PurchaseContractMaterialItem.NODENAME, seName =
			PurchaseContractMaterialItem.SENAME,
			nodeInstID = PurchaseContractMaterialItem.NODENAME,  showOnUI = true)
	protected String uuid;
	
	@BSearchFieldConfig(fieldName = "itemStatus", nodeName = PurchaseContractMaterialItem.NODENAME, seName =
			PurchaseContractMaterialItem.SENAME,
			nodeInstID = PurchaseContractMaterialItem.NODENAME,  showOnUI = true)
	protected int itemStatus;

	public ServiceDocSearchHeaderModel getParentDocHeaderModel() {
		return parentDocHeaderModel;
	}

	public void setParentDocHeaderModel(ServiceDocSearchHeaderModel parentDocHeaderModel) {
		this.parentDocHeaderModel = parentDocHeaderModel;
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

	public DocActionNodeSearchModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(DocActionNodeSearchModel submittedBy) {
		this.submittedBy = submittedBy;
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

	@Override
	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public AccountSearchSubModel getPurchaseFromSupplier() {
		return purchaseFromSupplier;
	}

	public void setPurchaseFromSupplier(AccountSearchSubModel purchaseFromSupplier) {
		this.purchaseFromSupplier = purchaseFromSupplier;
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
