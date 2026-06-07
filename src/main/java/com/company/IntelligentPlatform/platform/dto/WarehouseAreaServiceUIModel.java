package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.model.WarehouseArea;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

@Component
public class WarehouseAreaServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = WarehouseArea.NODENAME, nodeInstId = WarehouseArea.NODENAME)
	protected WarehouseAreaUIModel warehouseAreaUIModel;

	public WarehouseAreaUIModel getWarehouseAreaUIModel() {
		return warehouseAreaUIModel;
	}

	public void setWarehouseAreaUIModel(WarehouseAreaUIModel warehouseAreaUIModel) {
		this.warehouseAreaUIModel = warehouseAreaUIModel;
	}
	
}
