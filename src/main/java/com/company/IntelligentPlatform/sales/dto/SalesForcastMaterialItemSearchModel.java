package com.company.IntelligentPlatform.sales.dto;

import com.company.IntelligentPlatform.sales.model.SalesContractActionNode;
import com.company.IntelligentPlatform.sales.model.SalesForcast;
import com.company.IntelligentPlatform.sales.model.SalesForcastMaterialItem;
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

@Component
public class SalesForcastMaterialItemSearchModel extends SEUIComModel {

    @BSearchGroupConfig(groupInstId = SalesForcast.SENAME)
    protected ServiceDocSearchHeaderModel parentDocHeaderModel;

    @BSearchGroupConfig(groupInstId = SalesForcastMaterialItem.NODENAME)
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

    @BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_BY_DOC)
    protected DocFlowNodeSearchModel reservedByDoc;

    @BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_TARGET_DOC)
    protected DocFlowNodeSearchModel reservedTargetDoc;

    @BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_NEXT_DOC)
    protected DocFlowNodeSearchModel nextDoc;

    @BSearchGroupConfig(groupInstId = SalesContractActionNode.NODEINST_ACTION_APPROVE)
    protected DocActionNodeSearchModel submittedBy;

    @BSearchGroupConfig(groupInstId = SalesContractActionNode.NODEINST_ACTION_APPROVE)
    protected DocActionNodeSearchModel approvedBy;

    @BSearchFieldConfig(fieldName = "itemStatus", nodeName = SalesForcastMaterialItem.NODENAME, seName =
            SalesForcastMaterialItem.SENAME,
            nodeInstID = SalesForcastMaterialItem.NODENAME)
    protected int itemStatus;

    @BSearchFieldConfig(fieldName = "planExecutionDate", nodeName = SalesForcast.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW,
            seName = SalesForcast.SENAME, nodeInstID = SalesForcast.SENAME)
    protected Date planExecutionDateLow;

    protected String planExecutionDateStrLow;

    @BSearchFieldConfig(fieldName = "planExecutionDate", nodeName = SalesForcast.NODENAME, fieldType =
            BSearchFieldConfig.FIELDTYPE_HIGH,
            seName = SalesForcast.SENAME, nodeInstID = SalesForcast.SENAME)
    protected Date planExecutionDateHigh;

    protected String planExecutionDateStrHigh;

    // compound search field
    @BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
    protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

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
}