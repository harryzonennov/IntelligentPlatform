package com.company.IntelligentPlatform.logistics.dto;

import java.util.Date;

import com.company.IntelligentPlatform.logistics.model.InquiryParty;
import com.company.IntelligentPlatform.logistics.model.PurchaseContract;
import com.company.IntelligentPlatform.logistics.model.PurchaseContractActionNode;
import com.company.IntelligentPlatform.logistics.model.PurchaseContractParty;
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
public class PurchaseContractSearchModel extends SEUIComModel {

	@BSearchGroupConfig(groupInstId = PurchaseContract.SENAME)
	protected ServiceDocSearchHeaderModel headerModel;

	@BSearchGroupConfig(groupInstId = PurchaseContract.SENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchGroupConfig(groupInstId = PurchaseContractParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected AccountSearchSubModel purchaseFromSupplier;

	@BSearchGroupConfig(groupInstId = PurchaseContractParty.PARTY_NODEINST_PUR_ORG)
	protected AccountSearchSubModel purchaseToOrg;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_PREV_DOC)
	protected DocFlowNodeSearchModel prevDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_PREV_PROF_DOC)
	protected DocFlowNodeSearchModel prevProfDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_NEXT_PROF_DOC)
	protected DocFlowNodeSearchModel nextProfDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_NEXT_DOC)
	protected DocFlowNodeSearchModel nextDoc;

	@BSearchGroupConfig(groupInstId = PurchaseContractActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel submittedBy;

	@BSearchGroupConfig(groupInstId = PurchaseContractActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel approvedBy;

	@BSearchGroupConfig(groupInstId = PurchaseContractActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected DocActionNodeSearchModel deliveryDoneBy;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_BY_DOC)
	protected DocFlowNodeSearchModel reservedByDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_TARGET_DOC)
	protected DocFlowNodeSearchModel reservedTargetDoc;

	@BSearchFieldConfig(fieldName = "signDate", nodeName = PurchaseContract.NODENAME, seName = PurchaseContract.SENAME, nodeInstID = PurchaseContract.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date signDateLow;

	@BSearchFieldConfig(fieldName = "signDate", nodeName = PurchaseContract.NODENAME, seName = PurchaseContract.SENAME, nodeInstID = PurchaseContract.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date signDateHigh;

	@BSearchFieldConfig(fieldName = "requireExecutionDate", nodeName = PurchaseContract.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW,
			seName = PurchaseContract.SENAME, nodeInstID = PurchaseContract.SENAME)
	protected Date requireExecutionDateLow;

	@BSearchFieldConfig(fieldName = "requireExecutionDate", nodeName = PurchaseContract.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW,
			seName = PurchaseContract.SENAME, nodeInstID = PurchaseContract.SENAME)
	protected Date requireExecutionDateHigh;

	@BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
	protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

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

	public DocFlowNodeSearchModel getReservedTargetDoc() {
		return reservedTargetDoc;
	}

	public void setReservedTargetDoc(DocFlowNodeSearchModel reservedTargetDoc) {
		this.reservedTargetDoc = reservedTargetDoc;
	}

	public Date getSignDateLow() {
		return signDateLow;
	}

	public void setSignDateLow(Date signDateLow) {
		this.signDateLow = signDateLow;
	}

	public Date getSignDateHigh() {
		return signDateHigh;
	}

	public void setSignDateHigh(Date signDateHigh) {
		this.signDateHigh = signDateHigh;
	}


	public Date getRequireExecutionDateLow() {
		return requireExecutionDateLow;
	}

	public void setRequireExecutionDateLow(Date requireExecutionDateLow) {
		this.requireExecutionDateLow = requireExecutionDateLow;
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

	public DocEmbedMaterialSKUSearchModel getItemMaterialSKU() {
		return itemMaterialSKU;
	}

	public void setItemMaterialSKU(DocEmbedMaterialSKUSearchModel itemMaterialSKU) {
		this.itemMaterialSKU = itemMaterialSKU;
	}

	public Date getRequireExecutionDateHigh() {
		return requireExecutionDateHigh;
	}

	public void setRequireExecutionDateHigh(Date requireExecutionDateHigh) {
		this.requireExecutionDateHigh = requireExecutionDateHigh;
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

	public DocFlowNodeSearchModel getReservedByDoc() {
		return reservedByDoc;
	}

	public void setReservedByDoc(DocFlowNodeSearchModel reservedByDoc) {
		this.reservedByDoc = reservedByDoc;
	}
}
