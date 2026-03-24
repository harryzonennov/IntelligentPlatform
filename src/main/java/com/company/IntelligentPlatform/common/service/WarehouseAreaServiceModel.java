package com.company.IntelligentPlatform.common.service;


import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;


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
