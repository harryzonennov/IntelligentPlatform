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

import java.util.Date;
import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.DocActionNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.DocEmbedMaterialSKUSearchModel;
import com.company.IntelligentPlatform.common.dto.DocFlowNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;

@Component
public class PurchaseReturnMaterialItemSearchModel extends SEUIComModel {

	@BSearchGroupConfig(groupInstId = PurchaseReturnOrder.SENAME)
	protected ServiceDocSearchHeaderModel parentDocHeaderModel;

	@BSearchGroupConfig(groupInstId = PurchaseReturnMaterialItem.NODENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchGroupConfig(groupInstId = PurchaseReturnOrderParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected AccountSearchSubModel purchaseFromSupplier;

	@BSearchGroupConfig(groupInstId = PurchaseReturnOrderParty.PARTY_NODEINST_PUR_ORG)
	protected AccountSearchSubModel purchaseToOrg;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_PREV_DOC)
	protected DocFlowNodeSearchModel prevDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_PREV_PROF_DOC)
	protected DocFlowNodeSearchModel prevProfDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_NEXT_PROF_DOC)
	protected DocFlowNodeSearchModel nextProfDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_NEXT_DOC)
	protected DocFlowNodeSearchModel nextDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_TARGET_DOC)
	protected DocFlowNodeSearchModel reservedTargetDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_BY_DOC)
	protected DocFlowNodeSearchModel reservedByDoc;

	@BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
	protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

	@BSearchGroupConfig(groupInstId = PurchaseReturnOrderActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel submittedBy;

	@BSearchGroupConfig(groupInstId = PurchaseReturnOrderActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel approvedBy;

	@BSearchGroupConfig(groupInstId = PurchaseReturnOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected DocActionNodeSearchModel deliveryDoneBy;
	
	@BSearchFieldConfig(fieldName = "itemStatus", nodeName = PurchaseReturnMaterialItem.NODENAME, seName =
			PurchaseReturnMaterialItem.SENAME,
			nodeInstID = PurchaseReturnMaterialItem.NODENAME)
	
	protected int itemStatus;@BSearchFieldConfig(fieldName = "signDate", nodeName = PurchaseContract.NODENAME, fieldType =
			BSearchFieldConfig.FIELDTYPE_LOW,
			seName = PurchaseContract.SENAME, nodeInstID = PurchaseContract.SENAME)
	protected Date signDateLow;

	protected String signDateStrLow;

	@BSearchFieldConfig(fieldName = "signDate", nodeName = PurchaseContract.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH,
			seName = PurchaseContract.SENAME, nodeInstID = PurchaseContract.SENAME)
	protected Date signDateHigh;

	protected String signDateStrHigh;



	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(final int itemStatus) {
		this.itemStatus = itemStatus;
	}


	public DocEmbedMaterialSKUSearchModel getItemMaterialSKU() {
		return itemMaterialSKU;
	}

	public void setItemMaterialSKU(DocEmbedMaterialSKUSearchModel itemMaterialSKU) {
		this.itemMaterialSKU = itemMaterialSKU;
	}

	public Date getSignDateLow() {
		return signDateLow;
	}

	public void setSignDateLow(Date signDateLow) {
		this.signDateLow = signDateLow;
	}

	public String getSignDateStrLow() {
		return signDateStrLow;
	}

	public void setSignDateStrLow(String signDateStrLow) {
		this.signDateStrLow = signDateStrLow;
	}

	public Date getSignDateHigh() {
		return signDateHigh;
	}

	public void setSignDateHigh(Date signDateHigh) {
		this.signDateHigh = signDateHigh;
	}

	public String getSignDateStrHigh() {
		return signDateStrHigh;
	}

	public void setSignDateStrHigh(String signDateStrHigh) {
		this.signDateStrHigh = signDateStrHigh;
	}

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

	public DocFlowNodeSearchModel getReservedTargetDoc() {
		return reservedTargetDoc;
	}

	public void setReservedTargetDoc(DocFlowNodeSearchModel reservedTargetDoc) {
		this.reservedTargetDoc = reservedTargetDoc;
	}

	public DocFlowNodeSearchModel getReservedByDoc() {
		return reservedByDoc;
	}

	public void setReservedByDoc(DocFlowNodeSearchModel reservedByDoc) {
		this.reservedByDoc = reservedByDoc;
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

}
