package com.company.IntelligentPlatform.logistics.dto;

public class InventoryTransferMatItemBatchGenRequest extends DeliveryMatItemBatchGenRequest {

    protected String refOutboundWarehouseUUID;

    protected String refOutboundWarehouseAreaUUID;

    protected String refInboundWarehouseUUID;

    protected String refInboundWarehouseAreaUUID;

    public String getRefOutboundWarehouseUUID() {
        return refOutboundWarehouseUUID;
    }

    public void setRefOutboundWarehouseUUID(String refOutboundWarehouseUUID) {
        this.refOutboundWarehouseUUID = refOutboundWarehouseUUID;
    }

    public String getRefOutboundWarehouseAreaUUID() {
        return refOutboundWarehouseAreaUUID;
    }

    public void setRefOutboundWarehouseAreaUUID(String refOutboundWarehouseAreaUUID) {
        this.refOutboundWarehouseAreaUUID = refOutboundWarehouseAreaUUID;
    }

    public String getRefInboundWarehouseUUID() {
        return refInboundWarehouseUUID;
    }

    public void setRefInboundWarehouseUUID(String refInboundWarehouseUUID) {
        this.refInboundWarehouseUUID = refInboundWarehouseUUID;
    }

    public String getRefInboundWarehouseAreaUUID() {
        return refInboundWarehouseAreaUUID;
    }

    public void setRefInboundWarehouseAreaUUID(String refInboundWarehouseAreaUUID) {
        this.refInboundWarehouseAreaUUID = refInboundWarehouseAreaUUID;
    }

}
