package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.ProdOrderSupplyWarehouse;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProdOrderSupplyWarehouseServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProdOrderSupplyWarehouse.NODENAME, nodeInstId = ProdOrderSupplyWarehouse.NODENAME)
	protected ProdOrderSupplyWarehouse prodOrderSupplyWarehouse;

	public ProdOrderSupplyWarehouse getProdOrderSupplyWarehouse() {
		return prodOrderSupplyWarehouse;
	}

	public void setProdOrderSupplyWarehouse(ProdOrderSupplyWarehouse prodOrderSupplyWarehouse) {
		this.prodOrderSupplyWarehouse = prodOrderSupplyWarehouse;
	}
}
