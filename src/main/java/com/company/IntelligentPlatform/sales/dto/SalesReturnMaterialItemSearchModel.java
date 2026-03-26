package com.company.IntelligentPlatform.sales.dto;

import java.util.Date;

import com.company.IntelligentPlatform.sales.model.*;
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
public class SalesReturnMaterialItemSearchModel extends SEUIComModel {

    @BSearchGroupConfig(groupInstId = SalesReturnOrder.SENAME)
    protected ServiceDocSearchHeaderModel parentDocHeaderModel;

    @BSearchGroupConfig(groupInstId = SalesReturnMaterialItem.NODENAME)
    protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

    @BSearchGroupConfig(groupInstId = SalesReturnOrderParty.PARTY_NODEINST_SOLD_CUSTOMER)
    protected AccountSearchSubModel soldToCustomer;

    @BSearchGroupConfig(groupInstId = SalesReturnOrderParty.PARTY_NODEINST_SOLD_ORG)
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

    @BSearchGroupConfig(groupInstId = SalesContractActionNode.NODEINST_ACTION_APPROVE)
    protected DocActionNodeSearchModel submittedBy;

    @BSearchGroupConfig(groupInstId = SalesContractActionNode.NODEINST_ACTION_APPROVE)
    protected DocActionNodeSearchModel approvedBy;

    @BSearchGroupConfig(groupInstId = SalesContractActionNode.NODEINST_ACTION_DELIVERY_DONE)
    protected DocActionNodeSearchModel deliveryDoneBy;

    @BSearchFieldConfig(fieldName = "itemStatus", nodeName = SalesReturnMaterialItem.NODENAME, seName =
            SalesReturnMaterialItem.SENAME,
            nodeInstID = SalesReturnMaterialItem.NODENAME)
    protected int itemStatus;

    @BSearchFieldConfig(fieldName = "signDate", nodeName = SalesContract.NODENAME, fieldType =
            BSearchFieldConfig.FIELDTYPE_LOW,
            seName = SalesContract.SENAME, nodeInstID = SalesContract.SENAME)
    protected Date signDateLow;

    protected String signDateStrLow;

    @BSearchFieldConfig(fieldName = "signDate", nodeName = SalesContract.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH,
            seName = SalesContract.SENAME, nodeInstID = SalesContract.SENAME)
    protected Date signDateHigh;

    protected String signDateStrHigh;

    // compound search field
    @BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
    protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

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