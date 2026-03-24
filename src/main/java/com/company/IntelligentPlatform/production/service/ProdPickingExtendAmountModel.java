package com.company.IntelligentPlatform.production.service;
import com.company.IntelligentPlatform.production.service.ProdPickingExtendAmountModel;

import java.util.List;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;

/**
 * Extracted from inner class ProdPickingExtendAmountModel
 * to allow cross-class imports
 */
public class ProdPickingExtendAmountModel {

    protected ProdPickingRefMaterialItem prodPickingRefMaterialItem;
    protected StorageCoreUnit inProcessAmount;
    protected StorageCoreUnit pickedAmount;
    protected StorageCoreUnit toPickAmount;
    protected StorageCoreUnit inStockAmount;
    protected StorageCoreUnit suppliedAmount;
    protected List<ServiceEntityNode> endDocMatItemList;

    public ProdPickingExtendAmountModel() {}

    public ProdPickingExtendAmountModel(ProdPickingRefMaterialItem prodPickingRefMaterialItem,
            StorageCoreUnit inProcessAmount, StorageCoreUnit toPickAmount, StorageCoreUnit inStockAmount,
            StorageCoreUnit suppliedAmount, StorageCoreUnit pickedAmount, List<ServiceEntityNode> endDocMatItemList) {
        this.prodPickingRefMaterialItem = prodPickingRefMaterialItem;
        this.inProcessAmount = inProcessAmount;
        this.toPickAmount = toPickAmount;
        this.inStockAmount = inStockAmount;
        this.suppliedAmount = suppliedAmount;
        this.pickedAmount = pickedAmount;
        this.endDocMatItemList = endDocMatItemList;
    }

    public ProdPickingRefMaterialItem getProdPickingRefMaterialItem() { return prodPickingRefMaterialItem; }
    public void setProdPickingRefMaterialItem(ProdPickingRefMaterialItem v) { this.prodPickingRefMaterialItem = v; }
    public StorageCoreUnit getInProcessAmount() { return inProcessAmount; }
    public void setInProcessAmount(StorageCoreUnit v) { this.inProcessAmount = v; }
    public StorageCoreUnit getPickedAmount() { return pickedAmount; }
    public void setPickedAmount(StorageCoreUnit v) { this.pickedAmount = v; }
    public StorageCoreUnit getToPickAmount() { return toPickAmount; }
    public void setToPickAmount(StorageCoreUnit v) { this.toPickAmount = v; }
    public StorageCoreUnit getInStockAmount() { return inStockAmount; }
    public void setInStockAmount(StorageCoreUnit v) { this.inStockAmount = v; }
    public StorageCoreUnit getSuppliedAmount() { return suppliedAmount; }
    public void setSuppliedAmount(StorageCoreUnit v) { this.suppliedAmount = v; }
    public List<ServiceEntityNode> getEndDocMatItemList() { return endDocMatItemList; }
    public void setEndDocMatItemList(List<ServiceEntityNode> v) { this.endDocMatItemList = v; }
}
