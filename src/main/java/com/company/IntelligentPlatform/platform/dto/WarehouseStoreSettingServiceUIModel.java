package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

@Component
public class WarehouseStoreSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = WarehouseStoreSetting.NODENAME, nodeInstId = WarehouseStoreSetting.NODENAME)
	protected WarehouseStoreSettingUIModel warehouseStoreSettingUIModel;

	public WarehouseStoreSettingUIModel getWarehouseStoreSettingUIModel() {
		return warehouseStoreSettingUIModel;
	}

	public void setWarehouseStoreSettingUIModel(WarehouseStoreSettingUIModel warehouseStoreSettingUIModel) {
		this.warehouseStoreSettingUIModel = warehouseStoreSettingUIModel;
	}
	
}
