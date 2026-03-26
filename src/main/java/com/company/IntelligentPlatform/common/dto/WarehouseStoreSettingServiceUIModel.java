package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

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
