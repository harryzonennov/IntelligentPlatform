package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

public class WarehouseStoreItemLogServiceUIModel extends ServiceUIModule {

    @IServiceUIModuleFieldConfig(nodeName = WarehouseStoreItemLog.NODENAME, nodeInstId = WarehouseStoreItemLog.NODENAME)
    protected WarehouseStoreItemLogUIModel warehouseStoreItemLogUIModel;

    public WarehouseStoreItemLogUIModel getWarehouseStoreItemLogUIModel() {
        return warehouseStoreItemLogUIModel;
    }

    public void setWarehouseStoreItemLogUIModel(WarehouseStoreItemLogUIModel warehouseStoreItemLogUIModel) {
        this.warehouseStoreItemLogUIModel = warehouseStoreItemLogUIModel;
    }

}
