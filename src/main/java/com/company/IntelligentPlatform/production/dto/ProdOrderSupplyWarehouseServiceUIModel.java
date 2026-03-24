package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.ProdOrderSupplyWarehouse;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProdOrderSupplyWarehouseServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdOrderSupplyWarehouse.NODENAME, nodeInstId = ProdOrderSupplyWarehouse.NODENAME)
	protected ProdOrderSupplyWarehouseUIModel prodOrderSupplyWarehouseUIModel;

	public ProdOrderSupplyWarehouseUIModel getProdOrderSupplyWarehouseUIModel() {
		return prodOrderSupplyWarehouseUIModel;
	}

	public void setProdOrderSupplyWarehouseUIModel(ProdOrderSupplyWarehouseUIModel prodOrderSupplyWarehouseUIModel) {
		this.prodOrderSupplyWarehouseUIModel = prodOrderSupplyWarehouseUIModel;
	}

}
