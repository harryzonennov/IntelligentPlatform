package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class WarehouseStoreSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = WarehouseStoreSetting.NODENAME, nodeInstId = WarehouseStoreSetting.NODENAME)
	protected WarehouseStoreSetting warehouseStoreSetting;

	public WarehouseStoreSetting getWarehouseStoreSetting() {
		return this.warehouseStoreSetting;
	}

	public void setWarehouseStoreSetting(WarehouseStoreSetting warehouseStoreSetting) {
		this.warehouseStoreSetting = warehouseStoreSetting;
	}

}
