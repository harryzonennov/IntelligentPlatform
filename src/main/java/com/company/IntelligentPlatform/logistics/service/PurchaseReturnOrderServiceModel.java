package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class PurchaseReturnOrderServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = PurchaseReturnOrder.NODENAME, nodeInstId = PurchaseReturnOrder.SENAME, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
    protected PurchaseReturnOrder purchaseReturnOrder;

    @IServiceModuleFieldConfig(nodeName = PurchaseReturnOrderParty.NODENAME, nodeInstId = PurchaseReturnOrderParty.PARTY_NODEINST_PUR_ORG, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
    protected PurchaseReturnOrderParty purchaseToOrg;

    @IServiceModuleFieldConfig(nodeName = PurchaseReturnOrderParty.NODENAME, nodeInstId = PurchaseReturnOrderParty.PARTY_NODEINST_PUR_SUPPLIER, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
    protected PurchaseReturnOrderParty purchaseFromSupplier;

    @IServiceModuleFieldConfig(nodeName = PurchaseReturnOrderActionNode.NODENAME, nodeInstId =
            PurchaseReturnOrderActionNode.NODEINST_ACTION_APPROVE, blockUpdate = true, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
    protected PurchaseReturnOrderActionNode approvedBy;

    @IServiceModuleFieldConfig(nodeName = PurchaseReturnOrderActionNode.NODENAME, nodeInstId =
            PurchaseReturnOrderActionNode.NODEINST_ACTION_REJECT_APPROVE, blockUpdate = true, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
    protected PurchaseReturnOrderActionNode rejectApprovedBy;

    @IServiceModuleFieldConfig(nodeName = PurchaseReturnOrderActionNode.NODENAME, nodeInstId =
            PurchaseReturnOrderActionNode.NODEINST_ACTION_SUBMIT, blockUpdate = true, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
    protected PurchaseReturnOrderActionNode submittedBy;

    @IServiceModuleFieldConfig(nodeName = PurchaseReturnMaterialItem.NODENAME, nodeInstId =
            PurchaseReturnMaterialItem.NODENAME, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
    protected List<PurchaseReturnMaterialItemServiceModel> purchaseReturnMaterialItemList =
            new ArrayList<>();

    @IServiceModuleFieldConfig(nodeName = PurchaseReturnOrderAttachment.NODENAME, nodeInstId =
            PurchaseReturnOrderAttachment.NODENAME, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
    protected List<ServiceEntityNode> purchaseReturnOrderAttachmentList = new ArrayList<>();

    public List<PurchaseReturnMaterialItemServiceModel> getPurchaseReturnMaterialItemList() {
        return purchaseReturnMaterialItemList;
    }

    public void setPurchaseReturnMaterialItemList(final List<PurchaseReturnMaterialItemServiceModel> purchaseReturnMaterialItemList) {
        this.purchaseReturnMaterialItemList = purchaseReturnMaterialItemList;
    }

    public PurchaseReturnOrder getPurchaseReturnOrder() {
        return purchaseReturnOrder;
    }

    public void setPurchaseReturnOrder(PurchaseReturnOrder purchaseReturnOrder) {
        this.purchaseReturnOrder = purchaseReturnOrder;
    }

    public PurchaseReturnOrderParty getPurchaseFromSupplier() {
        return purchaseFromSupplier;
    }

    public void setPurchaseFromSupplier(PurchaseReturnOrderParty purchaseFromSupplier) {
        this.purchaseFromSupplier = purchaseFromSupplier;
    }

    public PurchaseReturnOrderParty getPurchaseToOrg() {
        return purchaseToOrg;
    }

    public void setPurchaseToOrg(PurchaseReturnOrderParty purchaseToOrg) {
        this.purchaseToOrg = purchaseToOrg;
    }

    public List<ServiceEntityNode> getPurchaseReturnOrderAttachmentList() {
        return purchaseReturnOrderAttachmentList;
    }

    public void setPurchaseReturnOrderAttachmentList(List<ServiceEntityNode> purchaseReturnOrderAttachmentList) {
        this.purchaseReturnOrderAttachmentList = purchaseReturnOrderAttachmentList;
    }

    public PurchaseReturnOrderActionNode getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(PurchaseReturnOrderActionNode approvedBy) {
        this.approvedBy = approvedBy;
    }

    public PurchaseReturnOrderActionNode getRejectApprovedBy() {
        return rejectApprovedBy;
    }

    public void setRejectApprovedBy(PurchaseReturnOrderActionNode rejectApprovedBy) {
        this.rejectApprovedBy = rejectApprovedBy;
    }

    public PurchaseReturnOrderActionNode getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(PurchaseReturnOrderActionNode submittedBy) {
        this.submittedBy = submittedBy;
    }
}
