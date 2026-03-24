package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemParty;
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
public class WasteProcessOrderSearchModel extends SEUIComModel {

    @BSearchGroupConfig(groupInstId = WasteProcessOrder.SENAME)
    protected ServiceDocSearchHeaderModel headerModel;

    @BSearchGroupConfig(groupInstId = WasteProcessOrder.SENAME)
    protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

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

    @BSearchGroupConfig(groupInstId = PurchaseReturnOrderActionNode.NODEINST_ACTION_APPROVE)
    protected DocActionNodeSearchModel submittedBy;

    @BSearchGroupConfig(groupInstId = PurchaseReturnOrderActionNode.NODEINST_ACTION_APPROVE)
    protected DocActionNodeSearchModel approvedBy;

    @BSearchGroupConfig(groupInstId = PurchaseReturnOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
    protected DocActionNodeSearchModel deliveryDoneBy;

    @BSearchFieldConfig(fieldName = "processType", nodeName = WasteProcessOrder.NODENAME, seName = WasteProcessOrder.SENAME,
			nodeInstID = WasteProcessOrder.SENAME)
    protected int processType;

    @BSearchGroupConfig(groupInstId = WarehouseStoreItemParty.PARTY_NODEINST_SOLD_CUSTOMER)
    protected AccountSearchSubModel soldToCustomer;

    @BSearchGroupConfig(groupInstId = WarehouseStoreItemParty.PARTY_NODEINST_SOLD_ORG)
    protected AccountSearchSubModel soldFromOrg;

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

    public int getProcessType() {
        return processType;
    }

    public void setProcessType(int processType) {
        this.processType = processType;
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

    public DocEmbedMaterialSKUSearchModel getItemMaterialSKU() {
        return itemMaterialSKU;
    }

    public void setItemMaterialSKU(DocEmbedMaterialSKUSearchModel itemMaterialSKU) {
        this.itemMaterialSKU = itemMaterialSKU;
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
}
