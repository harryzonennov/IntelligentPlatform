package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.WarehouseArea;
import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class WarehouseAreaServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = WarehouseArea.NODENAME, nodeInstId = WarehouseArea.NODENAME)
    protected WarehouseArea warehouseArea;

    public WarehouseArea getWarehouseArea() {
        return this.warehouseArea;
    }

    public void setWarehouseArea(WarehouseArea warehouseArea) {
        this.warehouseArea = warehouseArea;
    }

}
