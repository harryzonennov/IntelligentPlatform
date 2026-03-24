package com.company.IntelligentPlatform.sales.dto;

import com.company.IntelligentPlatform.sales.model.SalesForcastActionNode;
import com.company.IntelligentPlatform.sales.model.SalesForcast;
import com.company.IntelligentPlatform.sales.model.SalesForcastParty;
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


/**
 * SalesForcast Search Model
 * *
 *
 * @author
 * @date Wed Nov 11 10:58:25 CST 2015
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */

@Component
public class SalesForcastSearchModel extends SEUIComModel {

	public static final String NODE_INST_SOLDTOPARTY = "soldToParty";

	public static final String NODE_INST_SOLDFROMPARTY = "soldFromParty";

	@BSearchGroupConfig(groupInstId = SalesForcast.SENAME)
	protected ServiceDocSearchHeaderModel headerModel;

	@BSearchGroupConfig(groupInstId = SalesForcast.SENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchGroupConfig(groupInstId = SalesForcastParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected AccountSearchSubModel soldToCustomer;

	@BSearchGroupConfig(groupInstId = SalesForcastParty.PARTY_NODEINST_SOLD_ORG)
	protected AccountSearchSubModel soldFromOrg;

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

	@BSearchGroupConfig(groupInstId = SalesForcastActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel submittedBy;

	@BSearchGroupConfig(groupInstId = SalesForcastActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel approvedBy;

	@BSearchFieldConfig(fieldName = "planExecutionDate", nodeName = SalesForcast.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW,
			seName = SalesForcast.SENAME, nodeInstID = SalesForcast.SENAME)
	protected Date planExecutionDateLow;

	@BSearchFieldConfig(fieldName = "planExecutionDate", nodeName = SalesForcast.NODENAME, fieldType =
			BSearchFieldConfig.FIELDTYPE_HIGH,
			seName = SalesForcast.SENAME, nodeInstID = SalesForcast.SENAME)
	protected Date planExecutionDateHigh;

	// compound search field
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

	public Date getPlanExecutionDateLow() {
		return planExecutionDateLow;
	}

	public void setPlanExecutionDateLow(Date planExecutionDateLow) {
		this.planExecutionDateLow = planExecutionDateLow;
	}

	public Date getPlanExecutionDateHigh() {
		return planExecutionDateHigh;
	}

	public void setPlanExecutionDateHigh(Date planExecutionDateHigh) {
		this.planExecutionDateHigh = planExecutionDateHigh;
	}

	public DocEmbedMaterialSKUSearchModel getItemMaterialSKU() {
		return itemMaterialSKU;
	}

	public void setItemMaterialSKU(DocEmbedMaterialSKUSearchModel itemMaterialSKU) {
		this.itemMaterialSKU = itemMaterialSKU;
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

	public DocFlowNodeSearchModel getNextDoc() {
		return nextDoc;
	}

	public void setNextDoc(DocFlowNodeSearchModel nextDoc) {
		this.nextDoc = nextDoc;
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
}