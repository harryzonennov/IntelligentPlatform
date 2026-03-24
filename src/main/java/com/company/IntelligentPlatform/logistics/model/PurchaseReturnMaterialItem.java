package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class PurchaseReturnMaterialItem extends DocMatItemNode {

    public static final String NODENAME = IServiceModelConstants.PurchaseReturnMaterialItem;

    public static final String SENAME = PurchaseReturnOrder.SENAME;

    public static final int STATUS_INITIAL = 1;

    public static final int STATUS_DONE = 2;

    protected int itemStatus;

    protected String refFinAccountUUID;

    protected String refDocItemUUID;

    protected int refDocItemType;

    protected String refStoreItemUUID;

    public PurchaseReturnMaterialItem() {
        this.nodeName = NODENAME;
        this.serviceEntityName = SENAME;
        this.itemStatus = STATUS_INITIAL;
        this.refDocItemType = IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT;
        this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER;
    }

    public int getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(int itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getRefFinAccountUUID() {
        return refFinAccountUUID;
    }

    public void setRefFinAccountUUID(String refFinAccountUUID) {
        this.refFinAccountUUID = refFinAccountUUID;
    }

    public String getRefDocItemUUID() {
        return refDocItemUUID;
    }

    public void setRefDocItemUUID(final String refDocItemUUID) {
        this.refDocItemUUID = refDocItemUUID;
    }

    public int getRefDocItemType() {
        return refDocItemType;
    }

    public void setRefDocItemType(final int refDocItemType) {
        this.refDocItemType = refDocItemType;
    }

    public String getRefStoreItemUUID() {
        return refStoreItemUUID;
    }

    public void setRefStoreItemUUID(String refStoreItemUUID) {
        this.refStoreItemUUID = refStoreItemUUID;
    }
}
