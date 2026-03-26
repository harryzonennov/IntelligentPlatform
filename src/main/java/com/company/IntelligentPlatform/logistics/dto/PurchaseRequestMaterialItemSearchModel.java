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
public class PurchaseRequestMaterialItemSearchModel extends SEUIComModel {

	@BSearchGroupConfig(groupInstId = PurchaseRequest.SENAME)
	protected ServiceDocSearchHeaderModel parentDocHeaderModel;

	@BSearchGroupConfig(groupInstId = PurchaseRequestMaterialItem.NODENAME)
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

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_BY_DOC)
	protected DocFlowNodeSearchModel reservedByDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_TARGET_DOC)
	protected DocFlowNodeSearchModel reservedTargetDoc;

	@BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
	protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

	@BSearchGroupConfig(groupInstId = PurchaseRequestActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel submittedBy;

	@BSearchGroupConfig(groupInstId = PurchaseRequestActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel approvedBy;

	@BSearchGroupConfig(groupInstId = PurchaseRequestActionNode.NODEINST_ACTION_INPROCESS)
	protected DocActionNodeSearchModel inProcessBy;

	@BSearchFieldConfig(fieldName = "itemStatus", nodeName = PurchaseRequestMaterialItem.NODENAME, seName =
			PurchaseRequestMaterialItem.SENAME,
			nodeInstID = PurchaseRequestMaterialItem.NODENAME)
	protected int itemStatus;

	@BSearchFieldConfig(fieldName = "planExecutionDate", nodeName = PurchaseRequest.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW,
			seName = PurchaseRequest.SENAME, nodeInstID = PurchaseRequest.SENAME)
	protected Date planExecutionDateLow;

	protected String planExecutionDateStrLow;

	@BSearchFieldConfig(fieldName = "planExecutionDate", nodeName = PurchaseRequest.NODENAME, fieldType =
			BSearchFieldConfig.FIELDTYPE_HIGH,
			seName = PurchaseRequest.SENAME, nodeInstID = PurchaseRequest.SENAME)
	protected Date planExecutionDateHigh;

	protected String planExecutionDateStrHigh;

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

	public DocActionNodeSearchModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(DocActionNodeSearchModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public DocActionNodeSearchModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(DocActionNodeSearchModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public DocActionNodeSearchModel getInProcessBy() {
		return inProcessBy;
	}

	public void setInProcessBy(DocActionNodeSearchModel inProcessBy) {
		this.inProcessBy = inProcessBy;
	}

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Date getPlanExecutionDateLow() {
		return planExecutionDateLow;
	}

	public void setPlanExecutionDateLow(Date planExecutionDateLow) {
		this.planExecutionDateLow = planExecutionDateLow;
	}

	public String getPlanExecutionDateStrLow() {
		return planExecutionDateStrLow;
	}

	public void setPlanExecutionDateStrLow(String planExecutionDateStrLow) {
		this.planExecutionDateStrLow = planExecutionDateStrLow;
	}

	public Date getPlanExecutionDateHigh() {
		return planExecutionDateHigh;
	}

	public void setPlanExecutionDateHigh(Date planExecutionDateHigh) {
		this.planExecutionDateHigh = planExecutionDateHigh;
	}

	public String getPlanExecutionDateStrHigh() {
		return planExecutionDateStrHigh;
	}

	public void setPlanExecutionDateStrHigh(String planExecutionDateStrHigh) {
		this.planExecutionDateStrHigh = planExecutionDateStrHigh;
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
}
