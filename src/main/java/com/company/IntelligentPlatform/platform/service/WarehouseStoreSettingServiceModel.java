package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

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
