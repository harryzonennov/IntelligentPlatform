package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.ProdOrderTarSubItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProdOrderTarSubItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProdOrderTarSubItem.NODENAME, nodeInstId = ProdOrderTarSubItem.NODENAME)
	protected ProdOrderTarSubItem prodOrderTarSubItem;

	public ProdOrderTarSubItem getProdOrderTarSubItem() {
		return prodOrderTarSubItem;
	}

	public void setProdOrderTarSubItem(ProdOrderTarSubItem prodOrderTarSubItem) {
		this.prodOrderTarSubItem = prodOrderTarSubItem;
	}
}
