package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

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
