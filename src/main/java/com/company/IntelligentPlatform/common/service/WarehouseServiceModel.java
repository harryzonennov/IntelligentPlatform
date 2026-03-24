package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.model.WarehouseAttachment;
import com.company.IntelligentPlatform.common.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;


public class WarehouseServiceModel extends ServiceModule {

    @IServiceModuleFieldConfig(nodeName = Warehouse.NODENAME, nodeInstId = Warehouse.SENAME)
    protected Warehouse warehouse;

    @IServiceModuleFieldConfig(nodeName = WarehouseArea.NODENAME, nodeInstId = WarehouseArea.NODENAME)
    protected List<WarehouseAreaServiceModel> warehouseAreaList = new ArrayList<>();
    
    @IServiceModuleFieldConfig(nodeName = WarehouseStoreSetting.NODENAME, nodeInstId = WarehouseStoreSetting.NODENAME)
    protected List<ServiceEntityNode> warehouseStoreSettingList = new ArrayList<>();

    @IServiceModuleFieldConfig(nodeName = WarehouseAttachment.NODENAME , nodeInstId = WarehouseAttachment.NODENAME)
    protected List<ServiceEntityNode> warehouseAttachmentList = new ArrayList<>();

   
    public List<WarehouseAreaServiceModel> getWarehouseAreaList() {
        return this.warehouseAreaList;
    }

    public void setWarehouseAreaList(List<WarehouseAreaServiceModel> warehouseAreaList) {
        this.warehouseAreaList = warehouseAreaList;
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

	public List<ServiceEntityNode> getWarehouseStoreSettingList() {
		return warehouseStoreSettingList;
	}

	public void setWarehouseStoreSettingList(
			List<ServiceEntityNode> warehouseStoreSettingList) {
		this.warehouseStoreSettingList = warehouseStoreSettingList;
	}

    public List<ServiceEntityNode> getWarehouseAttachmentList() {
        return warehouseAttachmentList;
    }

    public void setWarehouseAttachmentList(List<ServiceEntityNode> warehouseAttachmentList) {
        this.warehouseAttachmentList = warehouseAttachmentList;
    }
}
