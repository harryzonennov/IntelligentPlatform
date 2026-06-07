package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.PurchaseContract;
import com.company.IntelligentPlatform.logistics.model.PurchaseContractMaterialItem;
import java.util.List;
import java.util.Map;

/**
 * Full editor payload for PurchaseContract — matches legacy PurchaseContractServiceUIModel structure:
 * contract header, buying-org party, supplier party, server meta, material items, attachments.
 */
public class PurchaseContractDetail {

    private PurchaseContract purchaseContractUIModel;
    private PurchaseContractPartyUIModel purchaseToOrgUIModel;
    private PurchaseContractPartyUIModel purchaseFromSupplierUIModel;
    private Map<String, Object> serviceUIMeta;
    private List<PurchaseContractMaterialItem> purchaseContractMaterialItemUIModelList;
    private List<PurchaseContractAttachmentUIModel> purchaseContractAttachmentUIModelList;

    public PurchaseContractDetail() {}

    public PurchaseContractDetail(
            PurchaseContract purchaseContractUIModel,
            PurchaseContractPartyUIModel purchaseToOrgUIModel,
            PurchaseContractPartyUIModel purchaseFromSupplierUIModel,
            Map<String, Object> serviceUIMeta,
            List<PurchaseContractMaterialItem> purchaseContractMaterialItemUIModelList,
            List<PurchaseContractAttachmentUIModel> purchaseContractAttachmentUIModelList) {
        this.purchaseContractUIModel = purchaseContractUIModel;
        this.purchaseToOrgUIModel = purchaseToOrgUIModel;
        this.purchaseFromSupplierUIModel = purchaseFromSupplierUIModel;
        this.serviceUIMeta = serviceUIMeta;
        this.purchaseContractMaterialItemUIModelList = purchaseContractMaterialItemUIModelList;
        this.purchaseContractAttachmentUIModelList = purchaseContractAttachmentUIModelList;
    }

    public PurchaseContract getPurchaseContractUIModel() { return purchaseContractUIModel; }
    public void setPurchaseContractUIModel(PurchaseContract purchaseContractUIModel) {
        this.purchaseContractUIModel = purchaseContractUIModel;
    }

    public PurchaseContractPartyUIModel getPurchaseToOrgUIModel() { return purchaseToOrgUIModel; }
    public void setPurchaseToOrgUIModel(PurchaseContractPartyUIModel purchaseToOrgUIModel) {
        this.purchaseToOrgUIModel = purchaseToOrgUIModel;
    }

    public PurchaseContractPartyUIModel getPurchaseFromSupplierUIModel() { return purchaseFromSupplierUIModel; }
    public void setPurchaseFromSupplierUIModel(PurchaseContractPartyUIModel purchaseFromSupplierUIModel) {
        this.purchaseFromSupplierUIModel = purchaseFromSupplierUIModel;
    }

    public Map<String, Object> getServiceUIMeta() { return serviceUIMeta; }
    public void setServiceUIMeta(Map<String, Object> serviceUIMeta) {
        this.serviceUIMeta = serviceUIMeta;
    }

    public List<PurchaseContractMaterialItem> getPurchaseContractMaterialItemUIModelList() {
        return purchaseContractMaterialItemUIModelList;
    }
    public void setPurchaseContractMaterialItemUIModelList(
            List<PurchaseContractMaterialItem> purchaseContractMaterialItemUIModelList) {
        this.purchaseContractMaterialItemUIModelList = purchaseContractMaterialItemUIModelList;
    }

    public List<PurchaseContractAttachmentUIModel> getPurchaseContractAttachmentUIModelList() {
        return purchaseContractAttachmentUIModelList;
    }
    public void setPurchaseContractAttachmentUIModelList(
            List<PurchaseContractAttachmentUIModel> purchaseContractAttachmentUIModelList) {
        this.purchaseContractAttachmentUIModelList = purchaseContractAttachmentUIModelList;
    }
}
