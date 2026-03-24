package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.QualityInsOrderActionNode;
import com.company.IntelligentPlatform.logistics.model.QualityInspectOrderParty;
import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.logistics.model.QualityInspectOrder;
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
public class QualityInspectOrderSearchModel extends SEUIComModel {

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

	@BSearchGroupConfig(groupInstId = QualityInspectOrderParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected AccountSearchSubModel purchaseFromSupplier;

	@BSearchGroupConfig(groupInstId = QualityInspectOrderParty.PARTY_NODEINST_PROD_ORG)
	protected AccountSearchSubModel productionOrg;

	@BSearchGroupConfig(groupInstId = QualityInspectOrderParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected AccountSearchSubModel soldToCustomer;

	@BSearchGroupConfig(groupInstId = QualityInspectOrderParty.PARTY_NODEINST_SOLD_ORG)
	protected AccountSearchSubModel soldFromOrg;

	@BSearchGroupConfig(groupInstId = QualityInspectOrderParty.PARTY_NODEINST_PUR_ORG)
	protected AccountSearchSubModel purchaseToOrg;

	@BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
	protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

	@BSearchGroupConfig(groupInstId = QualityInsOrderActionNode.NODEINST_ACTION_TEST_DONE)
	protected DocActionNodeSearchModel testDoneBy;

	@BSearchGroupConfig(groupInstId = QualityInsOrderActionNode.NODEINST_ACTION_TEST_DONE)
	protected DocActionNodeSearchModel inProcessBy;

	@BSearchGroupConfig(groupInstId = QualityInsOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected DocActionNodeSearchModel processDoneBy;

	@BSearchFieldConfig(fieldName = "checkStatus", nodeName = QualityInspectOrder.NODENAME, seName = QualityInspectOrder.SENAME, nodeInstID = QualityInspectOrder.SENAME)
	protected int checkStatus;

	@BSearchFieldConfig(fieldName = "checkResult", nodeName = QualityInspectOrder.NODENAME, seName = QualityInspectOrder.SENAME, nodeInstID = QualityInspectOrder.SENAME)
	protected String checkResult;

	@BSearchFieldConfig(fieldName = "checkDate", nodeName = QualityInspectOrder.NODENAME, seName = QualityInspectOrder.SENAME, nodeInstID = QualityInspectOrder.SENAME)
	protected String checkDate;

	@BSearchFieldConfig(fieldName = "category", nodeName = QualityInspectOrder.NODENAME, seName = QualityInspectOrder.SENAME, nodeInstID = QualityInspectOrder.SENAME)
	protected int category;

	@BSearchFieldConfig(fieldName = "inspectType", nodeName = QualityInspectOrder.NODENAME, seName = QualityInspectOrder.SENAME, nodeInstID = QualityInspectOrder.SENAME)
	protected int inspectType;

	@BSearchFieldConfig(fieldName = "refWarehouseUUID", nodeName = QualityInspectOrder.NODENAME, seName = QualityInspectOrder.SENAME, nodeInstID = QualityInspectOrder.SENAME)
	protected String refWarehouseUUID;

	public int getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
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

	public String getCheckResult() {
		return this.checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public int getCategory() {
		return this.category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getInspectType() {
		return this.inspectType;
	}

	public void setInspectType(int inspectType) {
		this.inspectType = inspectType;
	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
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

	public DocEmbedMaterialSKUSearchModel getItemMaterialSKU() {
		return itemMaterialSKU;
	}

	public void setItemMaterialSKU(DocEmbedMaterialSKUSearchModel itemMaterialSKU) {
		this.itemMaterialSKU = itemMaterialSKU;
	}

	public DocActionNodeSearchModel getTestDoneBy() {
		return testDoneBy;
	}

	public void setTestDoneBy(DocActionNodeSearchModel testDoneBy) {
		this.testDoneBy = testDoneBy;
	}

	public DocActionNodeSearchModel getInProcessBy() {
		return inProcessBy;
	}

	public void setInProcessBy(DocActionNodeSearchModel inProcessBy) {
		this.inProcessBy = inProcessBy;
	}

	public DocActionNodeSearchModel getProcessDoneBy() {
		return processDoneBy;
	}

	public void setProcessDoneBy(DocActionNodeSearchModel processDoneBy) {
		this.processDoneBy = processDoneBy;
	}

	public DocFlowNodeSearchModel getReservedByDoc() {
		return reservedByDoc;
	}

	public void setReservedByDoc(DocFlowNodeSearchModel reservedByDoc) {
		this.reservedByDoc = reservedByDoc;
	}
}
