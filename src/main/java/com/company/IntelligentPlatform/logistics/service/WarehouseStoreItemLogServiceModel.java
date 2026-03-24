package com.company.IntelligentPlatform.logistics.service;


import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class WarehouseStoreItemLogServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = WarehouseStoreItemLog.NODENAME, nodeInstId = WarehouseStoreItemLog.NODENAME)
    protected WarehouseStoreItemLog warehouseStoreItemLog;

    public WarehouseStoreItemLog getWarehouseStoreItemLog() {
        return warehouseStoreItemLog;
    }

    public void setWarehouseStoreItemLog(WarehouseStoreItemLog warehouseStoreItemLog) {
        this.warehouseStoreItemLog = warehouseStoreItemLog;
    }
}
