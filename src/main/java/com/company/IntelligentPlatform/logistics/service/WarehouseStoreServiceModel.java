package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class WarehouseStoreServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = WarehouseStore.NODENAME, nodeInstId = WarehouseStore.SENAME, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
    protected WarehouseStore warehouseStore;
    
    @IServiceModuleFieldConfig(nodeName = WarehouseStoreItem.NODENAME, nodeInstId = WarehouseStoreItem.NODENAME, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
    protected List<WarehouseStoreItemServiceModel> WarehouseStoreItemList = new ArrayList<>();

    @IServiceModuleFieldConfig(nodeName = WarehouseStoreAttachment.NODENAME, nodeInstId = WarehouseStoreAttachment.NODENAME, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
    protected List<WarehouseStoreAttachment> warehouseStoreAttachmentList = new ArrayList<>();

    @IServiceModuleFieldConfig(nodeName = WarehouseStoreActionNode.NODENAME, nodeInstId =
            WarehouseStoreActionNode.NODEINST_ACTION_INSTOCK, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
    protected WarehouseStoreActionNode instockBy;

    @IServiceModuleFieldConfig(nodeName = WarehouseStoreActionNode.NODENAME, nodeInstId =
            WarehouseStoreActionNode.NODEINST_ACTION_ARCHIVE, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
    protected WarehouseStoreActionNode archiveBy;

    @IServiceModuleFieldConfig(nodeName = WarehouseStoreParty.NODENAME, nodeInstId = WarehouseStoreParty.PARTY_NODEINST_PUR_SUPPLIER, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
    protected WarehouseStoreParty corporateSupplierParty;

    @IServiceModuleFieldConfig(nodeName = WarehouseStoreParty.NODENAME, nodeInstId = WarehouseStoreParty.PARTY_NODEINST_PUR_ORG, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
    protected WarehouseStoreParty purchaseOrgParty;

    @IServiceModuleFieldConfig(nodeName = WarehouseStoreParty.NODENAME, nodeInstId =
            WarehouseStoreParty.PARTY_NODEINST_PROD_ORG, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
    protected WarehouseStoreParty productionOrgParty;

    @IServiceModuleFieldConfig(nodeName = WarehouseStoreParty.NODENAME, nodeInstId =
            WarehouseStoreParty.PARTY_NODEINST_SOLD_CUSTOMER, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
    protected WarehouseStoreParty corporateCustomerParty;

    @IServiceModuleFieldConfig(nodeName = WarehouseStoreParty.NODENAME, nodeInstId =
            WarehouseStoreParty.PARTY_NODEINST_SOLD_ORG, docNodeCategory =
            IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
    protected WarehouseStoreParty salesOrganizationParty;

    public WarehouseStore getWarehouseStore() {
        return warehouseStore;
    }

    public void setWarehouseStore(WarehouseStore warehouseStore) {
        this.warehouseStore = warehouseStore;
    }

    public List<WarehouseStoreItemServiceModel> getWarehouseStoreItemList() {
        return WarehouseStoreItemList;
    }

    public void setWarehouseStoreItemList(List<WarehouseStoreItemServiceModel> warehouseStoreItemList) {
        WarehouseStoreItemList = warehouseStoreItemList;
    }

    public List<WarehouseStoreAttachment> getWarehouseStoreAttachmentList() {
        return warehouseStoreAttachmentList;
    }

    public void setWarehouseStoreAttachmentList(List<WarehouseStoreAttachment> warehouseStoreAttachmentList) {
        this.warehouseStoreAttachmentList = warehouseStoreAttachmentList;
    }

    public WarehouseStoreActionNode getInstockBy() {
        return instockBy;
    }

    public void setInstockBy(WarehouseStoreActionNode instockBy) {
        this.instockBy = instockBy;
    }

    public WarehouseStoreActionNode getArchiveBy() {
        return archiveBy;
    }

    public void setArchiveBy(WarehouseStoreActionNode archiveBy) {
        this.archiveBy = archiveBy;
    }

    public WarehouseStoreParty getCorporateSupplierParty() {
        return corporateSupplierParty;
    }

    public void setCorporateSupplierParty(WarehouseStoreParty corporateSupplierParty) {
        this.corporateSupplierParty = corporateSupplierParty;
    }

    public WarehouseStoreParty getPurchaseOrgParty() {
        return purchaseOrgParty;
    }

    public void setPurchaseOrgParty(WarehouseStoreParty purchaseOrgParty) {
        this.purchaseOrgParty = purchaseOrgParty;
    }

    public WarehouseStoreParty getProductionOrgParty() {
        return productionOrgParty;
    }

    public void setProductionOrgParty(WarehouseStoreParty productionOrgParty) {
        this.productionOrgParty = productionOrgParty;
    }

    public WarehouseStoreParty getCorporateCustomerParty() {
        return corporateCustomerParty;
    }

    public void setCorporateCustomerParty(WarehouseStoreParty corporateCustomerParty) {
        this.corporateCustomerParty = corporateCustomerParty;
    }

    public WarehouseStoreParty getSalesOrganizationParty() {
        return salesOrganizationParty;
    }

    public void setSalesOrganizationParty(WarehouseStoreParty salesOrganizationParty) {
        this.salesOrganizationParty = salesOrganizationParty;
    }
}
